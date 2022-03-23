package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public interface IUserDAO {
    User getUserProfile(String alias) throws DataAccessException;
    boolean validPassword(String alias, String password) throws DataAccessException;
    void addUser(RegisterRequest req) throws DataAccessException;
}
