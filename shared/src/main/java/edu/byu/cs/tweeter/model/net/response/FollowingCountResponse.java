package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;

/**
 * A response for a {@link FollowingCountRequest}.
 */
public class FollowingCountResponse {
    private int count;

    public FollowingCountResponse(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
