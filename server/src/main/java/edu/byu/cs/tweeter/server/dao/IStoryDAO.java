package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public interface IStoryDAO {
    StoryResponse getStory(StoryRequest req) throws DataAccessException;
    PostStatusResponse postStatus(PostStatusRequest req) throws DataAccessException;
}
