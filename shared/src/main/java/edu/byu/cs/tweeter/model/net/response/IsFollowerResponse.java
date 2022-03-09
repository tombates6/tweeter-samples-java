package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class IsFollowerResponse extends Response {
    boolean isFollower;

    IsFollowerResponse(String message) {
        super(false, message);
    }

    IsFollowerResponse(boolean isFollower) {
        super(true, null);
        this.isFollower = isFollower;
    }

    public boolean isFollower() {
        return isFollower;
    }

    public void setFollower(boolean follower) {
        isFollower = follower;
    }
}
