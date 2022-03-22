package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public interface IFollowDAO {
    boolean isFollower(String followerAlias, String followeeAlias) throws DataAccessException;
    FollowersResponse getFollowers(FollowersRequest request) throws DataAccessException;
    FollowersCountResponse getFollowersCount(FollowersCountRequest request) throws DataAccessException;
    FollowingResponse getFollowing(FollowingRequest request) throws DataAccessException;
    FollowingCountResponse getFollowingCount(FollowingCountRequest request) throws DataAccessException;
    FollowResponse follow(FollowRequest req) throws DataAccessException;
    UnfollowResponse unfollow(UnfollowRequest req) throws DataAccessException;
}
