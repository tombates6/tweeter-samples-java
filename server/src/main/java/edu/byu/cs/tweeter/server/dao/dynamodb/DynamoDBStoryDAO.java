package edu.byu.cs.tweeter.server.dao.dynamodb;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.IStoryDAO;

public class DynamoDBStoryDAO implements IStoryDAO {
    @Override
    public StoryResponse getStory(StoryRequest req) {
        assert req.getUserAlias() != null;
        assert req.getLimit() > 0;
        // TODO: Generates dummy data. Replace with a real implementation.
        assert req.getLimit() > 0;
        assert req.getUserAlias() != null;

        List<Status> allStatuses = getDummyStory();
        List<Status> responseStatuses = new ArrayList<>(req.getLimit());

        boolean hasMorePages = false;

        if (req.getLimit() > 0) {
            if (allStatuses != null) {
                int statusIndex = getStatusStartingIndex(req.getLastStatus(), allStatuses);

                for (int limitCounter = 0; statusIndex < allStatuses.size() && limitCounter < req.getLimit(); statusIndex++, limitCounter++) {
                    responseStatuses.add(allStatuses.get(statusIndex));
                }

                hasMorePages = statusIndex < allStatuses.size();
            }
        }

        return new StoryResponse(responseStatuses, hasMorePages);
    }

    @Override
    public PostStatusResponse postStatus(PostStatusRequest req) {
        return new PostStatusResponse(true, null);
    }
}
