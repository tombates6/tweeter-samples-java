package edu.byu.cs.tweeter.server.dao.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.server.dao.IUserDAO;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public class DynamoDBUserDAO implements IUserDAO {
    private static final String TableName = "users";
    private static final String AliasAttr = "alias";
    private static final String PasswordAttr = "password";
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
    public User getUserProfile(String alias) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);

        try {
            Item item = table.getItem(AliasAttr, alias);
            if (item == null) throw new DataAccessException("No such user exists!");
            return new User(
                    item.getString(FirstNameAttr),
                    item.getString(LastNameAttr),
                    item.getString(AliasAttr),
                    item.getString(ImageURLAttr)
            );
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean validPassword(String alias, String password) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);
        try {
            Item item = table.getItem(AliasAttr, alias);
            return item != null && item.getString(PasswordAttr).equals(password);
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void addUser(RegisterRequest req) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);
        try {
            Item item = new Item()
                    .withPrimaryKey(AliasAttr, req.getUsername())
                    .withString(PasswordAttr, req.getPassword())
                    .withString(FirstNameAttr, req.getFirstName())
                    .withString(LastNameAttr, req.getLastName())
                    .withString(ImageURLAttr, req.getImage());
            table.putItem(item);
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException(e.getMessage(), e.getCause());
        }
    }
}
