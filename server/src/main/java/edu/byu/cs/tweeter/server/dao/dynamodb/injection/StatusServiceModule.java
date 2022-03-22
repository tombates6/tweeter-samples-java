package edu.byu.cs.tweeter.server.dao.dynamodb.injection;

import com.google.inject.AbstractModule;

import edu.byu.cs.tweeter.server.dao.IAuthDAO;
import edu.byu.cs.tweeter.server.dao.IFeedDAO;
import edu.byu.cs.tweeter.server.dao.IFollowDAO;
import edu.byu.cs.tweeter.server.dao.IStoryDAO;
import edu.byu.cs.tweeter.server.dao.dynamodb.DynamoDBAuthDAO;
import edu.byu.cs.tweeter.server.dao.dynamodb.DynamoDBFeedDAO;
import edu.byu.cs.tweeter.server.dao.dynamodb.DynamoDBFollowDAO;
import edu.byu.cs.tweeter.server.dao.dynamodb.DynamoDBStoryDAO;

public class StatusServiceModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IAuthDAO.class).to(DynamoDBAuthDAO.class);
        bind(IStoryDAO.class).to(DynamoDBStoryDAO.class);
        bind(IFeedDAO.class).to(DynamoDBFeedDAO.class);
    }
}
