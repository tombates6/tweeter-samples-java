package edu.byu.cs.tweeter.server.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.UserRequest;
import edu.byu.cs.tweeter.model.net.response.UserResponse;
import edu.byu.cs.tweeter.server.service.UserService;

/**
 * An AWS lambda function that returns the user's profile
 */
public class GetUserHandler implements RequestHandler<UserRequest, UserResponse> {

    /**
     * Gets a user's profile
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the count.
     */
    @Override
    public UserResponse handleRequest(UserRequest request, Context context) {
        UserService service = new UserService();
        return service.getUserProfile(request);
    }
}
