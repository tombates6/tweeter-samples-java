package edu.byu.cs.tweeter.server.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.server.dao.dynamodb.injection.FollowServiceModule;
import edu.byu.cs.tweeter.server.service.FollowService;

/**
 * An AWS lambda function that returns whether one user follows another
 */
public class IsFollowerHandler implements RequestHandler<IsFollowerRequest, IsFollowerResponse> {

    /**
     * Checks for the following status
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the count.
     */
    @Override
    public IsFollowerResponse handleRequest(IsFollowerRequest request, Context context) {
        Injector injector = Guice.createInjector(new FollowServiceModule());
        FollowService service = injector.getInstance(FollowService.class);
        return service.isFollower(request);
    }
}
