package edu.byu.cs.tweeter.server.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.ArrayList;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.ResultsPage;
import edu.byu.cs.tweeter.server.dao.dynamodb.DynamoDBAuthDAO;
import edu.byu.cs.tweeter.server.dao.dynamodb.DynamoDBStoryDAO;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public class StatusServiceTest {

    private StoryRequest request;
    private ResultsPage<Status> expectedResponse;
    private DynamoDBAuthDAO mockAuthDao;
    private StatusService statusServiceSpy;
    private AuthToken authToken;
    private final User currentUser = new User("FirstName", "LastName", null);

    private final User resultUser1 = new User("FirstName1", "LastName1",
            "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
    private final User resultUser2 = new User("FirstName2", "LastName2",
            "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
    private final User resultUser3 = new User("FirstName3", "LastName3",
            "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

    private final long dateTime = Instant.now().toEpochMilli();

    @Before
    public void setup() throws DataAccessException {
        authToken = new AuthToken();

        expectedResponse = new ResultsPage<>();
        expectedResponse.addValue(new Status("Post 1", resultUser1, dateTime, new ArrayList<>(), new ArrayList<>()));
        expectedResponse.addValue(new Status("Post 2", resultUser2, dateTime, new ArrayList<>(), new ArrayList<>()));
        expectedResponse.addValue(new Status("Post 3", resultUser3, dateTime, new ArrayList<>(), new ArrayList<>()));
        expectedResponse.setHasLastItem(false);

        // Setup a request object to use in the tests
        request = new StoryRequest(authToken, currentUser.getAlias(), 3, null);

        // Setup a mock StoryDAO that will return known responses
        DynamoDBStoryDAO mockStoryDao = Mockito.mock(DynamoDBStoryDAO.class);
        Mockito.when(mockStoryDao.getStory(request.getUserAlias(), request.getLimit(), request.getLastStatus())).thenReturn(expectedResponse);

        mockAuthDao = Mockito.mock(DynamoDBAuthDAO.class);

        statusServiceSpy = Mockito.spy(StatusService.class);
        Mockito.when(statusServiceSpy.getStoryDAO()).thenReturn(mockStoryDao);
        Mockito.when(statusServiceSpy.getAuthDAO()).thenReturn(mockAuthDao);
    }

    @Test
    public void testGetStory_validRequest_correctResponse() {
        StoryResponse response = statusServiceSpy.getStory(request);
        Assert.assertEquals(expectedResponse.getValues(), response.getStory());
        Assert.assertEquals(expectedResponse.hasLastItem(), response.getHasMorePages());
    }

    @Test
    public void testGetStory_validRequest_hasMoreStatuses() {
        expectedResponse.setHasLastItem(true);

        StoryResponse response = statusServiceSpy.getStory(request);
        Assert.assertEquals(expectedResponse.getValues(), response.getStory());
        Assert.assertEquals(expectedResponse.hasLastItem(), response.getHasMorePages());
    }

    @Test(expected = RuntimeException.class)
    public void testGetStory_invalidAuthToken_correctResponse() throws DataAccessException {
        Mockito.doThrow(DataAccessException.class).when(mockAuthDao).validateToken(authToken);
        statusServiceSpy.getStory(request);
    }
}
