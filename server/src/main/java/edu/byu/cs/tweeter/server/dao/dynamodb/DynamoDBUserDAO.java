package edu.byu.cs.tweeter.server.dao.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.byu.cs.tweeter.model.domain.User;
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
            if (item == null) throw new DataAccessException("[BadRequest] User not found");
            return new User(
                    item.getString(FirstNameAttr),
                    item.getString(LastNameAttr),
                    item.getString(AliasAttr),
                    item.getString(ImageURLAttr)
            );
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("[Server Error] " + e.getMessage(), e.getCause());
        }
    }

    @Override
    public void validatePassword(String alias, String password) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);
        try {
            Item item = table.getItem(AliasAttr, alias);
            if (item == null || !item.getString(PasswordAttr).equals(hashPassword(password))) {
                throw new RuntimeException("[BadRequest] Incorrect Username or Password");
            }
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("[Server Error] " + e.getMessage(), e.getCause());
        }
    }

    @Override
    public void addUser(User newUser, String password) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);
        try {
            Item item = new Item()
                    .withPrimaryKey(AliasAttr, newUser.getAlias())
                    .withString(PasswordAttr, hashPassword(password))
                    .withString(FirstNameAttr, newUser.getFirstName())
                    .withString(LastNameAttr, newUser.getLastName())
                    .withString(ImageURLAttr, newUser.getImageUrl());
            table.putItem(item);
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("[Server Error] " + e.getMessage(), e.getCause());
        }
    }

    private String hashPassword(String passwordToHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "FAILED TO HASH";
    }
}
