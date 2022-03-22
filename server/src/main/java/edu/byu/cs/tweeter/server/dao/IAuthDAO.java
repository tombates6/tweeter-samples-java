package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public interface IAuthDAO {
    AuthToken login(String alias) throws DataAccessException;
    void logout(AuthToken authToken) throws DataAccessException;
    String getAlias(AuthToken authToken) throws DataAccessException;
    void expireToken(AuthToken authToken) throws DataAccessException;
}
