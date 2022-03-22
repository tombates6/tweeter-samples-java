package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public interface IAuthDAO {
    LoginResponse login(LoginRequest req) throws DataAccessException;
    LogoutResponse logout(LogoutRequest req) throws DataAccessException;
    RegisterResponse register(RegisterRequest req) throws DataAccessException;
}
