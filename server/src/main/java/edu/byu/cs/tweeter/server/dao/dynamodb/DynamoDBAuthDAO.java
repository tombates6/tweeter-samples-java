package edu.byu.cs.tweeter.server.dao.dynamodb;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.IAuthDAO;
import edu.byu.cs.tweeter.util.Pair;

public class DynamoDBAuthDAO implements IAuthDAO {
    @Override
    public LoginResponse login(LoginRequest req) {
        if(request.getUsername() == null){
            throw new RuntimeException("[BadRequest] Missing a username");
        } else if(request.getPassword() == null) {
            throw new RuntimeException("[BadRequest] Missing a password");
        }

        // TODO: Generates dummy data. Replace with a real implementation.
        User user = getDummyUser();
        AuthToken authToken = getDummyAuthToken();
        return new LoginResponse(user, authToken);
    }

    @Override
    public LogoutResponse logout(LogoutRequest req) {
        // TODO: Generates dummy data. Replace with a real implementation.
        return new LogoutResponse(true, null);
    }

    @Override
    public RegisterResponse register(RegisterRequest req) {
        // TODO: Generates dummy data. Replace with a real implementation.
        User registeredUser = getFakeData().getFirstUser();
        AuthToken authToken = getFakeData().getAuthToken();
        return new RegisterResponse(new Pair<>(registeredUser, authToken));
    }
}
