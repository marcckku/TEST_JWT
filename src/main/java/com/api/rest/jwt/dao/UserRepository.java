package com.api.rest.jwt.dao;

import com.api.rest.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}