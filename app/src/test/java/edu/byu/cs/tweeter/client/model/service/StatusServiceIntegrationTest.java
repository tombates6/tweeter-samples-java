package edu.byu.cs.tweeter.client.model.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.observer.IPagedTaskObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;

public class StatusServiceIntegrationTest {
//    FIXME Method getMainLooper in android.os.Looper not mocked
//    Also, refactor so the ServerFacade is in the service layer. Maybe?

    private User currentUser;
    private AuthToken currentAuthToken;

    private StatusService statusServiceSpy;
    private StoryObserver observer;

    private CountDownLatch countDownLatch;

    /**
     * Create a StatusService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @Before
    public void setup() {
        currentUser = new User("FirstName", "LastName", null);
        currentAuthToken = new AuthToken();

        statusServiceSpy = spy(new StatusService());

        // Setup an observer for the StatusService
        observer = new StoryObserver();

        // Prepare the countdown latch
        resetCountDownLatch();
    }

    private void resetCountDownLatch() {
        countDownLatch = new CountDownLatch(1);
    }

    private void awaitCountDownLatch() throws InterruptedException {
        countDownLatch.await();
        resetCountDownLatch();
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

            countDownLatch.countDown();
        }

        @Override
        public void handleException(Exception exception) {

            this.success = false;
            this.message = null;
            this.statuses = null;
            this.hasMorePages = false;
            this.exception = exception;

            countDownLatch.countDown();
        }

        @Override
        public void handleSuccess(List<Status> items, boolean hasMorePages) {
            this.success = true;
            this.message = null;
            this.statuses = items;
            this.hasMorePages = hasMorePages;
            this.exception = null;

            countDownLatch.countDown();

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

    /**
     * Verify that for successful requests, the {@link StatusService#getStory}
     * asynchronous method eventually returns the same result as the {@link ServerFacade}.
     */
    @Test
    public void testGetStory_validRequest_correctResponse() throws InterruptedException {
        statusServiceSpy.getStory(currentAuthToken, currentUser, 3, null, observer);
        awaitCountDownLatch();

        List<Status> expectedStatuses = new FakeData().getFakeStatuses().subList(0, 3);
        assertTrue(observer.isSuccess());
        assertNull(observer.getMessage());
        assertEquals(expectedStatuses, observer.getStatuses());
        assertTrue(observer.getHasMorePages());
        assertNull(observer.getException());
    }
}
