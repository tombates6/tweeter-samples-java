import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateUsers {
        public static void main(String[] args) {
                AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                        .withRegion("us-west-2")
                        .build();

                DynamoDB dynamoDB = new DynamoDB(client);

                Table table = dynamoDB.getTable("follows");

                //        Create 10,000 followers
                List<Item> items = new ArrayList<>();
                for (
                        int i = 0;
                        i < 17; i++) {
                        Item item = new Item()
                                .withPrimaryKey("follower_handle", "@moreoops" + i, "followee_handle", "@ManyFollowers")
                                .withString("followee_first_name", "Many")
                                .withString("followee_last_name", "Followers")
                                .withString("follower_first_name", "MoreOops")
                                .withString("follower_last_name", String.valueOf(i));
                        items.add(item);
                }

                System.out.println("Items size: " + items.size());

                try {
                        int lastIndex = 0;
                        int nextIndex = 25 < items.size() ? 25 : items.size() - 1;

                        while (lastIndex <= items.size() - 1) {
                                TableWriteItems followsItems = new TableWriteItems("follows")
                                        .withItemsToPut(items.subList(lastIndex, nextIndex));
                                BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(followsItems);
                                do {

                                        // Check for unprocessed keys which could happen if you exceed
                                        // provisioned throughput

                                        Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();

                                        if (outcome.getUnprocessedItems().size() == 0) {
                                                System.out.println("No unprocessed items found");
                                        } else {
                                                System.out.println("Retrieving the unprocessed items");
                                                outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
                                        }

                                } while (outcome.getUnprocessedItems().size() > 0);

                                lastIndex = nextIndex;
                                nextIndex = lastIndex + 25;
                                if (nextIndex >= items.size()) {
                                        nextIndex = items.size() - 1;
                                }
                        }
                } catch (
                        Exception e) {
                        System.err.println("Unable to add item");
                        System.err.println(e.getMessage());
                }
        }
}