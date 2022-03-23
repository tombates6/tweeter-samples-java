package edu.byu.cs.tweeter.server.service;

import com.google.inject.Inject;

import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.request.UserRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.model.net.response.UserResponse;
import edu.byu.cs.tweeter.server.dao.IAuthDAO;
import edu.byu.cs.tweeter.server.dao.IUserDAO;

public class UserService {
    private final IAuthDAO authDAO;
    private final IUserDAO userDAO;

    @Inject
    public UserService(IAuthDAO authDAO, IUserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public LoginResponse login(LoginRequest request) {
        if (request.getUsername() == null) {
            throw new RuntimeException("[BadRequest] Missing a username");
        } else if (request.getPassword() == null) {
            throw new RuntimeException("[BadRequest] Missing a password");
        }
        // Check UserDAO if password matches
        // Use AuthDAO to record a new AuthToken
        // Get User Profile from UserDAO
    }

    public LogoutResponse logout(LogoutRequest req) {
        if(req.getAuthToken() == null){
            throw new RuntimeException("[BadRequest] Missing auth token");
        }
        // Remove token from AuthDAO (or invalidate?)
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
        // Add user to UserDAO
        // login through AuthDAO
    }

    public UserResponse getUserProfile(UserRequest req) {
        if (req.getAlias() == null) {
            throw new RuntimeException("[BadRequest] Missing an alias");
        }
        // Check Auth token with AuthDAO
        // Get user profile from UserDAO
    }
}
