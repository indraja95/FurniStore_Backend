package com.furniStore.dao.login;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "Login")
public class Login {
    @DynamoDBHashKey
    private String email;
    private String password;
    private String userName;
}
