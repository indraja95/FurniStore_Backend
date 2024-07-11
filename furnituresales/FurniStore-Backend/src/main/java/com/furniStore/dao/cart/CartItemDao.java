package com.furniStore.dao.cart;

public interface CartItemDao {
    void saveItem(final CartItem cartItem);
    void deleteItem(final String email, final String id);
    void deleteAllItemByHashKey(final String email);
}
