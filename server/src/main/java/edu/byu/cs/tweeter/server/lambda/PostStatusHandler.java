package edu.byu.cs.tweeter.server.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.service.StatusService;

/**
 * An AWS lambda function that posts a status
 */
public class PostStatusHandler implements RequestHandler<PostStatusRequest, PostStatusResponse> {

    /**
     * Posts a status
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the followee.
     */
    @Override
    public PostStatusResponse handleRequest(PostStatusRequest request, Context context) {
        StatusService service = new StatusService();
        return service.post(request);
    }
}
