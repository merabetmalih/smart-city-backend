package com.example.springmvcrest.user.simple.service;

import com.example.springmvcrest.product.api.dto.CategoryDto;
import com.example.springmvcrest.product.api.mapper.CategoryMapper;
import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.product.service.CategoryService;
import com.example.springmvcrest.store.api.dto.StoreInformationDto;
import com.example.springmvcrest.store.service.exception.MultipleStoreException;
import com.example.springmvcrest.store.service.exception.StoreNotFoundException;
import com.example.springmvcrest.user.api.dto.UserDto;
import com.example.springmvcrest.user.api.dto.UserRegestrationDto;
import com.example.springmvcrest.user.api.mapper.UserMapper;
import com.example.springmvcrest.user.api.mapper.UserRegestrationMapper;
import com.example.springmvcrest.user.simple.api.dto.SimpleUserDto;
import com.example.springmvcrest.user.simple.api.dto.SimpleUserInformationDto;
import com.example.springmvcrest.user.simple.api.mapper.SimpleUserInformationMapper;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import com.example.springmvcrest.user.simple.repository.SimpleUserRepository;
import com.example.springmvcrest.utils.DateUtil;
import com.example.springmvcrest.utils.Errorhandler.DateException;
import com.example.springmvcrest.utils.Errorhandler.SimpleUserException;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
    private final SimpleUserInformationMapper simpleUserInformationMapper;

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



    public SimpleUser findById(Long id) {
        return simpleUserRepository.findById(id)
                .orElseThrow(() -> new SimpleUserException("error.user.notfound"));
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
        SimpleUser user=findById(simpleUserDto.getId());
        Set<Category> collect = simpleUserDto.getInterest().stream()
                .map(categoryService::findCategoryByName)
                .collect(Collectors.toSet());
        user.setInterestCenter(collect);
        simpleUserRepository.save(user);
        return new Response<>("created.");
    }

    public List<CategoryDto> getUserInterestCenter(Long id){
        SimpleUser user=findById(id);
        return user.getInterestCenter()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public Response<String> setUserInformation(SimpleUserInformationDto simpleUserInformationDto){
        if(!DateUtil.isValidDate(simpleUserInformationDto.getBirthDay())){
            throw new DateException("error.date.invalid");
        }
        SimpleUser simpleUser = findById(simpleUserInformationDto.getUserId());
        simpleUser.setFirstName(simpleUserInformationDto.getFirstName());
        simpleUser.setLastName(simpleUserInformationDto.getLastName());
        simpleUser.setBirthDay(DateUtil.parseDate(simpleUserInformationDto.getBirthDay()));
        simpleUserRepository.save(simpleUser);
        return new Response<>("created.");
    }

    public SimpleUserInformationDto getUserInformation(Long userId){
        return Optional.of(findById(userId))
                .map(simpleUserInformationMapper::ToDto)
                .orElseThrow(() -> new SimpleUserException("error.user.information"));

    }
}
