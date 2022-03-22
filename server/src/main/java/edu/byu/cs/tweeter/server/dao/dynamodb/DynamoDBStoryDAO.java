package edu.byu.cs.tweeter.server.dao.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.IStoryDAO;
import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public class DynamoDBStoryDAO implements IStoryDAO {
    private static final String TableName = "stories";
    private static final String IndexName = "alias";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    @Override
    public StoryResponse getStory(StoryRequest req) throws DataAccessException {
        assert req.getUserAlias() != null;
        assert req.getLimit() > 0;
        // TODO: Generates dummy data. Replace with a real implementation.
        assert req.getLimit() > 0;
        assert req.getUserAlias() != null;

        List<Status> allStatuses = getDummyStory();
        List<Status> responseStatuses = new ArrayList<>(req.getLimit());

        boolean hasMorePages = false;

        if (req.getLimit() > 0) {
            if (allStatuses != null) {
                int statusIndex = getStatusStartingIndex(req.getLastStatus(), allStatuses);

                for (int limitCounter = 0; statusIndex < allStatuses.size() && limitCounter < req.getLimit(); statusIndex++, limitCounter++) {
                    responseStatuses.add(allStatuses.get(statusIndex));
                }

                hasMorePages = statusIndex < allStatuses.size();
            }
        }

        return new StoryResponse(responseStatuses, hasMorePages);
    }

    @Override
    public PostStatusResponse postStatus(PostStatusRequest req) throws DataAccessException {
        return new PostStatusResponse(true, null);
    }
}
