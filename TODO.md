[] Create abstract factories or dependency injection to create DAOs
[] Create DAO interfaces for each DynamoDB table. Have read/write methods. Do this so it's DB agnostic.
    [] UserDAO
[] Create a package of classes that implement DAO interfaces using DynamoDB
[] Create DynamoDB tables and indices
    [] follows - follower/followee alias, first/last names, image URL
    [] users - alias, hashed password, first name, last name, image URL, counts
    [] session/authtokens - token, alias, datetime
    [] stories - alias, datetime, post, urls, mentions
    [] feeds - owner alias, timestamp, author alias, post content, more?
[] Pre-compute every user's feed and store it in DB 
[] Upload profile pictures to S3
[] Store hashes of user passwords
[] Expire auth tokens after N minutes of inactivity