package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.net.request.FollowersCountRequest;

/**
 * A response for a {@link FollowersCountRequest}.
 */
public class FollowersCountResponse {
    private int count;

    public FollowersCountResponse(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
