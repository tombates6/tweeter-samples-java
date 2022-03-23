package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public interface IImageDAO {
    String uploadImage(String alias, String image) throws DataAccessException;
}
