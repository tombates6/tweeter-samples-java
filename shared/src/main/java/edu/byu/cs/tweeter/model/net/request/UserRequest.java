package edu.byu.cs.tweeter.model.net.request;

public class UserRequest {
    private String alias;
    private String authToken;

    private UserRequest(){}

    public UserRequest(String alias, String authToken) {
        this.alias = alias;
        this.authToken = authToken;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
