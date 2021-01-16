package com.example.springmvcrest.repositories;

import com.example.springmvcrest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User findByEmailAndPassWord1(String email,String password1);

}
