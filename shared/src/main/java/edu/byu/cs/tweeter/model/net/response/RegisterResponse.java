package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public class RegisterResponse extends Response {
    private Pair<User, AuthToken> loginResult;

    public RegisterResponse(String message) {
        super(false, message);
    }

    public RegisterResponse(Pair<User, AuthToken> loginResult) {
        super(true, null);
        this.loginResult = loginResult;
    }

    public Pair<User, AuthToken> getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(Pair<User, AuthToken> loginResult) {
        this.loginResult = loginResult;
    }
}
