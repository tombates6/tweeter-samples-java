package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PagedStatusTask {

    public GetFeedTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                       Handler messageHandler) {
        super(authToken, targetUser, limit, lastStatus, messageHandler);
    }

    @Override
    protected Pair<List<Status>, Boolean> getItems() throws RuntimeException {
        Pair<List<Status>, Boolean> items;
        try {
            FeedResponse res = getServer().getFeed(
                    new FeedRequest(
                            getAuthToken(),
                            getTargetUser().getAlias(),
                            getLimit(),
                            getLastItem()
                    ),
                    "/status/get-feed");
            items = new Pair<>(res.getFeed(), res.getHasMorePages());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return items;
    }
}
