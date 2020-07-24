package com.api.rest.jwt.dao;



import com.api.rest.jwt.model.Authority;
import com.api.rest.jwt.model.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(AuthorityName name);

}