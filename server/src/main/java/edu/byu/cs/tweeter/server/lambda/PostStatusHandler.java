package edu.byu.cs.tweeter.server.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.byu.cs.tweeter.model.net.JsonSerializer;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.dao.dynamodb.injection.StatusServiceModule;
import edu.byu.cs.tweeter.server.service.StatusService;

/**
 * An AWS lambda function that posts a status
 */
public class PostStatusHandler implements RequestHandler<PostStatusRequest, PostStatusResponse> {
    private static final String PostStatusQueueURL = "https://sqs.us-west-2.amazonaws.com/619607277720/PostStatusQueue";

    /**
     * Posts a status
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return success.
     */
    @Override
    public PostStatusResponse handleRequest(PostStatusRequest request, Context context) {

        // Update author's request
        Injector injector = Guice.createInjector(new StatusServiceModule());
        StatusService service = injector.getInstance(StatusService.class);
        PostStatusResponse res = service.post(request);

        if (res.isSuccess()) {
            // Send new status through the updating pipeline
            String postReq = JsonSerializer.serialize(request);
            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(PostStatusQueueURL)
                    .withMessageBody(postReq);

            AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
            sqs.sendMessage(send_msg_request);
        }

        return res;
    }
}
