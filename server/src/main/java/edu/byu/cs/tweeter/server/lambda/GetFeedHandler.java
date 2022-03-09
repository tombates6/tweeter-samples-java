package edu.byu.cs.tweeter.server.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.server.service.FollowService;

/**
 * An AWS lambda function that returns a user's feed
 */
public class FeedHandler implements RequestHandler<FeedRequest, FeedResponse> {

    /**
     * Gets a user's feed
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the followee.
     */
    @Override
    public FeedResponse handleRequest(FeedRequest request, Context context) {
        FollowService service = new FollowService();
        return service.getFeed(request);
    }
}
