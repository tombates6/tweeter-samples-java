package edu.byu.cs.tweeter.model.net.response;

public class RegisterResponse extends Response {
    public RegisterResponse(boolean success, String message) {
        super(success, message);
    }
}
