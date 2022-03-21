package edu.byu.cs.tweeter.server.dao.dynamodb;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.util.FakeData;

public class StatusDAO {
    public FeedResponse getFeed(FeedRequest req) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert req.getLimit() > 0;
        assert req.getUserAlias() != null;

        List<Status> allStatuses = getDummyFeed();
        List<Status> responseStatuses = new ArrayList<>(req.getLimit());

        boolean hasMorePages = false;

        if(req.getLimit() > 0) {
            if (allStatuses != null) {
                int statusIndex = getStatusStartingIndex(req.getLastStatus(), allStatuses);

                for(int limitCounter = 0; statusIndex < allStatuses.size() && limitCounter < req.getLimit(); statusIndex++, limitCounter++) {
                    responseStatuses.add(allStatuses.get(statusIndex));
                }

                hasMorePages = statusIndex < allStatuses.size();
            }
        }

        return new FeedResponse(responseStatuses, hasMorePages);
    }

    public StoryResponse getStory(StoryRequest req) {
        assert req.getUserAlias() != null;
        assert req.getLimit() > 0;
        // TODO: Generates dummy data. Replace with a real implementation.
        assert req.getLimit() > 0;
        assert req.getUserAlias() != null;

        List<Status> allStatuses = getDummyStory();
        List<Status> responseStatuses = new ArrayList<>(req.getLimit());

        boolean hasMorePages = false;

        if(req.getLimit() > 0) {
            if (allStatuses != null) {
                int statusIndex = getStatusStartingIndex(req.getLastStatus(), allStatuses);

                for(int limitCounter = 0; statusIndex < allStatuses.size() && limitCounter < req.getLimit(); statusIndex++, limitCounter++) {
                    responseStatuses.add(allStatuses.get(statusIndex));
                }

                hasMorePages = statusIndex < allStatuses.size();
            }
        }

        return new StoryResponse(responseStatuses, hasMorePages);
    }

    public PostStatusResponse post(PostStatusRequest req) {
        return new PostStatusResponse(true, null);
    }

    List<Status> getDummyStory() {
        return getFakeData().getFakeStatuses();
    }

    List<Status> getDummyFeed() {
        return getFakeData().getFakeStatuses();
    }

    private int getStatusStartingIndex(Status lastStatusAlias, List<Status> allStatuses) {

        int statusesIndex = 0;

        if(lastStatusAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatusAlias.equals(allStatuses.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusesIndex = i + 1;
                    break;
                }
            }
        }

        return statusesIndex;
    }

    /**
     * Returns the {@link FakeData} object used to generate dummy followees.
     * This is written as a separate method to allow mocking of the {@link FakeData}.
     *
     * @return a {@link FakeData} instance.
     */
    FakeData getFakeData() {
        return new FakeData();
    }
}
