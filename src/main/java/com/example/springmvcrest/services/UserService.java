package com.example.springmvcrest.services;

import com.example.springmvcrest.api.model.ProductDTO;
import com.example.springmvcrest.domain.Product;
import com.example.springmvcrest.domain.User;
import com.example.springmvcrest.domain.dto.UserDto;

import java.util.List;


public interface UserService {

    /*User findUserById(Long id);
    User findUserByEmail(String email);*/
    User findByEmail(String email);
    List<User> findAllUsers();
    User saveUser(User user);
    User saveUser(UserDto userDto);
    User findByEmailAndPassWord(String email,String password1);
    List<Product> userCategoriesList(Long id);

    ProductDTO createNewProduct(ProductDTO productDTO);
}
