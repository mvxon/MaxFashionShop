package com.strigalev.maxfashionshop.dto;

import com.strigalev.maxfashionshop.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDetailsDTO {
    private String title;
    private Long productId;
    private BigDecimal price;
    private BigDecimal amount;
    private Double sum;

    public BucketDetailsDTO(Product product) {
        this.title = product.getTitle();
        this.amount = new BigDecimal(1.0);
        this.price = product.getPrice();
        this.sum = Double.valueOf(product.getPrice().toString());
        this.productId = product.getId();
    }
}
