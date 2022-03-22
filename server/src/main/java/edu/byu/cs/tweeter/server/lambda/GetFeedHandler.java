package edu.byu.cs.tweeter.server.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.dynamodb.injection.FollowServiceModule;
import edu.byu.cs.tweeter.server.dao.dynamodb.injection.StatusServiceModule;
import edu.byu.cs.tweeter.server.service.FollowService;
import edu.byu.cs.tweeter.server.service.StatusService;

/**
 * An AWS lambda function that returns a user's feed
 */
public class GetFeedHandler implements RequestHandler<FeedRequest, FeedResponse> {

    /**
     * Gets a user's feed
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the feed.
     */
    @Override
    public FeedResponse handleRequest(FeedRequest request, Context context) {
        Injector injector = Guice.createInjector(new StatusServiceModule());
        StatusService service = injector.getInstance(StatusService.class);
        return service.getFeed(request);
    }
}
