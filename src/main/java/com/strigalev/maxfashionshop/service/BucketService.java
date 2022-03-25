package com.strigalev.maxfashionshop.service;

import com.strigalev.maxfashionshop.domain.Bucket;
import com.strigalev.maxfashionshop.domain.User;
import com.strigalev.maxfashionshop.dto.BucketDTO;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);

    void addProducts(Bucket bucket, List<Long> productIds);

    void deleteProduct(Bucket bucket, Long productId);

    void clearBucket(Bucket bucket);

    BucketDTO getBucketByUser(String name);
}
