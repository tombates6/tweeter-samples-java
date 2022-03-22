package edu.byu.cs.tweeter.server.dao.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.UserRequest;
import edu.byu.cs.tweeter.model.net.response.UserResponse;
import edu.byu.cs.tweeter.server.dao.IUserDAO;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public class DynamoDBUserDAO implements IUserDAO {
    private static final String TableName = "users";
    private static final String IndexName = "alias";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    @Override
    public UserResponse getUserProfile(UserRequest req) throws DataAccessException {
        if (req.getAlias() == null) {
            throw new RuntimeException("[BadRequest] Missing an alias");
        }
        // TODO: Generates dummy data. Replace with a real implementation.
        User user = getFakeData().findUserByAlias(req.getAlias());
        return new UserResponse(user);
    }
}
