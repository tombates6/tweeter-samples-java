package edu.byu.cs.tweeter.client.model.net;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.request.UserRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;
import edu.byu.cs.tweeter.model.net.response.UserResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    private static final String SERVER_URL = "https://c625455gjf.execute-api.us-west-2.amazonaws.com/dev";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    /**
     * Performs a login and if successful, returns the logged in user and an auth token.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException {

        return clientCommunicator.doPost("/auth/login", request, null, LoginResponse.class);
    }

    public LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException {

        return clientCommunicator.doPost("/auth/logout", request, null, LogoutResponse.class);
    }

    public RegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException {

        return clientCommunicator.doPost("/auth/register", request, null, RegisterResponse.class);
    }

    public UserResponse getUserProfile(UserRequest request) throws IOException, TweeterRemoteException {

        return clientCommunicator.doPost("/user/get-profile", request, null, UserResponse.class);
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowingResponse getFollowing(FollowingRequest request) throws IOException, TweeterRemoteException {

        return clientCommunicator.doPost("/follow/get-following", request, null, FollowingResponse.class);
    }

    public FollowersResponse getFollowers(FollowersRequest request) throws IOException, TweeterRemoteException {

        return clientCommunicator.doPost("/follow/get-followers", request, null, FollowersResponse.class);
    }

    public FollowingCountResponse getFollowingCount(FollowingCountRequest request) throws IOException, TweeterRemoteException {

        return clientCommunicator.doPost("/follow/get-following-count", request, null, FollowingCountResponse.class);
    }

    public FollowersCountResponse getFollowersCount(FollowersCountRequest request) throws IOException, TweeterRemoteException {

        return clientCommunicator.doPost("/follow/get-followers-count", request, null, FollowersCountResponse.class);
    }

    public FollowResponse follow(FollowRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/follow/follow-user", request, null, FollowResponse.class);
    }

    public UnfollowResponse unfollow(UnfollowRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/follow/unfollow-user", request, null, UnfollowResponse.class);
    }

    public IsFollowerResponse isFollower(IsFollowerRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/follow/is-follower", request, null, IsFollowerResponse.class);
    }

    public FeedResponse getFeed(FeedRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/status/get-feed", request, null, FeedResponse.class);
    }

    public StoryResponse getStory(StoryRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/status/get-story", request, null, StoryResponse.class);
    }

    public PostStatusResponse postStatus(PostStatusRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/status/post-status", request, null, PostStatusResponse.class);
    }
}