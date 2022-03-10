package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.TweeterRequestException;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticateTask {

    public LoginTask(String username, String password, Handler messageHandler) {
        super(messageHandler, username, password);
    }

    @Override
    protected Pair<User, AuthToken> runAuthenticationTask() throws IOException {
        Pair<User, AuthToken> loginResult;
        try {
            LoginResponse res = getServer().login(new LoginRequest(username, password));
            if (!res.isSuccess()) {
                sendFailedMessage(res.getMessage());
                return null;
            }
            loginResult = new Pair<>(res.getUser(), res.getAuthToken());
        } catch (TweeterRemoteException e) {
            throw new RuntimeException(e.getMessage());
        }
        return loginResult;
    }
}
