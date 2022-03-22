package edu.byu.cs.tweeter.server.dao.dynamodb;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.UserRequest;
import edu.byu.cs.tweeter.model.net.response.UserResponse;
import edu.byu.cs.tweeter.server.dao.IUserDAO;

public class DynamoDBUserDAO implements IUserDAO {

    @Override
    public UserResponse getUserProfile(UserRequest req) {
        if (req.getAlias() == null) {
            throw new RuntimeException("[BadRequest] Missing an alias");
        }
        // TODO: Generates dummy data. Replace with a real implementation.
        User user = getFakeData().findUserByAlias(req.getAlias());
        return new UserResponse(user);
    }
}
