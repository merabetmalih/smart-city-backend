package com.example.springmvcrest.user.simple.service;

import com.example.springmvcrest.flashDeal.api.dto.FlashDealDto;
import com.example.springmvcrest.flashDeal.api.mapper.FlashDealMapper;
import com.example.springmvcrest.flashDeal.domain.FlashDeal;
import com.example.springmvcrest.offer.api.mapper.OfferMapper;
import com.example.springmvcrest.offer.domain.Offer;
import com.example.springmvcrest.product.api.dto.CategoryDto;
import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.product.api.mapper.CategoryMapper;
import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.product.service.CategoryService;
import com.example.springmvcrest.store.service.StoreService;
import com.example.springmvcrest.user.api.dto.UserDto;
import com.example.springmvcrest.user.api.dto.UserRegestrationDto;
import com.example.springmvcrest.user.api.mapper.UserMapper;
import com.example.springmvcrest.user.api.mapper.UserRegestrationMapper;
import com.example.springmvcrest.user.simple.api.dto.CityDto;
import com.example.springmvcrest.user.simple.api.dto.SimpleUserDto;
import com.example.springmvcrest.user.simple.api.dto.SimpleUserInformationDto;
import com.example.springmvcrest.user.simple.api.mapper.CityMapper;
import com.example.springmvcrest.user.simple.api.mapper.SimpleUserInformationMapper;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import com.example.springmvcrest.user.simple.repository.SimpleUserRepository;
import com.example.springmvcrest.utils.DateUtil;
import com.example.springmvcrest.utils.Errorhandler.DateException;
import com.example.springmvcrest.utils.Errorhandler.SimpleUserException;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Setter(onMethod=@__({@Autowired}))
public class SimpleUserService {
    private SimpleUserRepository simpleUserRepository;
    private UserMapper userMapper;
    private UserRegestrationMapper userRegestrationMapper;
    private CategoryService categoryService;
    private CategoryMapper categoryMapper;
    private SimpleUserInformationMapper simpleUserInformationMapper;
    private FlashDealMapper flashDealMapper;
    private OfferMapper offerMapper;
    private StoreService storeService;
    private CityMapper cityMapper;

    public Optional<SimpleUser> findSimpleUserByEmail(String email) {
        return simpleUserRepository.findByEmail(email);
    }

    public List<SimpleUser> findSimpleUserByInterestCenter(Set<Category> categories) {
        return simpleUserRepository.findDistinctByInterestCenterIn(categories);
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
        if(simpleUserDto.getInterest()!=null && !simpleUserDto.getInterest().isEmpty()){
            SimpleUser user=findById(simpleUserDto.getId());
            Set<Category> collect = simpleUserDto.getInterest().stream()
                    .map(categoryService::findCategoryByName)
                    .collect(Collectors.toSet());
            user.setInterestCenter(collect);
            simpleUserRepository.save(user);
        }
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

    public List<FlashDealDto> getUserFlashDeals(Long userId,String date){
        if(!DateUtil.isValidDate(date)){
            throw new DateException("error.date.invalid");
        }
        LocalDateTime startOfDate = LocalDateTime.of(LocalDate.parse(date), LocalTime.MIDNIGHT);
        LocalDateTime endOfDate = LocalDateTime.of(LocalDate.parse(date), LocalTime.MAX);
        return findById(userId).getFlashDeals().stream()
                .filter(flash -> flash.getCreateAt().isAfter(startOfDate) && flash.getCreateAt().isBefore(endOfDate))
                .map(flashDealMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getUserProductOffers(Long userId){
        Date todayDate=new Date();
        return findById(userId).getOffers().stream()
                .filter(offer -> !offer.getDeleted())
                .filter(offer -> todayDate.after(offer.getStartDate()) && todayDate.before(offer.getEndDate()))
                .map(offerMapper::toDto)
                .flatMap(offerDto -> offerDto.getProducts().stream())
                .collect(Collectors.toList());
    }

    public void setFlashDeal(SimpleUser simpleUser, FlashDeal flashDeal){
        simpleUser.getFlashDeals().add(flashDeal);
        simpleUserRepository.save(simpleUser);
    }

    public void setOffers(SimpleUser simpleUser, Offer offer){
        simpleUser.getOffers().add(offer);
        simpleUserRepository.save(simpleUser);
    }

    public Response<String> followStore(Long storeId,Long userId){
        SimpleUser user=findById(userId);
        user.getFollowedStores().add(
                storeService.findStoreById(storeId)
        );
        simpleUserRepository.save(
                user
        );
        return new Response<>("created.");
    }

    public Response<String> stopFollowingStore(Long storeId,Long userId){
        SimpleUser user=findById(userId);
        user.getFollowedStores().remove(
                storeService.findStoreById(storeId)
        );
        simpleUserRepository.save(
                user
        );
        return new Response<>("deleted.");
    }

    public Response<String> isFollowingStore(Long storeId,Long userId){
        SimpleUser user=findById(userId);
        if(user.getFollowedStores().contains(
                storeService.findStoreById(storeId)
        )){
            return new Response<>("isFollowing");
        }else {
            return new Response<>("notFollowing");
        }
    }

    public Response<String> setUserDefaultCity(CityDto cityDto){
        SimpleUser user=findById(cityDto.getUserId());
        user.setDefaultCity(cityMapper.toModel(cityDto));
        simpleUserRepository.save(user);
        return new Response<>("created");
    }

    public CityDto getUserDefaultCity(Long UserId){
        SimpleUser user=findById(UserId);
        return Optional.of(user.getDefaultCity())
                .map(cityMapper::toDto)
                .orElseThrow(() -> new SimpleUserException("error.user.city.notfound"));
    }
}
