package edu.byu.cs.tweeter.client.model.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.observer.IAuthObserver;
import edu.byu.cs.tweeter.client.model.service.observer.IPagedTaskObserver;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.client.presenter.view.MainView;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;

public class PostStatusIntegrationTest {

    private User currentUser;
    private AuthToken currentAuthToken;


    private AuthService authServiceSpy;
    private MainView view;
    private MainPresenter mainPresenterSpy;
    private StatusService statusServiceSpy;
    private StoryObserver storyObserver;
    private AuthObserver loginObserver;

    private CountDownLatch storyCountDownLatch;
    private CountDownLatch authCountDownLatch;
    private CountDownLatch postCountDownLatch;

    /**
     * Create a StatusService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @Before
    public void setup() {
        view = Mockito.mock(MainView.class);
        mainPresenterSpy = spy(new MainPresenter(view));

        authServiceSpy = spy(new AuthService());
        statusServiceSpy = spy(new StatusService());

        // Setup an observer for the StatusService
        storyObserver = new StoryObserver();

        loginObserver = new AuthObserver();

        // Prepare the countdown latch
        resetCountDownLatches();
    }

    private void resetCountDownLatches() {
        storyCountDownLatch = new CountDownLatch(1);
        authCountDownLatch = new CountDownLatch(1);
        postCountDownLatch = new CountDownLatch(1);
    }

    private void resetPostCountDownLatch() {
        postCountDownLatch = new CountDownLatch(1);
    }

    private void resetAuthCountDownLatch() {
        authCountDownLatch = new CountDownLatch(1);
    }

    private void resetStoryCountDownLatch() {
        storyCountDownLatch = new CountDownLatch(1);
    }

    private void awaitStoryCountDownLatch() throws InterruptedException {
        storyCountDownLatch.await();
        resetStoryCountDownLatch();
    }

    private void awaitPostCountDownLatch() throws InterruptedException {
        postCountDownLatch.await();
        resetPostCountDownLatch();
    }

    private void awaitAuthCountDownLatch() throws InterruptedException {
        authCountDownLatch.await();
        resetAuthCountDownLatch();
    }

    /**
     * A {@link IPagedTaskObserver} implementation that can be used to get the values
     * eventually returned by an asynchronous call on the {@link StatusService}. Counts down
     * on the countDownLatch so tests can wait for the background thread to call a method on the
     * observer.
     */
    private class StoryObserver implements IPagedTaskObserver<Status> {
        private boolean success;
        private String message;
        private List<Status> statuses;
        private boolean hasMorePages;
        private Exception exception;

        @Override
        public void handleFailure(String message) {
            this.success = false;
            this.message = message;
            this.statuses = null;
            this.hasMorePages = false;
            this.exception = null;

            storyCountDownLatch.countDown();
        }

        @Override
        public void handleException(Exception exception) {

            this.success = false;
            this.message = null;
            this.statuses = null;
            this.hasMorePages = false;
            this.exception = exception;

            storyCountDownLatch.countDown();
        }

        @Override
        public void handleSuccess(List<Status> items, boolean hasMorePages) {
            this.success = true;
            this.message = null;
            this.statuses = items;
            this.hasMorePages = hasMorePages;
            this.exception = null;

            storyCountDownLatch.countDown();

        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public List<Status> getStatuses() {
            return statuses;
        }

        public boolean getHasMorePages() {
            return hasMorePages;
        }

        public Exception getException() {
            return exception;
        }
    }

    private class AuthObserver implements IAuthObserver {

        @Override
        public void handleFailure(String message) {

        }

        @Override
        public void handleException(Exception exception) {

        }

        @Override
        public void handleSuccess(User loggedInUser, AuthToken authToken) {
            currentUser = loggedInUser;
            currentAuthToken = authToken;
        }
    }

    /**
     * Verify that for successful requests, the {@link StatusService#getStory}
     * asynchronous method eventually returns the same result as the {@link ServerFacade}.
     */
    @Test
    public void testGetStory_validRequest_correctResponse() throws InterruptedException {
        awaitStoryCountDownLatch();

        List<Status> expectedStatuses = new FakeData().getFakeStatuses().subList(0, 3);
        List<Status> actualStatuses = storyObserver.getStatuses();
        assertTrue(storyObserver.isSuccess());
        assertNull(storyObserver.getMessage());
        for (int i = 0; i < 3; i++){
            assertEquals(expectedStatuses.get(i).getPost(), actualStatuses.get(i).getPost());
            assertEquals(expectedStatuses.get(i).getMentions(), actualStatuses.get(i).getMentions());
            assertEquals(expectedStatuses.get(i).getUrls(), actualStatuses.get(i).getUrls());
            assertEquals(expectedStatuses.get(i).getUser(), actualStatuses.get(i).getUser());
        }
        assertTrue(storyObserver.getHasMorePages());
        assertNull(storyObserver.getException());
    }

    @Test
    public void testPostStory_validRequest_correctResponse() throws InterruptedException {
        authServiceSpy.login("@LastFollower", "lastfollower", loginObserver);
        awaitAuthCountDownLatch();

        mainPresenterSpy.postStatus(testPost);
        awaitPostCountDownLatch();

        statusServiceSpy.getStory(currentAuthToken, currentUser, 1, null, storyObserver);
        awaitStoryCountDownLatch();

        List<Status> statuses = storyObserver.getStatuses();
        assertTrue(storyObserver.isSuccess());
        assertNull(storyObserver.getMessage());

        assertEquals(statuses.get(0).getPost(), testPost);




    }
}
