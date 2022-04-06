import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.ArrayList;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.JsonSerializer;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.UpdateFeedsRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.dynamodb.injection.StatusServiceModule;
import edu.byu.cs.tweeter.server.dao.dynamodb.injection.UserServiceModule;
import edu.byu.cs.tweeter.server.service.StatusService;
import edu.byu.cs.tweeter.server.service.UserService;

public class test {
    public static void main(String[] args) {
//        Injector injector = Guice.createInjector(new UserServiceModule());
//        UserService service = injector.getInstance(UserService.class);
//        UserResponse r = service.getUserProfile(new UserRequest("@TomBates", new AuthToken("4e4f5a72-5552-4716-a4d0-6a8c06fc5340",1648148250026L)));

//        Injector injector = Guice.createInjector(new UserServiceModule());
//        UserService service = injector.getInstance(UserService.class);
//        LoginResponse r = service.login(new LoginRequest("@testy", "test"));

//        Injector injector = Guice.createInjector(new FollowServiceModule());
//        FollowService service = injector.getInstance(FollowService.class);
//        FollowersCountResponse r = service.getFollowersCount(new FollowersCountRequest(new AuthToken("4e4f5a72-5552-4716-a4d0-6a8c06fc5340",1648148250026L), "@TomBates"));
        
//        Injector injector = Guice.createInjector(new FollowServiceModule());
//        FollowService service = injector.getInstance(FollowService.class);
//        FollowingCountResponse r = service.getFollowingCount(new FollowingCountRequest(new AuthToken("4e4f5a72-5552-4716-a4d0-6a8c06fc5340",1648148250026L), "@TomBates"));

//        Injector injector = Guice.createInjector(new FollowServiceModule());
//        FollowService service = injector.getInstance(FollowService.class);
//        FollowersResponse r = service.getFollowers(new FollowersRequest(new AuthToken("ae7ae897-708d-403e-af7d-be38782f6420",1648156783799L), "@TomBates", 10, "@user13"));

//        Injector injector = Guice.createInjector(new FollowServiceModule());
//        FollowService service = injector.getInstance(FollowService.class);
//        FollowingResponse fr = service.getFollowing(new FollowingRequest(new AuthToken("ae7ae897-708d-403e-af7d-be38782f6420",1648156783799L), "@TomBates", 10, "@user4"));

//        Injector injector = Guice.createInjector(new FollowServiceModule());
//        FollowService service = injector.getInstance(FollowService.class);
//        IsFollowerResponse r = service.isFollower(new Is

//        Injector injector = Guice.createInjector(new StatusServiceModule());
//        StatusService service = injector.getInstance(StatusService.class);
//        FeedResponse r = service.getFeed(new FeedRequest(new AuthToken("4e4f5a72-5552-4716-a4d0-6a8c06fc5340",1648148250026L), "@TomBates", 10, null));
        
//        Injector injector = Guice.createInjector(new StatusServiceModule());
//        StatusService service = injector.getInstance(StatusService.class);
//        StoryResponse r = service.getStory(new StoryRequest(new AuthToken("4e4f5a72-5552-4716-a4d0-6a8c06fc5340",1648148250026L), "@TomBates", 10, null));

//        Injector injector = Guice.createInjector(new StatusServiceModule());
//        StatusService service = injector.getInstance(StatusService.class);
//        PostStatusResponse r = service.post(new PostStatusRequest(new AuthToken("4e4f5a72-5552-4716-a4d0-6a8c06fc5340",1648148250026L),
//                new Status(
//                        "a dummy post",
//                        new User("Tom", "Bates", "@TomBates", "https://tbates6-tweeter-images.s3.us-west-2.amazonaws.com/%40TomBates-image.jpeg"),
//                        1648148250026L,
//                        null,
//                        null
//                )));
//
//        Injector injector = Guice.createInjector(new StatusServiceModule());
//        StatusService service = injector.getInstance(StatusService.class);
//        StoryResponse r = service.getStory(new StoryRequest(new AuthToken("4e4f5a72-5552-4716-a4d0-6a8c06fc5340",1648148250026L), "@TomBates", 10, null));

//        Injector injector = Guice.createInjector(new UserServiceModule());
//        UserService service = injector.getInstance(UserService.class);
//        RegisterResponse r = service.register(new RegisterRequest("Test", "Tester", "@testy", "test", "asdfkjelcnekl/+"));
        Injector injector = Guice.createInjector(new StatusServiceModule());
        StatusService service = injector.getInstance(StatusService.class);
        service.updateAllFeeds(new UpdateFeedsRequest(
                new Status(
                        "test",
                        new User("User", "1", "@user1", null),
                        1649208639341L,
                        null, null
                ),
                new ArrayList<>() {{ add("@LastFollower"); }}
        ));
        int x = 1;
    }
}
