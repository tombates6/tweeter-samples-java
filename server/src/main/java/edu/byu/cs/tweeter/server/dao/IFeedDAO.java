package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public interface IFeedDAO {
    FeedResponse getFeed(FeedRequest req) throws DataAccessException;
}
