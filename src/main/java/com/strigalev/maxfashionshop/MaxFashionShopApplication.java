package com.strigalev.maxfashionshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MaxFashionShopApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context= SpringApplication.run(MaxFashionShopApplication.class, args);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        System.out.println(passwordEncoder.encode("pass"));
    }

}
