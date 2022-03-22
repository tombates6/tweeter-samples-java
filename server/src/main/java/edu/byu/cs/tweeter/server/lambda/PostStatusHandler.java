package edu.byu.cs.tweeter.server.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.dao.dynamodb.injection.StatusServiceModule;
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
        Injector injector = Guice.createInjector(new StatusServiceModule());
        StatusService service = injector.getInstance(StatusService.class);
        return service.post(request);
    }
}
