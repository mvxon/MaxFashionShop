package com.strigalev.maxfashionshop.service;

import com.strigalev.maxfashionshop.dao.BucketRepository;
import com.strigalev.maxfashionshop.dao.ProductRepository;
import com.strigalev.maxfashionshop.domain.Bucket;
import com.strigalev.maxfashionshop.domain.Product;
import com.strigalev.maxfashionshop.domain.User;
import com.strigalev.maxfashionshop.dto.BucketDTO;
import com.strigalev.maxfashionshop.dto.BucketDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService {
    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Autowired
    public BucketServiceImpl(BucketRepository bucketRepository,
                             ProductRepository productRepository,
                             UserService userService) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Bucket createBucket(User user, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        bucket.setProducts(productList);
        return bucketRepository.save(bucket);
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    @Override
    public void addProducts(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectRefProductsByIds(productIds));
        bucket.setProducts(newProductList);
        bucketRepository.save(bucket);
    }

    @Override
    public void deleteProduct(Bucket bucket, Long productId) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductList = new ArrayList<>();
        boolean flag = false;
        for (Product product : products) {
            if (Objects.equals(product.getId(), productId) && !flag) {
                flag = true;
                continue;
            }
            newProductList.add(product);
        }
        bucket.setProducts(newProductList);
        bucketRepository.save(bucket);
    }

    @Override
    public void clearBucket(Bucket bucket) {
        List<Product> products = new ArrayList<>();
        bucket.setProducts(products);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketDTO getBucketByUser(String name) {
        User user = userService.findByName(name);
        if (user == null || user.getBucket() == null) {
            return new BucketDTO();
        }
        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailsDTO> mapByProductId = new HashMap<>();
        List<Product> products = user.getBucket().getProducts();
        for (Product product : products) {
            BucketDetailsDTO details = mapByProductId.get(product.getId());
            if (details == null) {
                mapByProductId.put(product.getId(), new BucketDetailsDTO(product));
            } else {
                details.setAmount(details.getAmount().add(new BigDecimal(1.0)));
                details.setSum(details.getSum() + Double.valueOf(product.getPrice().toString()));
            }
        }
        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDTO.aggregate();
        return bucketDTO;
    }
}
