package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's story.
 */
public class GetStoryTask extends PagedStatusTask {

    public GetStoryTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                        Handler messageHandler) {
        super(authToken, targetUser, limit, lastStatus, messageHandler);
    }

    @Override
    protected Pair<List<Status>, Boolean> getItems() {
        Pair<List<Status>, Boolean> items;
        try {
            StoryResponse res = getServer().getStory(new StoryRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), getLastItem()));
            if (!res.isSuccess()) {
                sendFailedMessage(res.getMessage());
                return null;
            }
            items = new Pair<>(res.getStory(), res.getHasMorePages());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return items;
    }
}
