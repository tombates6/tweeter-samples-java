package edu.byu.cs.tweeter.server.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.server.service.UserService;

/**
 * An AWS lambda function that registers a user.
 */
public class RegisterHandler implements RequestHandler<RegisterRequest, RegisterResponse> {

    /**
     * Registers a user
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the followee.
     */
    @Override
    public RegisterResponse handleRequest(RegisterRequest request, Context context) {
        UserService service = new UserService();
        return service.register(request);
    }
}
