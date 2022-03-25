package com.strigalev.maxfashionshop.controllers;

import com.strigalev.maxfashionshop.dto.BucketDTO;
import com.strigalev.maxfashionshop.service.BucketService;
import com.strigalev.maxfashionshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class BucketController {
    private final BucketService bucketService;
    private final ProductService productService;

    @Autowired
    public BucketController(BucketService bucketService, ProductService productService) {
        this.bucketService = bucketService;
        this.productService = productService;
    }

    @GetMapping("/bucket")
    public String aboutBucket(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("bucket", new BucketDTO());
        } else {
            BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDTO);
        }
        return "bucket";
    }
    @GetMapping("/bucket/{id}")
    public String deleteFromBucket(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/bucket";
        }
        productService.deleteFromUserBucket(id, principal.getName());
        return "redirect:/bucket";
    }

    @GetMapping("/bucket/clear")
    public String clearBucket(Principal principal) {
        if (principal == null) {
            return "redirect:/bucket";
        }
        productService.clearUserBucket(principal.getName());
        return "redirect:/bucket";
    }


}
