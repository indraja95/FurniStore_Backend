package com.furniStore.dao.cart;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;

import java.util.List;

@RequiredArgsConstructor
public class CartItemDaoImpl implements CartItemDao{

    @NonNull
    private DynamoDBMapper mapper;

    @Override
    public void saveItem(@NonNull final CartItem cartItem) {
        Validate.notEmpty(cartItem.getEmail(), "Email of the cart item must not be empty!");
        Validate.notEmpty(cartItem.getId(), "Id of the cart item must not be null!");
        mapper.save(cartItem);
    }

    @Override
    public void deleteItem(@NonNull final String email, @NonNull final String id) {
        final CartItem cartItem = new CartItem();
        cartItem.setEmail(email);
        cartItem.setId(id);
        mapper.delete(cartItem);
    }

    @Override
    public void deleteAllItemByHashKey(@NonNull final String email) {
        final CartItem hashKey = new CartItem();
        hashKey.setEmail(email);

        final DynamoDBQueryExpression<CartItem> queryExpression = new DynamoDBQueryExpression<CartItem>()
                .withHashKeyValues(hashKey)
                .withConsistentRead(false);

        final QueryResultPage<CartItem> cartItemQueryResultPage = mapper.queryPage(CartItem.class, queryExpression);
        final List<CartItem> cartItems = cartItemQueryResultPage.getResults();
        mapper.batchDelete(cartItems);
    }
}
