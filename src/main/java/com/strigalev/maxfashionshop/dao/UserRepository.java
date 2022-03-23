package com.strigalev.maxfashionshop.dao;

import com.strigalev.maxfashionshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
