package com.strigalev.maxfashionshop.service;

import com.strigalev.maxfashionshop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();

    void addToUserBucket(Long productId, String username);

    void deleteFromUserBucket(Long productId, String username);

    void clearUserBucket(String username);
}
