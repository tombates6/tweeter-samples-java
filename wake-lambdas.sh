aws lambda invoke --function-name Tweeter-LoginHandler --payload '{}' response.json > wake-log.txt
echo "1/16 done"
aws lambda invoke --function-name Tweeter-RegisterHandler --payload '{}' response.json >> wake-log.txt
echo "2/16 done"
aws lambda invoke --function-name Tweeter-LogoutHandler --payload '{}' response.json >> wake-log.txt
echo "3/16 done"
aws lambda invoke --function-name Tweeter-GetUserHandler --payload '{}' response.json >> wake-log.txt
echo "4/16 done"
aws lambda invoke --function-name Tweeter-GetFeedHandler --payload '{}' response.json >> wake-log.txt
echo "5/16 done"
aws lambda invoke --function-name Tweeter-GetStoryHandler --payload '{}' response.json >> wake-log.txt
echo "6/16 done"
aws lambda invoke --function-name Tweeter-PostStatusHandler --payload '{}' response.json >> wake-log.txt
echo "7/16 done"
aws lambda invoke --function-name Tweeter-GetFollowingCountHandler --payload '{}' response.json >> wake-log.txt
echo "8/16 done"
aws lambda invoke --function-name Tweeter-GetFollowingHandler --payload '{}' response.json >> wake-log.txt
echo "9/16 done"
aws lambda invoke --function-name Tweeter-GetFollowersCountHandler --payload '{}' response.json >> wake-log.txt
echo "10/16 done"
aws lambda invoke --function-name Tweeter-GetFollowersHandler --payload '{}' response.json >> wake-log.txt
echo "11/16 done"
aws lambda invoke --function-name Tweeter-FollowHandler --payload '{}' response.json >> wake-log.txt
echo "12/16 done"
aws lambda invoke --function-name Tweeter-UnfollowHandler --payload '{}' response.json >> wake-log.txt
echo "13/16 done"
aws lambda invoke --function-name Tweeter-IsFollowerHandler --payload '{}' response.json >> wake-log.txt
echo "14/16 done"
aws lambda invoke --function-name Tweeter-GetPostFollowersHandler --payload '{}' response.json >> wake-log.txt
echo "15/16 done"
aws lambda invoke --function-name Tweeter-UpdateFeedsHandler --payload '{}' response.json >> wake-log.txt
echo "16/16 done"
echo "All lambdas awake"