[X] Create API in APIGateway

[X] Create Empty Lambda functions for each Task

[X] Connect APIGateway to Empty Lambdas

[X] Test connection to Lambda functions through APIGateway

[X] Write Request/Response classes
    [X] Follow
    [X] GetFeed
    [X] GetFollowers
    [X] GetFollowersCount
    [X] GetFollowing
    [X] GetFollowingCount
    [X] GetStory
    [X] GetUser
    [X] IsFollower
    [X] Login
    [X] Logout
    [x] PostStatus
    [X] Register
    [X] Unfollow

[X] Write Lambda Handler classes
    [X] Follow
    [X] GetFeed
    [X] GetFollowers
    [X] GetFollowersCount
    [X] GetFollowing
    [X] GetFollowingCount
    [X] GetStory
    [X] GetUser
    [X] IsFollower
    [X] Login
    [X] Logout
    [X] PostStatus
    [X] Register
    [X] Unfollow

[] Write Lambda Service and FakeData classes
    [] FollowService
        [] getFollowersCount
        [] getFollowingCount
        [] isFollower
        [] unfollow
    [] StatusService
        [] getFeed
        [] getStory
        [] post
    [] UserService
        [] getUserProfile
        [] logout
        [] register

[] Acceptance Test all the AWS services by hitting the APIGateway endpoints

[] Modify Client Code to use ServerFacade and ClientCommunicator

[] Integration Tests

[] UML Sequence Diagram

[] Print API Swagger Doc