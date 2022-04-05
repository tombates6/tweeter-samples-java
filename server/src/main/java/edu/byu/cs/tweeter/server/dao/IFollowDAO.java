package edu.byu.cs.tweeter.server.dao;


import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public interface IFollowDAO {
    boolean isFollower(String followerAlias, String followeeAlias) throws DataAccessException;
    ResultsPage<User> getFollowers(String followeeAlias, int limit, String lastFollowerAlias) throws DataAccessException;
    List<String> getAllFollowers(String followeeAlias) throws DataAccessException;
    int getFollowersCount(String userAlias) throws DataAccessException;
    ResultsPage<User> getFollowing(String followerAlias, int limit, String lastFolloweeAlias) throws DataAccessException;
    int getFollowingCount(String userAlias) throws DataAccessException;
    void follow(User follower, User followee) throws DataAccessException;
    void unfollow(String followerAlias, String followeeAlias) throws DataAccessException;
}
