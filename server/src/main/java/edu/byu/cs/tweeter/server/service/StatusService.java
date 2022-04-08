package edu.byu.cs.tweeter.server.service;

import javax.inject.Inject;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.request.UpdateFeedsRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.IAuthDAO;
import edu.byu.cs.tweeter.server.dao.IFeedDAO;
import edu.byu.cs.tweeter.server.dao.IFollowDAO;
import edu.byu.cs.tweeter.server.dao.IStoryDAO;
import edu.byu.cs.tweeter.server.dao.IUserDAO;
import edu.byu.cs.tweeter.server.dao.ResultsPage;
import edu.byu.cs.tweeter.server.dao.dynamodb.DynamoDBAuthDAO;
import edu.byu.cs.tweeter.server.dao.dynamodb.DynamoDBFeedDAO;
import edu.byu.cs.tweeter.server.dao.dynamodb.DynamoDBStoryDAO;
import edu.byu.cs.tweeter.server.dao.dynamodb.DynamoDBUserDAO;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public class StatusService {
//    private final IAuthDAO authDAO;
//    private final IStoryDAO storyDAO;
//    private final IFeedDAO feedDAO;

//    @Inject
//    public StatusService(IAuthDAO authDAO, IStoryDAO storyDAO, IFeedDAO feedDAO) {
//        this.authDAO = authDAO;
//        this.storyDAO = storyDAO;
//        this.feedDAO = feedDAO;
//    }
    
    public FeedResponse getFeed(FeedRequest req) {
        if (req.getUserAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a user alias");
        } else if(req.getLimit() <= 0) {
            throw new RuntimeException("[BadRequest] Request needs to have a positive limit");
        }
        try {
            getAuthDAO().validateToken(req.getAuthToken());
            ResultsPage<Status> results = getFeedDAO().getFeed(
                    req.getUserAlias(),
                    req.getLimit(),
                    req.getLastStatus()
            );
            return new FeedResponse(
                    results.getValues(),
                    results.hasLastItem()
            );
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public StoryResponse getStory(StoryRequest req) {
        if (req.getUserAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a user alias");
        } else if(req.getLimit() <= 0) {
            throw new RuntimeException("[BadRequest] Request needs to have a positive limit");
        }

        try {
            getAuthDAO().validateToken(req.getAuthToken());
            ResultsPage<Status> results = getStoryDAO().getStory(
                    req.getUserAlias(),
                    req.getLimit(),
                    req.getLastStatus()
            );
            return new StoryResponse(
                    results.getValues(),
                    results.hasLastItem()
            );
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public PostStatusResponse post(PostStatusRequest req) {
        if (req.getStatus() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a status");
        }
        try {
            getAuthDAO().validateToken(req.getAuthToken());
            getStoryDAO().postStatus(req.getStatus());
            return new PostStatusResponse(true, null);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateAllFeeds(UpdateFeedsRequest req) {
        try {
            getFeedDAO().updateFeeds(req.getAliases(), req.getStatus());
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // FIXME this is a temporary arrangement to appease the DI/unit test demons
    public IStoryDAO getStoryDAO() {
        return new DynamoDBStoryDAO();
    }
    public IAuthDAO getAuthDAO() {
        return new DynamoDBAuthDAO();
    }
    public IFeedDAO getFeedDAO() {
        return new DynamoDBFeedDAO();
    }
}
