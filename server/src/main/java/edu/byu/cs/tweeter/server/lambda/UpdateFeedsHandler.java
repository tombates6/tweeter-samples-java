package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.byu.cs.tweeter.model.net.JsonSerializer;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.request.UpdateFeedsRequest;
import edu.byu.cs.tweeter.model.net.response.PostFollowersResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.dynamodb.injection.StatusServiceModule;
import edu.byu.cs.tweeter.server.service.StatusService;

public class UpdateFeedsHandler implements RequestHandler<SQSEvent, Void> {
    /**
     * Handles updating all followers' feeds with a new post
     *
     * @param event contains the data required to fulfill the request.
     * @param context the lambda context.
     */
    @Override
    public Void handleRequest(SQSEvent event, Context context) {

        Injector injector = Guice.createInjector(new StatusServiceModule());
        StatusService service = injector.getInstance(StatusService.class);

        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            UpdateFeedsRequest updateReq = JsonSerializer.deserialize(msg.getBody(), UpdateFeedsRequest.class);
            System.out.println(msg.getBody());
//            service.updateAllFeeds(updateReq);
        }
        return null;
    }
}
