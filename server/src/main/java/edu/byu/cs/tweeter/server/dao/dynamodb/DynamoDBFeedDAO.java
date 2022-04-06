package edu.byu.cs.tweeter.server.dao.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.IFeedDAO;
import edu.byu.cs.tweeter.server.dao.ResultsPage;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public class DynamoDBFeedDAO implements IFeedDAO {
    private static final String TableName = "feeds";
    private static final String OwnerAliasAttr = "owner_alias";
    private static final String TimestampAttr = "timestamp";
    private static final String PostAttr = "post";
    private static final String AuthorAliasAttr = "author_alias";
    private static final String AuthorFirstNameAttr = "first_name";
    private static final String AuthorLastNameAttr = "last_name";
    private static final String AuthorImageURLAttr = "image_url";
    private static final int MAX_WRITE_ITEMS = 25;

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    @Override
    public ResultsPage<Status> getFeed(String userAlias, int limit, Status lastStatus) throws DataAccessException {
        ResultsPage<Status> result = new ResultsPage<>();

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#alias", OwnerAliasAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":userAlias", new AttributeValue().withS(userAlias));

        Map<String, AttributeValue> startKey = new HashMap<>();

        if (lastStatus != null) {
            startKey.put(OwnerAliasAttr, new AttributeValue().withS(userAlias));
            startKey.put(TimestampAttr, new AttributeValue().withN(String.valueOf(lastStatus.getDatetime())));
        }

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#alias = :userAlias")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(limit);
        if (lastStatus != null) {
                queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        try {
            QueryResult queryResult = amazonDynamoDB.query(queryRequest);
            List<Map<String, AttributeValue>> items = queryResult.getItems();
            if (items != null) {
                for (Map<String, AttributeValue> item : items) {
                    result.addValue(createStatus(item));
                }
            }

            Map<String, AttributeValue> keyMap = queryResult.getLastEvaluatedKey();
            result.setHasLastItem(keyMap != null);

            return result;
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("[Server Error] " + e.getMessage(), e.getCause());
        }
    }

    @Override
    public void updateFeeds(List<String> aliases, Status status) throws DataAccessException {
        List<Item> items = new ArrayList<>();
        for (String alias : aliases) {
            Item item = new Item()
                    .withPrimaryKey(OwnerAliasAttr, alias, TimestampAttr, status.getDatetime())
                    .withString(PostAttr, status.getPost())
                    .withString(AuthorAliasAttr, status.getUser().getAlias())
                    .withString(AuthorFirstNameAttr, status.getUser().getFirstName())
                    .withString(AuthorLastNameAttr, status.getUser().getLastName())
                    .withString(AuthorImageURLAttr, status.getUser().getImageUrl());
            items.add(item);
        }
        try {
            int lastIndex = 0;
            int nextIndex = MAX_WRITE_ITEMS < items.size() ? MAX_WRITE_ITEMS : items.size() - 1;
            while (lastIndex <= items.size() - 1) {
                TableWriteItems feedWriteItems = new TableWriteItems(TableName)
                        .withItemsToPut(items.subList(lastIndex, nextIndex));
                BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(feedWriteItems);
                do {

                    // Check for unprocessed keys which could happen if you exceed
                    // provisioned throughput

                    Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();

                    if (outcome.getUnprocessedItems().size() != 0) {
                        System.out.println("Retrieving the unprocessed items");
                        outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
                    }

                } while (outcome.getUnprocessedItems().size() > 0);

                lastIndex = nextIndex + 1;
                nextIndex = lastIndex + MAX_WRITE_ITEMS;

                if (nextIndex >= items.size()) {
                    nextIndex = items.size() - 1;
                }
            }
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("[Server Error] " + e.getMessage(), e.getCause());
        }
    }

    private Status createStatus(Map<String, AttributeValue> item) {
        String post = item.get(PostAttr).getS();
        long timestamp = Long.parseLong(item.get(TimestampAttr).getN(), 10);
        List<String> mentions = parseMentions(post);
        List<String> urls = parseURLs(post);

        String authorAlias = item.get(AuthorAliasAttr).getS();
        String authorFirstName = item.get(AuthorFirstNameAttr).getS();
        String authorLastName = item.get(AuthorLastNameAttr).getS();
        String authorImageURL = item.get(AuthorImageURLAttr).getS();
        User author = new User(authorFirstName, authorLastName, authorAlias, authorImageURL);

        return new Status(post, author, timestamp, urls, mentions);
    }

    // TODO make the following three functions shared. They need to be used in the client and the server.
    private int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

    private List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }

    private List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }
}
