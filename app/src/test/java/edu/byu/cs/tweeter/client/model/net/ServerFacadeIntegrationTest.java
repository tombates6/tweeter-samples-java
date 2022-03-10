package edu.byu.cs.tweeter.client.model.net;

import org.junit.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowersCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

public class ServerFacadeIntegrationTest {
    private final AuthToken authToken = new AuthToken();
    private final String alias = "@allen";
    private final String firstName = "Allen";
    private final String lastName = "Anderson";
    private final String password = "password";
    private final String image = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User testUser = new User(firstName, lastName, alias, image);
    private final int limit = 10;

    private final RegisterRequest goodRegisterReq = new RegisterRequest(
        firstName,
        lastName,
        alias,
        password,
        image
    );
    private final RegisterRequest badRegisterReq = new RegisterRequest(
        null,
        null,
        null,
        null,
        null
    );
    private final FollowersRequest followersReq = new FollowersRequest(
            authToken,
            alias,
            limit,
            null
    );
    private final FollowingCountRequest followingCountReq = new FollowingCountRequest(authToken, alias);
    private final FollowersCountRequest followersCountReq = new FollowersCountRequest(authToken, alias);
    private final ServerFacade server = new ServerFacade();


    @Test
    public void registerSuccess() throws IOException, TweeterRemoteException {
        RegisterResponse res = server.register(goodRegisterReq);
        assert(res.isSuccess());
        assert(res.getLoginResult().getFirst().equals(testUser));
        assert(res.getLoginResult().getSecond().token == null);
        assert(res.getLoginResult().getSecond().datetime == null);
    }

    @Test
    public void registerFailure() throws IOException, TweeterRemoteException {
        RegisterResponse res = server.register(badRegisterReq);
        assert(!res.isSuccess());
        assert(res.getLoginResult().getFirst().equals(testUser));
    }

    @Test
    public void registerException() throws IOException, TweeterRemoteException {
        RegisterResponse res = server.register(goodRegisterReq);
        assert(res.isSuccess());
        assert(res.getLoginResult().getFirst().equals(testUser));
        assert(res.getLoginResult().getSecond().token == null);
        assert(res.getLoginResult().getSecond().datetime == null);
    }

    @Test
    public void getFollowersSuccess() throws IOException, TweeterRemoteException {
        FollowersResponse res = server.getFollowers(followersReq);
        assert(res.isSuccess());
        assert(res.getFollowers().size() == limit);
    }

    @Test
    public void getFollowingCount() throws IOException, TweeterRemoteException {
        FollowingCountResponse res = server.getFollowingCount(followingCountReq);
        assert(res.isSuccess());
        assert(res.getCount() == 21);
    }

    @Test
    public void getFollowersCount() throws IOException, TweeterRemoteException {
        FollowersCountResponse res = server.getFollowersCount((followersCountReq));
        assert(res.isSuccess());
        assert(res.getCount() == 21);
    }
}
