package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.dynamodb.DynamoDBFeedDAO;

public class StatusService {
    public FeedResponse getFeed(FeedRequest req) {
        if (req.getUserAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a user alias");
        } else if(req.getLimit() <= 0) {
            throw new RuntimeException("[BadRequest] Request needs to have a positive limit");
        }
        return getStatusDAO().getFeed(req);
    }

    public StoryResponse getStory(StoryRequest req) {
        if (req.getUserAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a user alias");
        } else if(req.getLimit() <= 0) {
            throw new RuntimeException("[BadRequest] Request needs to have a positive limit");
        }
        return getStatusDAO().getStory(req);
    }

    public PostStatusResponse post(PostStatusRequest req) {
        if (req.getStatus() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a status");
        }
        return getStatusDAO().post(req);
    }

    /**
     * Returns an instance of {@link DynamoDBFeedDAO}. Allows mocking of the StatusDAO class
     * for testing purposes. All usages of StatusDAO should get their StatusDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    DynamoDBFeedDAO getStatusDAO() {
        return new DynamoDBFeedDAO();
    }
}
