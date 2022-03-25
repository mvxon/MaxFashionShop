package com.strigalev.maxfashionshop.dao;

import com.strigalev.maxfashionshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
