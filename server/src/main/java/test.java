import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.request.UserRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.model.net.response.UserResponse;
import edu.byu.cs.tweeter.server.dao.S3ImageDAO;
import edu.byu.cs.tweeter.server.dao.dynamodb.injection.FollowServiceModule;
import edu.byu.cs.tweeter.server.dao.dynamodb.injection.StatusServiceModule;
import edu.byu.cs.tweeter.server.dao.dynamodb.injection.UserServiceModule;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;
import edu.byu.cs.tweeter.server.service.FollowService;
import edu.byu.cs.tweeter.server.service.StatusService;
import edu.byu.cs.tweeter.server.service.UserService;

public class test {
    public static void main(String[] args) {
//        Injector injector = Guice.createInjector(new UserServiceModule());
//        UserService service = injector.getInstance(UserService.class);
//        UserResponse r = service.getUserProfile(new UserRequest("@TomBates", new AuthToken("4e4f5a72-5552-4716-a4d0-6a8c06fc5340",1648148250026L)));

//        Injector injector = Guice.createInjector(new UserServiceModule());
//        UserService service = injector.getInstance(UserService.class);
//        LoginResponse r = service.login(new LoginRequest("@TomBates", "tom"));

//        Injector injector = Guice.createInjector(new FollowServiceModule());
//        FollowService service = injector.getInstance(FollowService.class);
//        FollowersCountResponse r = service.getFollowersCount(new FollowersCountRequest(new AuthToken("4e4f5a72-5552-4716-a4d0-6a8c06fc5340",1648148250026L), "@TomBates"));
        
//        Injector injector = Guice.createInjector(new FollowServiceModule());
//        FollowService service = injector.getInstance(FollowService.class);
//        FollowingCountResponse r = service.getFollowingCount(new FollowingCountRequest(new AuthToken("4e4f5a72-5552-4716-a4d0-6a8c06fc5340",1648148250026L), "@TomBates"));

//        Injector injector = Guice.createInjector(new FollowServiceModule());
//        FollowService service = injector.getInstance(FollowService.class);
//        FollowersResponse r = service.getFollowers(new FollowersRequest(new AuthToken("4e4f5a72-5552-4716-a4d0-6a8c06fc5340",1648148250026L), "@TomBates", 10, null));

//        Injector injector = Guice.createInjector(new FollowServiceModule());
//        FollowService service = injector.getInstance(FollowService.class);
//        FollowingResponse r = service.getFollowing(new FollowingRequest(new AuthToken("4e4f5a72-5552-4716-a4d0-6a8c06fc5340",1648148250026L), "@TomBates", 10, null));

        Injector injector = Guice.createInjector(new StatusServiceModule());
        StatusService service = injector.getInstance(StatusService.class);
        FeedResponse r = service.getFeed(new FeedRequest(new AuthToken("4e4f5a72-5552-4716-a4d0-6a8c06fc5340",1648148250026L), "@TomBates", 10, null));
        
//        Injector injector = Guice.createInjector(new StatusServiceModule());
//        StatusService service = injector.getInstance(StatusService.class);
//        StoryResponse r = service.getStory(new StoryRequest(new AuthToken("4e4f5a72-5552-4716-a4d0-6a8c06fc5340",1648148250026L), "@TomBates", 10, null));
//
//        Injector injector = Guice.createInjector(new StatusServiceModule());
//        StatusService service = injector.getInstance(StatusService.class);
//        StoryResponse r = service.getStory(new StoryRequest(new AuthToken("4e4f5a72-5552-4716-a4d0-6a8c06fc5340",1648148250026L), "@TomBates", 10, null));
        int x = 1;
    }
}
