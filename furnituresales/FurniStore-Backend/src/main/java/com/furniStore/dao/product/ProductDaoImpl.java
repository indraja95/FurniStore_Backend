package com.furniStore.dao.product;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.furniStore.util.DynamoDBPaginationHelper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;

@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao{

    @NonNull
    private DynamoDBMapper mapper;

    @Override
    public void saveProduct(@NonNull final Product product) {
        Validate.notEmpty(product.getProductType(), "Product type must not be empty!");
        Validate.notEmpty(product.getId(), "Product Id must not be empty!");
        mapper.save(product);
    }

    //TODO make this method generic enough to support
    @Override
    public QueryResult getProductsByType(@NonNull final ProductQuery productQuery) {
        Validate.notEmpty(productQuery.getProductType(), "ProductType must not be empty!");
        final Product hashKey = new Product();
        hashKey.setProductType(productQuery.getProductType());

        final DynamoDBQueryExpression<Product> queryExpression = new DynamoDBQueryExpression<Product>()
                .withHashKeyValues(hashKey)
                .withExclusiveStartKey(DynamoDBPaginationHelper.stringToLastEvaluatedKey(productQuery.getLastPageToken()))
                .withLimit(productQuery.getPageLimit())
                .withConsistentRead(false);

        final QueryResultPage<Product> productQueryResultPage = mapper.queryPage(Product.class, queryExpression);

        return QueryResult.builder()
                .pageItemCount(productQueryResultPage.getCount())
                .currentPageToken(DynamoDBPaginationHelper.lastEvaluatedKeyToString(productQueryResultPage.getLastEvaluatedKey()))
                .productList(productQueryResultPage.getResults())
                .build();
    }

    @Override
    public void removeProduct(@NonNull final String productType, @NonNull final String id) {
        final Product product = new Product();
        product.setProductType(productType);
        product.setId(id);
        mapper.delete(product);
    }
}
