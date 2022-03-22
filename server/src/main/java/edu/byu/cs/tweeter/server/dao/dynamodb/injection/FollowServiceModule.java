package edu.byu.cs.tweeter.server.dao.dynamodb.injection;

import com.google.inject.AbstractModule;

import edu.byu.cs.tweeter.server.dao.IAuthDAO;
import edu.byu.cs.tweeter.server.dao.IFollowDAO;
import edu.byu.cs.tweeter.server.dao.dynamodb.DynamoDBAuthDAO;
import edu.byu.cs.tweeter.server.dao.dynamodb.DynamoDBFollowDAO;

public class FollowServiceModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IAuthDAO.class).to(DynamoDBAuthDAO.class);
        bind(IFollowDAO.class).to(DynamoDBFollowDAO.class);
    }
}
