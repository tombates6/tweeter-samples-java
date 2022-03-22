package edu.byu.cs.tweeter.server.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;
import edu.byu.cs.tweeter.server.dao.dynamodb.injection.FollowServiceModule;
import edu.byu.cs.tweeter.server.service.FollowService;

/**
 * An AWS lambda function that returns the count of users a user follows
 */
public class GetFollowingCountHandler implements RequestHandler<FollowingCountRequest, FollowingCountResponse> {

    /**
     * Gets a user's followee count
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the count.
     */
    @Override
    public FollowingCountResponse handleRequest(FollowingCountRequest request, Context context) {
        Injector injector = Guice.createInjector(new FollowServiceModule());
        FollowService service = injector.getInstance(FollowService.class);
        return service.getFollowingCount(request);
    }
}
