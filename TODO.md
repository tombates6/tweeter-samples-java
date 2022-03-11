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

[X] Write Lambda Service and FakeData classes
    [X] FollowService
        [X] getFollowers
        [X] getFollowersCount
        [X] getFollowingCount
        [X] isFollower
        [X] unfollow
    [X] StatusService
        [X] getFeed
        [X] getStory
        [X] post
    [X] UserService
        [X] getUserProfile
        [X] logout
        [X] register

[X] Generate .jar file for Lambdas and upload code to Lambdas
    [X] Generate .jar file from the server layer
    [X] Upload .jar file to each Lambda handler and select the correct class

[X] Add error handling, multiple response codes, and a description to each API method

[X] Acceptance Test all the AWS services by hitting the APIGateway endpoints

[X] Modify Client Code to use ServerFacade and ClientCommunicator
    [X] ServerFacade URL and methods
    [x] Integrate ServerFacade with background tasks

[X] Ensure pagination is implemented in the Lambda handlers

[X] Acceptance Test the apps

[X] Integration Tests (success only)
    [X] ServerFacade
        [X] Register
        [X] GetFollowers
        [X] GetFollowingCount and/or GetFollowersCount
    [X] StatusService GetStory

[] UML Sequence Diagram

[] API Swagger Doc