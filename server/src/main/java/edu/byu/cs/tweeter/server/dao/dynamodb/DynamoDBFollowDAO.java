package edu.byu.cs.tweeter.server.dao.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.IFollowDAO;
import edu.byu.cs.tweeter.server.dao.ResultsPage;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class DynamoDBFollowDAO implements IFollowDAO {
    private static final String TableName = "follows";
    private static final String IndexName = "follows_index";
    private static final String FollowerHandleAttr = "follower_handle";
    private static final String FollowerFirstNameAttr = "follower_first_name";
    private static final String FollowerLastNameAttr = "follower_last_name";
    private static final String FollowerImageURLAttr = "follower_image_url";
    private static final String FolloweeHandleAttr = "followee_handle";
    private static final String FolloweeFirstNameAttr = "followee_first_name";
    private static final String FolloweeLastNameAttr = "followee_last_name";
    private static final String FolloweeImageURLAttr = "followee_image_url";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    @Override
    public boolean isFollower(String followerAlias, String followeeAlias) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);

        try {
            Item item = table.getItem(FollowerHandleAttr, followerAlias, FolloweeHandleAttr, followeeAlias);
            return (item != null);
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("[Server Error] " + e.getMessage(), e.getCause());
        }
    }

    /**
     * Gets the count of users from the database that the user specified is following. The
     * current implementation uses generated data and doesn't actually access a database.
     *
     * @param userAlias the User alias whose count of how many following is desired.
     * @return said count.
     */
    public int getFollowingCount(String userAlias) throws DataAccessException {
        ResultsPage<User> results = queryTable(userAlias, true, false, null, 0);
        return results.getValues().size();
    }

    /**
     * Gets the count of users from the database that are following the user specified. The
     * current implementation uses generated data and doesn't actually access a database.
     *
     * @param userAlias the alias of the User whose count of how many being followed is desired.
     * @return said count.
     */
    public int getFollowersCount(String userAlias) throws DataAccessException {
        ResultsPage<User> results = queryTable(userAlias, false, false, null, 0);
        return results.getValues().size();
    }

    /**
     * Gets the users from the database that the user specified in the request is following. Uses
     * information in the request object to limit the number of followees returned and to return the
     * next set of followees after any that were returned in a previous request.
     *
     * @return the followees.
     */
    public ResultsPage<User> getFollowing(String followerAlias, int limit, String lastFolloweeAlias) throws DataAccessException {
        return queryTable(followerAlias, true, true, lastFolloweeAlias, limit);
    }

    /**
     * Gets the users from the database that the user specified in the request are following the user. Uses
     * information in the request object to limit the number of followers returned and to return the
     * next set of followers after any that were returned in a previous request.
     *
     * @return the followers.
     */
    public ResultsPage<User> getFollowers(String followeeAlias, int limit, String lastFollowerAlias) throws DataAccessException {
        return queryTable(followeeAlias, false, true, lastFollowerAlias, limit);
    }

    @Override
    public List<User> getAllFollowers(String followeeAlias) throws DataAccessException {
        ResultsPage<User> results = queryTable(followeeAlias, false, false, null, 0);
        return results.getValues();
    }

    /**
     * Follows the user that the user specified in the request.
     * @param follower
     * @param followee
     */
    public void follow(User follower, User followee) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);

        try {
            Item item = new Item()
                    .withPrimaryKey(FollowerHandleAttr, follower.getAlias(), FolloweeHandleAttr, followee.getAlias())
                    .withString(FollowerFirstNameAttr, follower.getFirstName())
                    .withString(FollowerLastNameAttr, follower.getLastName())
                    .withString(FolloweeFirstNameAttr, followee.getFirstName())
                    .withString(FolloweeLastNameAttr, followee.getLastName());
            table.putItem(item);
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("[Server Error] " + e.getMessage(), e.getCause());
        }
    }

    /**
     * Unfollows the user that the user specified in the request.
     *
     */
    public void unfollow(String followerAlias, String followeeAlias) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);

        try {
            table.deleteItem(FollowerHandleAttr, followerAlias, FolloweeHandleAttr, followeeAlias);
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("[Server Error] " + e.getMessage(), e.getCause());
        }
    }


    private static ResultsPage<User> queryTable(String alias, boolean getFollowees, boolean paginate, String lastAlias, int limit) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);

        HashMap<String, String> nameMap = new HashMap<>();
        HashMap<String, Object> valueMap = new HashMap<>();
        boolean sortAscending = true;
        String hashKey = FollowerHandleAttr;
        String firstName = FolloweeFirstNameAttr;
        String lastName = FolloweeLastNameAttr;
        String imageURL = FolloweeImageURLAttr;
        String sortKey = FolloweeHandleAttr;

        if (!getFollowees) {
            hashKey = FolloweeHandleAttr;
            firstName = FollowerFirstNameAttr;
            lastName = FollowerLastNameAttr;
            imageURL = FollowerImageURLAttr;
            sortKey = FollowerHandleAttr;
            sortAscending = false;
        }
        nameMap.put("#fh", hashKey);
        valueMap.put(":handle", alias);

        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("#fh = :handle")
                .withNameMap(nameMap)
                .withValueMap(valueMap)
                .withScanIndexForward(sortAscending);

        if (paginate) {
            querySpec = querySpec.withMaxResultSize(limit);
            if (lastAlias != null) {
                PrimaryKey lastKey = new PrimaryKey(hashKey, alias, sortKey, lastAlias);
                querySpec = querySpec.withExclusiveStartKey(lastKey);
            }
        }

        ItemCollection<QueryOutcome> items;
        Iterator<Item> iterator;
        Item item;

        try {
            if (getFollowees) {
                items = table.query(querySpec);
            } else {
                Index index = table.getIndex(IndexName);
                items = index.query(querySpec);
            }
            ResultsPage<User> results = new ResultsPage<>();
            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                results.addValue(new User(
                        item.getString(firstName),
                        item.getString(lastName),
                        item.getString(sortKey),
                        item.getString(imageURL)
                ));
            }

            Map<String, AttributeValue> keyMap = items
                    .getLastLowLevelResult()
                    .getQueryResult()
                    .getLastEvaluatedKey();
            results.setHasLastItem(keyMap != null);
            return results;

        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("[Server Error] " + e.getMessage(), e.getCause());
        }

    }
}
