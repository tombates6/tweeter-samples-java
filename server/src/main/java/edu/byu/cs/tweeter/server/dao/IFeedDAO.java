package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public interface IFeedDAO {
    ResultsPage<Status> getFeed(AuthToken authToken, String userAlias, int limit, Status lastStatus) throws DataAccessException;
}
