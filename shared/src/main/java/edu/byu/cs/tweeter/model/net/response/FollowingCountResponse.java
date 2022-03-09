package edu.byu.cs.tweeter.model.net.response;
import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;

/**
 * A response for a {@link FollowingCountRequest}.
 */
public class FollowingCountResponse extends Response {
    private int count;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public FollowingCountResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param count number of users being followed
     */
    public FollowingCountResponse(int count) {
        super(true, null);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
