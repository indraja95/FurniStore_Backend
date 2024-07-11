package com.furniStore.controller;

import com.furniStore.dao.product.Product;
import com.furniStore.dao.product.ProductDao;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("product-page")
@RequiredArgsConstructor
public class ProductController {

    @NonNull
    private ProductDao productDao;

    @PostMapping("/product")
    public void saveProduct(@RequestBody final Product product) {
        if (product.getId() == null) {
            product.setId(UUID.randomUUID().toString());
        }
        productDao.saveProduct(product);
    }

    @GetMapping("/product")
    public ProductDao.QueryResult getProductsByType(@RequestParam final String productType,
                                                    @RequestParam final Integer pageLimit,
                                                    @RequestParam(required = false) final String lastPageToken) {
        final ProductDao.ProductQuery query = ProductDao.ProductQuery.builder()
                .productType(productType)
                .pageLimit(pageLimit)
                .lastPageToken(lastPageToken)
                .build();
        return productDao.getProductsByType(query);
    }

    @DeleteMapping("/product")
    public void deleteProduct(@RequestParam final String productType, @RequestParam final String id) {
        productDao.removeProduct(productType, id);
    }
}
