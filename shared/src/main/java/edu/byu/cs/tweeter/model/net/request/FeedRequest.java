package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class FeedRequest {


    private AuthToken authToken;
    private String followerAlias;
    private int limit;
    private Status lastStatus;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private FeedRequest() {}

    /**
     * Creates an instance.
     *
     * @param followerAlias the alias of the user whose statuses are to be returned.
     * @param limit the maximum number of statuses to return.
     * @param lastStatus the alias of the last status that was returned in the previous request (null if
     *                     there was no previous request or if no statuses were returned in the
     *                     previous request).
     */
    public FeedRequest(AuthToken authToken, String followerAlias, int limit, Status lastStatus) {
        this.authToken = authToken;
        this.followerAlias = followerAlias;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    /**
     * Returns the auth token of the user who is making the request.
     *
     * @return the auth token.
     */
    public AuthToken getAuthToken() {
        return authToken;
    }

    /**
     * Sets the auth token.
     *
     * @param authToken the auth token.
     */
    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    /**
     * Returns the follower whose statuses are to be returned by this request.
     *
     * @return the follower.
     */
    public String getFollowerAlias() {
        return followerAlias;
    }

    /**
     * Sets the follower.
     *
     * @param followerAlias the follower.
     */
    public void setFollowerAlias(String followerAlias) {
        this.followerAlias = followerAlias;
    }

    /**
     * Returns the number representing the maximum number of statuses to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Sets the limit.
     *
     * @param limit the limit.
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * Returns the last status that was returned in the previous request or null if there was no
     * previous request or if no statuses were returned in the previous request.
     *
     * @return the last status.
     */
    public Status getLastStatus() {
        return lastStatus;
    }

    /**
     * Sets the last status.
     *
     * @param lastStatus the last status.
     */
    public void setlastStatus(Status lastStatus) {
        this.lastStatus = lastStatus;
    }
}
