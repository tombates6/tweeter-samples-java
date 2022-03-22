package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public interface IStoryDAO {
    ResultsPage<Status> getStory(String userAlias, int limit, Status lastStatus) throws DataAccessException;
    void postStatus(Status status) throws DataAccessException;
}
