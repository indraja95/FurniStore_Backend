package com.furniStore.util;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemUtils;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.Map;

public class DynamoDBPaginationHelper {
    public static Map<String, AttributeValue> stringToLastEvaluatedKey(final String pageToken) {
        if (pageToken == null) {
            return null;
        }
        final Item item = Item.fromJSON(pageToken);
        return ItemUtils.toAttributeValues(item);
    }

    public static String lastEvaluatedKeyToString(final Map<String, AttributeValue> lastEvaluatedKey) {
        if (lastEvaluatedKey == null) {
            return null;
        }
        final Item item = ItemUtils.toItem(lastEvaluatedKey);
        return item.toJSON();
    }
}
