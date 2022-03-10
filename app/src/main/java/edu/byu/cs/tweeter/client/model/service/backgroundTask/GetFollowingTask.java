package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedUserTask {

    public GetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee,
                            Handler messageHandler) {
        super(authToken, targetUser, limit, lastFollowee, messageHandler);
    }

    @Override
    protected Pair<List<User>, Boolean> getItems() {
        Pair<List<User>, Boolean> items;
        try {
            FollowingResponse res = getServer().getFollowing(new FollowingRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), getLastItem().getAlias()));
            if (!res.isSuccess()) {
                sendFailedMessage(res.getMessage());
                return null;
            }
            items = new Pair<>(res.getFollowees(), res.getHasMorePages());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return items;
    }
}
