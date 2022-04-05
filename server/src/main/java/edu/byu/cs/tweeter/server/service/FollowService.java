package edu.byu.cs.tweeter.server.service;

import java.util.List;

import javax.inject.Inject;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.PostFollowersResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;
import edu.byu.cs.tweeter.server.dao.IAuthDAO;
import edu.byu.cs.tweeter.server.dao.IFollowDAO;
import edu.byu.cs.tweeter.server.dao.IUserDAO;
import edu.byu.cs.tweeter.server.dao.ResultsPage;
import edu.byu.cs.tweeter.server.dao.dynamodb.DynamoDBFollowDAO;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService {
    private final IAuthDAO authDAO;
    private final IFollowDAO followDAO;
    private final IUserDAO userDAO;

    @Inject
    public FollowService(IAuthDAO authDAO, IFollowDAO followDAO, IUserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
        this.followDAO = followDAO;
    }

    /**
     * Returns whether a user is following another user
     *
     * @param request contains the data required to fulfill the request.
     * @return the followers.
     */
    public IsFollowerResponse isFollower(IsFollowerRequest request) {
        if(request.getFolloweeAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a followee alias");
        } else if(request.getFollowerAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a follower alias");
        }
        try {
            authDAO.validateToken(request.getAuthToken());
            boolean isFollower = followDAO.isFollower(request.getFollowerAlias(), request.getFolloweeAlias());
            return new IsFollowerResponse(isFollower);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Returns the users that are following the user specified in the request. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. Uses the {@link DynamoDBFollowDAO} to
     * get the followers.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followers.
     */
    public FollowersResponse getFollowers(FollowersRequest request) {
        if(request.getFolloweeAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a followee alias");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[BadRequest] Request needs to have a positive limit");
        }
        try {
            authDAO.validateToken(request.getAuthToken());
            ResultsPage<User> results = followDAO.getFollowers(
                    request.getFolloweeAlias(),
                    request.getLimit(),
                    request.getLastFollowerAlias()
            );
            return new FollowersResponse(results.getValues(), results.hasLastItem());
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Returns the count of users that are following the user specified in the request.
     * Uses the {@link DynamoDBFollowDAO} to get the count.
     *
     * @param request contains the data required to fulfill the request.
     * @return the count.
     */
    public FollowersCountResponse getFollowersCount(FollowersCountRequest request) {
        if(request.getUserAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a followee alias");
        }
        try {
            authDAO.validateToken(request.getAuthToken());
            int count = followDAO.getFollowersCount(request.getUserAlias());
            return new FollowersCountResponse(count);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link DynamoDBFollowDAO} to
     * get the followees.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FollowingResponse getFollowing(FollowingRequest request) {
        if(request.getFollowerAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a follower alias");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[BadRequest] Request needs to have a positive limit");
        }
        try {
            authDAO.validateToken(request.getAuthToken());
            ResultsPage<User> results = followDAO.getFollowing(
                    request.getFollowerAlias(),
                    request.getLimit(),
                    request.getLastFolloweeAlias()
            );
            return new FollowingResponse(results.getValues(), results.hasLastItem());
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Returns the count of users that are following the user specified in the request.
     * Uses the {@link DynamoDBFollowDAO} to get the count.
     *
     * @param request contains the data required to fulfill the request.
     * @return the count.
     */
    public FollowingCountResponse getFollowingCount(FollowingCountRequest request) {
        if(request.getUserAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a user alias");
        }
        try {
            authDAO.validateToken(request.getAuthToken());
            int count = followDAO.getFollowingCount(request.getUserAlias());
            return new FollowingCountResponse(count);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Follows the user that the user specified in the request.
     *
     * @param req contains the data required to fulfill the request.
     * @return success or failure.
     */
    public FollowResponse follow(FollowRequest req) {
        try {
            String followerAlias = authDAO.getAlias(req.getAuthToken());
            User follower = userDAO.getUserProfile(followerAlias);
            User followee = userDAO.getUserProfile(req.getSelectedUser().getAlias());
            followDAO.follow(follower, followee);
            return new FollowResponse(true, null);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Unfollows the user that the user specified in the request.
     *
     * @param req contains the data required to fulfill the request.
     * @return success or failure.
     */
    public UnfollowResponse unfollow(UnfollowRequest req) {
        try {
            String alias = authDAO.getAlias(req.getAuthToken());
            followDAO.unfollow(alias, req.getFolloweeAlias());
            return new UnfollowResponse(true, null);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public PostFollowersResponse getAllFollowers(PostStatusRequest req) {
        try {
            List<User> followers = followDAO.getAllFollowers(
                    req
                            .getStatus()
                            .getUser()
                            .getAlias()
            );
            return new PostFollowersResponse(followers);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
