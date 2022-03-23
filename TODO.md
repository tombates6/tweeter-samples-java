# Milestone 4A
[X] Create DAO interfaces for each DynamoDB table. Have read/write methods. Do this so it's DB agnostic.
    [X] IUserDAO
    [X] IFollowDAO
    [X] IAuthDAO
    [X] IStoryDAO
    [X] IFeedDAO
[X] Create a package of classes that implement DAO interfaces using DynamoDB
    [X] DynamoDBUserDAO
    [X] DynamoDBFollowDAO
    [X] DynamoDBAuthDAO
    [X] DynamoDBStoryDAO
    [X] DynamoDBFeedDAO
[X] Implement dependency injection to create DAOs and use them in service layer
[X] Create DynamoDB tables and indices
    [X] follows - follower/followee alias, first/last names, image URL
    [X] users - alias, hashed password, first name, last name, image URL, counts
    [X] session/authtokens - token, timestamp, alias
    [X] stories - alias, timestamp, post, urls, mentions
    [X] feeds - owner alias, timestamp, author alias, post content, more?
[X] Implement DAOs in service layer (get rid of errors)
[X] Pre-compute every user's feed
[X] Upload profile pictures to S3
[] Store hashes of user passwords
[] Expire auth tokens after N minutes of inactivity
[] Test and work out bugs