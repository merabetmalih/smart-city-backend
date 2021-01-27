package com.example.springmvcrest.user.service;

import com.example.springmvcrest.product.api.mapper.ProductMapper;
import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.product.domain.Product;
import com.example.springmvcrest.product.repository.CategoryRepository;
import com.example.springmvcrest.product.repository.ProductRepository;
import com.example.springmvcrest.user.api.mapper.UserMapper;
import com.example.springmvcrest.user.api.mapper.UserRegestrationMapper;
import com.example.springmvcrest.user.api.dto.UserRegestrationDto;
import com.example.springmvcrest.user.domain.User;
import com.example.springmvcrest.user.api.dto.UserDto;
import com.example.springmvcrest.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;
    private final UserRegestrationMapper userRegestrationMapper;


    public   Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Boolean isPresentUserByEmail(String email)
    {
        return userRepository.findByEmail(email).isPresent();
    }

    public UserRegestrationDto saveUser(UserDto userDto) {
        System.out.println(userMapper.toModel(userDto).getUserName());
        return Optional.of(userDto)
                .map(userMapper::toModel)
                .filter(user -> !isPresentUserByEmail(user.getEmail()))
                .map(userRepository::save)
                .map(userMapper::ToDto)
                .map(userRegestrationMapper::ToRegestrationDto)
                .orElse(null);
    }



    public List<ProductDTO> userCategoriesList(Long id) {
        if (userRepository.findById(id).isPresent()){
            User user=userRepository.findById(id).get();
            Set<Category> categories=user.getCategories();

            List<Product> productList=productRepository.findByCategoriesIn(categories);
            Set<Product> set = new HashSet<>(productList);
            productList.clear();
            productList.addAll(set);
            return productList.stream().map(productMapper::productToProductDTO).collect(Collectors.toList());

        }
        return null;
    }

   /* @Override
    public ProductDTO createNewProduct(ProductDTO productDTO) {


        Product product = productMapper.productDtoToProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.productToProductDTO(savedProduct);
    }*/
}
