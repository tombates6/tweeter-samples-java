package edu.byu.cs.tweeter.server.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.FollowersCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersCountResponse;
import edu.byu.cs.tweeter.server.service.FollowService;

/**
 * An AWS lambda function that returns the count of a user's followers
 */
public class GetFollowersCountHandler implements RequestHandler<FollowersCountRequest, FollowersCountResponse> {

    /**
     * Gets a user's follower count
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the count.
     */
    @Override
    public FollowersCountResponse handleRequest(FollowersCountRequest request, Context context) {
        FollowService service = new FollowService();
        return service.getFollowersCount(request);
    }
}
