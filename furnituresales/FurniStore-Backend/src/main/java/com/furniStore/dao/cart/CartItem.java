package com.furniStore.dao.cart;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "cartItem")
public class CartItem {
    @DynamoDBHashKey
    private String email;
    @DynamoDBRangeKey
    private String id;
}
