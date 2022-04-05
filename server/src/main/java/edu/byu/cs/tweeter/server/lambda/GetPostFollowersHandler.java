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
import edu.byu.cs.tweeter.model.net.request.UpdateFeedsRequest;
import edu.byu.cs.tweeter.model.net.response.PostFollowersResponse;
import edu.byu.cs.tweeter.server.dao.dynamodb.injection.FollowServiceModule;
import edu.byu.cs.tweeter.server.service.FollowService;

/**
 * An AWS lambda function that handles the first level of SQS queues involved in posting a status
 */
public class GetPostFollowersHandler implements RequestHandler<SQSEvent, Void> {
    private final String UpdateFeedsQueueURL = "https://sqs.us-west-2.amazonaws.com/619607277720/UpdateFeedsQueue";
    /**
     * Gets all followers for a user in preparation to update their feeds with a new post.
     * Forwards the request to another queue.
     *
     * @param event contains the data required to fulfill the request.
     * @param context the lambda context.
     */
    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        Injector injector = Guice.createInjector(new FollowServiceModule());
        FollowService service = injector.getInstance(FollowService.class);

        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            PostStatusRequest postReq = JsonSerializer.deserialize(msg.getBody(), PostStatusRequest.class);
            PostFollowersResponse followers = service.getAllFollowers(postReq);

            // Send new status through the updating pipeline
            String updateReqString = JsonSerializer.serialize(new UpdateFeedsRequest(postReq.getStatus(), followers.getAliases()));
            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(UpdateFeedsQueueURL)
                    .withMessageBody(updateReqString);

            AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
            sqs.sendMessage(send_msg_request);
        }
        return null;
    }
}