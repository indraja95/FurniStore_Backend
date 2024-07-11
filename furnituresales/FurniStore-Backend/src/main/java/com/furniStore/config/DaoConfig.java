package com.furniStore.config;

import com.furniStore.dao.cart.CartItemDao;
import com.furniStore.dao.cart.CartItemDaoImpl;
import com.furniStore.dao.login.LoginDao;
import com.furniStore.dao.login.LoginDaoImpl;
import com.furniStore.dao.product.ProductDao;
import com.furniStore.dao.product.ProductDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(DynamoDBConfig.class)
@Configuration
public class DaoConfig {

    @Autowired
    private DynamoDBConfig dynamoDBConfig;

    @Bean
    public LoginDao loginDao() {
        return new LoginDaoImpl(dynamoDBConfig.dynamoDBMapper());
    }

    @Bean
    public ProductDao productDao() {
        return new ProductDaoImpl(dynamoDBConfig.dynamoDBMapper());
    }

    @Bean
    public CartItemDao cartItemDao() {
        return new CartItemDaoImpl(dynamoDBConfig.dynamoDBMapper());
    }
}
