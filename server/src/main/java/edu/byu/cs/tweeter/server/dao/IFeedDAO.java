package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public interface IFeedDAO {
    ResultsPage<Status> getFeed(String userAlias, int limit, Status lastStatus) throws DataAccessException;
    void updateFeeds(List<User> followers, Status status);
}
