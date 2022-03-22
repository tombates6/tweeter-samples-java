package edu.byu.cs.tweeter.server.dao.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;

import java.time.Instant;
import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.server.dao.IAuthDAO;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public class DynamoDBAuthDAO implements IAuthDAO {
    private static final String TableName = "auth";
    private static final String TokenAttr = "token";
    private static final String TimestampAttr = "timestamp";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    @Override
    public AuthToken login(LoginRequest req) throws DataAccessException {
        AuthToken authToken = new AuthToken(
                UUID.randomUUID().toString(),
                String.valueOf(Instant.now().toEpochMilli())
        );
        Table table = dynamoDB.getTable(TableName);

        try {
            Item item = new Item().withPrimaryKey(TokenAttr, authToken.getToken()).withString(TimestampAttr, authToken.getDatetime());
            table.putItem(item);
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException(e.getMessage(), e.getCause());
        }

        return authToken;
    }

    @Override
    public void logout(AuthToken req) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);

        try {
            table.deleteItem(TokenAttr, req.getToken());
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean validToken(AuthToken authToken) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);

        try {
            Item item = table.getItem(TokenAttr, authToken.getToken());
            return (item != null) && !isExpired(item.getString(TimestampAttr));
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void expireToken(AuthToken authToken) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);

        try {
            table.deleteItem(TokenAttr, authToken.getToken());
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException(e.getMessage(), e.getCause());
        }
    }

    private boolean isExpired(String timestamp) {
        // TODO compare with current timestamp to make sure it's active
        return false;
    }
}
