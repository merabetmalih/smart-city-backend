package com.example.springmvcrest.user.simple.service;

import com.example.springmvcrest.product.api.dto.CategoryDto;
import com.example.springmvcrest.product.api.mapper.CategoryMapper;
import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.product.service.CategoryService;
import com.example.springmvcrest.store.service.exception.MultipleStoreException;
import com.example.springmvcrest.user.api.dto.UserDto;
import com.example.springmvcrest.user.api.dto.UserRegestrationDto;
import com.example.springmvcrest.user.api.mapper.UserMapper;
import com.example.springmvcrest.user.api.mapper.UserRegestrationMapper;
import com.example.springmvcrest.user.simple.api.SimpleUserDto;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import com.example.springmvcrest.user.simple.repository.SimpleUserRepository;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SimpleUserService {
    private final SimpleUserRepository simpleUserRepository;
    private final UserMapper userMapper;
    private final UserRegestrationMapper userRegestrationMapper;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public Optional<SimpleUser> findSimpleUserByEmail(String email) {
        return simpleUserRepository.findByEmail(email);
    }

    public List<SimpleUser> findAllSimpleUsers() {
        return simpleUserRepository.findAll();
    }

    private Boolean isPresentSimpleUserByEmail(String email)
    {
        return simpleUserRepository.findByEmail(email).isPresent();
    }



    SimpleUser findById(Long id)
    {
        return simpleUserRepository.findById(id)
                .orElseThrow(MultipleStoreException::new);
    }

    public UserRegestrationDto saveUser(UserDto userDto) {
        return Optional.of(userDto)
                .map(userMapper::toModel)
                .filter(user -> !isPresentSimpleUserByEmail(user.getEmail()))
                .map(simpleUserRepository::save)
                .map(userMapper::ToDto)
                .map(userRegestrationMapper::ToRegestrationDto)
                .orElse(null);
    }





    public Response<String> setUserInterestCenter(SimpleUserDto simpleUserDto){
        SimpleUser user=simpleUserRepository.findById(simpleUserDto.getId())
                .orElseThrow(MultipleStoreException::new);
        Set<Category> collect = simpleUserDto.getInterest().stream()
                .map(categoryService::findCategoryByName)
                .collect(Collectors.toSet());
        user.setInterestCenter(collect);
        simpleUserRepository.save(user);
        return new Response<>("created.");
    }

    public List<CategoryDto> getUserInterestCenter(Long id){
        SimpleUser user=simpleUserRepository.findById(id)
                .orElseThrow(MultipleStoreException::new);
        return user.getInterestCenter()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
