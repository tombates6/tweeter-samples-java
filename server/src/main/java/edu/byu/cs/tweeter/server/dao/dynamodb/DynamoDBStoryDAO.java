package edu.byu.cs.tweeter.server.dao.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.IStoryDAO;
import edu.byu.cs.tweeter.server.dao.ResultsPage;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public class DynamoDBStoryDAO implements IStoryDAO {
    private static final String TableName = "stories";
    private static final String TimestampAttr = "timestamp";
    private static final String PostAttr = "post";
    private static final String AliasAttr = "alias";
    private static final String FirstNameAttr = "first_name";
    private static final String LastNameAttr = "last_name";
    private static final String ImageURLAttr = "image_url";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    @Override
    public ResultsPage<Status> getStory(String userAlias, int limit, Status lastStatus) throws DataAccessException {
        ResultsPage<Status> result = new ResultsPage<>();

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#alias", AliasAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":userAlias", new AttributeValue().withS(userAlias));

        Map<String, AttributeValue> startKey = new HashMap<>();

        if (lastStatus != null) {
            startKey.put(AliasAttr, new AttributeValue().withS(userAlias));
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
    public void postStatus(Status status) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);

        try {
            Item item = new Item()
                    .withPrimaryKey(AliasAttr, status.getUser().getAlias(), TimestampAttr, status.getDatetime())
                    .withString(PostAttr, status.getPost())
                    .withString(FirstNameAttr, status.getUser().getFirstName())
                    .withString(LastNameAttr, status.getUser().getLastName())
                    .withString(ImageURLAttr, status.getUser().getImageUrl());
            table.putItem(item);
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("[Server Error] " + e.getMessage(), e.getCause());
        }
    }

    private Status createStatus(Map<String, AttributeValue> item) {
        String post = item.get(PostAttr).getS();
        long timestamp = Long.parseLong(item.get(TimestampAttr).getN(), 10);
        List<String> mentions = parseMentions(post);
        List<String> urls = parseURLs(post);

        String alias = item.get(AliasAttr).getS();
        String firstName = item.get(FirstNameAttr).getS();
        String lastName = item.get(LastNameAttr).getS();
        String imageURL = item.get(ImageURLAttr).getS();
        User author = new User(firstName, lastName, alias, imageURL);

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
