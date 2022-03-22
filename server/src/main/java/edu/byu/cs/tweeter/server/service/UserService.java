package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.request.UserRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.model.net.response.UserResponse;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

public class UserService {

    public LoginResponse login(LoginRequest request) {
    }

    public LogoutResponse logout(LogoutRequest req) {
        if(req.getAuthToken() == null){
            throw new RuntimeException("[BadRequest] Missing auth token");
        }
    }

    public RegisterResponse register(RegisterRequest req) {
        if (req.getUsername() == null) {
            throw new RuntimeException("[BadRequest] Missing auth token");
        } else if (req.getPassword() == null) {
            throw new RuntimeException("[BadRequest] Missing auth token");
        } else if (req.getFirstName() == null || req.getLastName() == null) {
            throw new RuntimeException("[BadRequest] Missing auth token");
        } else if (req.getImage() == null) {
            throw new RuntimeException("[BadRequest] Missing auth token");
        }
    }

    public UserResponse getUserProfile(UserRequest req) {
    }

    /**
     * Returns the dummy user to be returned by the login operation.
     * This is written as a separate method to allow mocking of the dummy user.
     *
     * @return a dummy user.
     */
    User getDummyUser() {
        return getFakeData().getFirstUser();
    }

    /**
     * Returns the dummy auth token to be returned by the login operation.
     * This is written as a separate method to allow mocking of the dummy auth token.
     *
     * @return a dummy auth token.
     */
    AuthToken getDummyAuthToken() {
        return getFakeData().getAuthToken();
    }

    /**
     * Returns the {@link FakeData} object used to generate dummy users and auth tokens.
     * This is written as a separate method to allow mocking of the {@link FakeData}.
     *
     * @return a {@link FakeData} instance.
     */
    FakeData getFakeData() {
        return new FakeData();
    }
}
