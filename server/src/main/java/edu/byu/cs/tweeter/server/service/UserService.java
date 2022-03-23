package edu.byu.cs.tweeter.server.service;

import com.google.inject.Inject;

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
import edu.byu.cs.tweeter.server.dao.IAuthDAO;
import edu.byu.cs.tweeter.server.dao.IImageDAO;
import edu.byu.cs.tweeter.server.dao.IUserDAO;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;
import edu.byu.cs.tweeter.util.Pair;

public class UserService {
    private final IAuthDAO authDAO;
    private final IUserDAO userDAO;
    private final IImageDAO imageDAO;

    @Inject
    public UserService(IAuthDAO authDAO, IUserDAO userDAO, IImageDAO imageDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
        this.imageDAO = imageDAO;
    }

    public LoginResponse login(LoginRequest req) {
        if (req.getUsername() == null) {
            throw new RuntimeException("[BadRequest] Missing a username");
        } else if (req.getPassword() == null) {
            throw new RuntimeException("[BadRequest] Missing a password");
        }
        try {
            userDAO.validatePassword(req.getUsername(), req.getPassword());
            User currUser = userDAO.getUserProfile(req.getUsername());
            AuthToken authToken = authDAO.login(req.getUsername());
            return new LoginResponse(currUser, authToken);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public LogoutResponse logout(LogoutRequest req) {
        if(req.getAuthToken() == null){
            throw new RuntimeException("[BadRequest] Missing auth token");
        }
        try {
            authDAO.logout(req.getAuthToken());
            return new LogoutResponse(true, null);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
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
        try {
            String imageURL = imageDAO.uploadImage(req.getUsername(), req.getImage());
            User newUser = new User(
                    req.getUsername(),
                    req.getFirstName(),
                    req.getLastName(),
                    imageURL
            );
            userDAO.addUser(newUser, req.getPassword());
            AuthToken authToken = authDAO.login(req.getUsername());
            return new RegisterResponse(new Pair<>(newUser, authToken));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public UserResponse getUserProfile(UserRequest req) {
        if (req.getAlias() == null) {
            throw new RuntimeException("[BadRequest] Missing an alias");
        }
        try {
            authDAO.validateToken(req.getAuthToken());
            User currUser = userDAO.getUserProfile(req.getAlias());
            return new UserResponse(currUser);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
