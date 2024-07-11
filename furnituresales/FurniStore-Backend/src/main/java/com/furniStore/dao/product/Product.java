package com.furniStore.dao.product;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "Product")
public class Product {
    @DynamoDBHashKey
    private String productType;
    @DynamoDBRangeKey
    private String id;
    private String description;
    private double price;
    private String imageUrl;
}
