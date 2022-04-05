

//        Create 10,000 followers
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
        Item item = new Item()
        .withPrimaryKey("follower_handle", "@user" + i, "followee_handle", "@ManyFollowers")
        .withString("followee_first_name", "Many")
        .withString("followee_last_name", "Followers")
        .withString("follower_first_name", "User")
        .withString("follower_last_name", String.valueOf(i));
        items.add(item);
        }

        System.out.println("Items size: " + items.size());

        try {
        int lastIndex = 0;
        int nextIndex = 25 < items.size() ? 25 : items.size() - 1;

        while (nextIndex <= items.size() - 1) {
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

        lastIndex = nextIndex + 1;
        nextIndex += 25;
        }
        } catch (Exception e) {
        System.err.println("Unable to add item");
        System.err.println(e.getMessage());
        }