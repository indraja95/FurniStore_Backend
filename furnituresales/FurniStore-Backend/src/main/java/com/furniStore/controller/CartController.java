package com.furniStore.controller;

import com.furniStore.dao.cart.CartItem;
import com.furniStore.dao.cart.CartItemDao;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart-page")
@RequiredArgsConstructor
public class CartController {

    @NonNull
    private CartItemDao cartItemDao;

    @PostMapping("/item")
    @ResponseBody
    public void saveCartItem(@RequestBody final CartItem cartItem) {
        cartItemDao.saveItem(cartItem);
    }

    @DeleteMapping("/item")
    public void deleteCartItem(@RequestParam final String email, @RequestParam final String id) {
        cartItemDao.deleteItem(email, id);
    }

    @DeleteMapping("/items")
    public void deleteAllCartItem(@RequestParam final String email) {
        cartItemDao.deleteAllItemByHashKey(email);
    }
}
