package edu.byu.cs.tweeter.server.dao;


import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public interface IFollowDAO {
    boolean isFollower(String followerAlias, String followeeAlias) throws DataAccessException;
    ResultsPage<User> getFollowers(String followeeAlias, int limit, String lastFollowerAlias) throws DataAccessException;
    int getFollowersCount(String userAlias) throws DataAccessException;
    ResultsPage<User> getFollowing(String followerAlias, int limit, String lastFolloweeAlias) throws DataAccessException;
    int getFollowingCount(String userAlias) throws DataAccessException;
    void follow(String followerAlias, String followeeAlias) throws DataAccessException;
    void unfollow(String followerAlias, String followeeAlias) throws DataAccessException;
}
