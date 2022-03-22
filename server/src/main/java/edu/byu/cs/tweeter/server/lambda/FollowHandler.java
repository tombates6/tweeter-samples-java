package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.server.dao.dynamodb.injection.FollowServiceModule;
import edu.byu.cs.tweeter.server.service.FollowService;

/**
 * An AWS lambda function that follows a User.
 */
public class FollowHandler implements RequestHandler<FollowRequest, FollowResponse> {

    /**
     * Follows a user that the user specified in the request.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the followee.
     */
    @Override
    public FollowResponse handleRequest(FollowRequest request, Context context) {
        Injector injector = Guice.createInjector(new FollowServiceModule());
        FollowService service = injector.getInstance(FollowService.class);
        return service.follow(request);
    }
}
