package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.dynamodb.injection.StatusServiceModule;
import edu.byu.cs.tweeter.server.service.StatusService;

/**
 * An AWS lambda function that returns the story for a user
 */
public class GetStoryHandler implements RequestHandler<StoryRequest, StoryResponse> {

    /**
     * Gets a user's follower count
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the count.
     */
    @Override
    public StoryResponse handleRequest(StoryRequest request, Context context) {
        Injector injector = Guice.createInjector(new StatusServiceModule());
        StatusService service = injector.getInstance(StatusService.class);
        return service.getStory(request);
    }
}
