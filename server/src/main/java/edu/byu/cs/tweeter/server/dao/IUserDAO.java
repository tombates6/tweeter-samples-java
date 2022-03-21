package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.request.UserRequest;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.model.net.response.UserResponse;

public interface IUserDAO {
    UserResponse getUserProfile(UserRequest req);
}
