package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of followers.
 */
public class GetFollowersTask extends PagedUserTask {

    public GetFollowersTask(AuthToken authToken, User targetUser, int limit, User lastFollower,
                            Handler messageHandler) {
        super(authToken, targetUser, limit, lastFollower, messageHandler);
    }

    @Override
    protected Pair<List<User>, Boolean> getItems() {
        Pair<List<User>, Boolean> items;
        try {
            FollowersResponse res = getServer().getFollowers(new FollowersRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), getLastItem().getAlias()));
            if (!res.isSuccess()) {
                sendFailedMessage(res.getMessage());
                return null;
            }
            items = new Pair<>(res.getFollowers(), res.getHasMorePages());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return items;
    }
}
