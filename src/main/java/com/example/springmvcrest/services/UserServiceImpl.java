package com.example.springmvcrest.services;

import com.example.springmvcrest.api.mapper.ProductMapper;
import com.example.springmvcrest.api.model.ProductDTO;
import com.example.springmvcrest.domain.Category;
import com.example.springmvcrest.domain.Product;
import com.example.springmvcrest.domain.Tags;
import com.example.springmvcrest.domain.User;
import com.example.springmvcrest.domain.dto.UserDto;
import com.example.springmvcrest.repositories.CategoryRepository;
import com.example.springmvcrest.repositories.ProductRepository;
import com.example.springmvcrest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    
    public UserServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository, ProductMapper productMapper) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;

        this.productMapper = productMapper;
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User saveUser(UserDto userDto) {
        Set<Category> categories=new HashSet<>();
        for (String name:userDto.getCategories()) {
            categories.add(categoryRepository.findByName(name));
        }

        return userRepository.save(new User(userDto.getEmail(),userDto.getUserName(),userDto.getPassWord1(),userDto.getPassWord2(),categories));
    }

    @Override
    public User findByEmailAndPassWord(String email, String password1) {
        return userRepository.findByEmailAndPassWord1(email,password1);
    }

    @Override

    public List<Product> userCategoriesList(Long id) {
        if (userRepository.findById(id).isPresent()){
            User user=userRepository.findById(id).get();
            Set<Category> categories=user.getCategories();

            List<Product> productList=productRepository.findByParentProductNullAndCategoriesIn(categories);
            Set<Product> set = new HashSet<>(productList);
            productList.clear();
            productList.addAll(set);
            return productList;

        }
        return null;
    }

    @Override
    public ProductDTO createNewProduct(ProductDTO productDTO) {


        Product product = productMapper.productDtoToProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.productToProductDTO(savedProduct);
    }
}
