package edu.byu.cs.tweeter.server.service;

import javax.inject.Inject;

import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.IAuthDAO;
import edu.byu.cs.tweeter.server.dao.IFeedDAO;
import edu.byu.cs.tweeter.server.dao.IStoryDAO;

public class StatusService {
    private final IAuthDAO authDAO;
    private final IStoryDAO storyDAO;
    private final IFeedDAO feedDAO;
    
    @Inject
    public StatusService(IAuthDAO authDAO, IStoryDAO storyDAO, IFeedDAO feedDAO) {
        this.authDAO = authDAO;
        this.storyDAO = storyDAO;
        this.feedDAO = feedDAO;
    }
    // TODO check authToken status in all methods
    
    
    public FeedResponse getFeed(FeedRequest req) {
        if (req.getUserAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a user alias");
        } else if(req.getLimit() <= 0) {
            throw new RuntimeException("[BadRequest] Request needs to have a positive limit");
        }
        // Check auth token
        // Get statuses
        return feedDAO.getFeed(req);
    }

    public StoryResponse getStory(StoryRequest req) {
        if (req.getUserAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a user alias");
        } else if(req.getLimit() <= 0) {
            throw new RuntimeException("[BadRequest] Request needs to have a positive limit");
        }
        return storyDAO.getStory(req);
    }

    public PostStatusResponse post(PostStatusRequest req) {
        if (req.getStatus() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a status");
        }
        // TODO also update feed here
        return storyDAO.postStatus(req.getStatus());
    }
}
