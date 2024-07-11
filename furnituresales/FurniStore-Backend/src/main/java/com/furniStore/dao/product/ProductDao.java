package com.furniStore.dao.product;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

public interface ProductDao {
    void saveProduct(final Product product);

    QueryResult getProductsByType(final ProductQuery productQuery);

    void removeProduct(final String productType, final String id);

    @Value
    @Builder
    class ProductQuery {
        @NonNull
        private String productType;

        private final String lastPageToken;

        @NonNull
        private final Integer pageLimit;
    }

    @Value
    @Builder
    class QueryResult {
        private final List<Product> productList;

        @NonNull
        private final Integer pageItemCount;

        private final String currentPageToken;
    }
}
