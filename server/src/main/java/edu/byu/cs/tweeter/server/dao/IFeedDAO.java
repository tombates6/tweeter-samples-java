package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public interface IFeedDAO {
    ResultsPage<Status> getFeed(AuthToken authToken, String userAlias, int limit, Status lastStatus) throws DataAccessException;
}
