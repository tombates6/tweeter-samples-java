package edu.byu.cs.tweeter.client.model.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

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
    private final FollowersRequest goodFollowersReq = new FollowersRequest(
            authToken,
            alias,
            limit,
            null
    );
    private final FollowersRequest badFollowersReq = new FollowersRequest(
            null,
            null,
            -1,
            null
    );
    private final FollowingCountRequest goodFollowingCountReq = new FollowingCountRequest(authToken, alias);
    private final FollowingCountRequest badFollowingCountReq = new FollowingCountRequest(null, null);
    private final FollowersCountRequest goodFollowersCountReq = new FollowersCountRequest(authToken, alias);
    private final FollowersCountRequest badFollowersCountReq = new FollowersCountRequest(null, null);
    private final ServerFacade server = spy(new ServerFacade());


    @Test
    public void registerSuccess() throws IOException, TweeterRemoteException {
        RegisterResponse res = server.register(goodRegisterReq);
        assertTrue(res.isSuccess());
        assertEquals(res.getLoginResult().getFirst(), testUser);
        assertNull(res.getLoginResult().getSecond().token);
        assertNull(res.getLoginResult().getSecond().timestamp);
    }

    @Test(expected = TweeterRemoteException.class)
    public void registerFailure() throws IOException, TweeterRemoteException {
        verify(server.register(badRegisterReq));
    }

    @Test
    public void getFollowersSuccess() throws IOException, TweeterRemoteException {
        FollowersResponse res = server.getFollowers(goodFollowersReq);
        assertTrue(res.isSuccess());
        assertEquals(res.getFollowers().size(), limit);
    }

    @Test(expected = TweeterRemoteException.class)
    public void getFollowersFailure() throws IOException, TweeterRemoteException {
        verify(server.getFollowers(badFollowersReq));
    }

    @Test
    public void getFollowingCountSuccess() throws IOException, TweeterRemoteException {
        FollowingCountResponse res = server.getFollowingCount(goodFollowingCountReq);
        assertTrue(res.isSuccess());
        assertEquals(21, res.getCount());
    }

    @Test(expected = TweeterRemoteException.class)
    public void getFollowingCountFailure() throws IOException, TweeterRemoteException {
        verify(server.getFollowingCount(badFollowingCountReq));
    }

    @Test
    public void getFollowersCountSuccess() throws IOException, TweeterRemoteException {
        FollowersCountResponse res = server.getFollowersCount((goodFollowersCountReq));
        assertTrue(res.isSuccess());
        assertEquals(21, res.getCount());
    }

    @Test(expected = TweeterRemoteException.class)
    public void getFollowersCountFailure() throws IOException, TweeterRemoteException {
        verify(server.getFollowersCount((badFollowersCountReq)));
    }
}
