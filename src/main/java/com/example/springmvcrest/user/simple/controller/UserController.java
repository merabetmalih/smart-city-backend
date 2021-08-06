package com.example.springmvcrest.user.simple.controller;


import com.example.springmvcrest.flashDeal.api.dto.FlashDealDto;
import com.example.springmvcrest.product.api.dto.CategoryDto;
import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.user.api.dto.UserDto;
import com.example.springmvcrest.user.api.dto.UserRegestrationDto;
import com.example.springmvcrest.user.simple.api.dto.CityDto;
import com.example.springmvcrest.user.simple.api.dto.SimpleUserDto;
import com.example.springmvcrest.user.simple.api.dto.SimpleUserInformationDto;
import com.example.springmvcrest.user.simple.service.SimpleUserService;
import com.example.springmvcrest.utils.Response;
import com.example.springmvcrest.utils.Results;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final SimpleUserService simpleUserService;

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserRegestrationDto registerUser(@ModelAttribute UserDto userDto) {
        return simpleUserService.saveUser(userDto);
    }


    @PostMapping("/interestCenter")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Response<String>  setUserInterestCenter(@ModelAttribute SimpleUserDto simpleUserDto){
       return simpleUserService.setUserInterestCenter(simpleUserDto);
    }

    @GetMapping("/interestCenter/{id}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Results<CategoryDto> getUserInterestCenter(@PathVariable Long id){
        return new Results<>(simpleUserService.getUserInterestCenter(id));
    }

    @PostMapping("/Information")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Response<String> setUserInformation(@RequestBody SimpleUserInformationDto simpleUserInformationDto){
        return simpleUserService.setUserInformation(simpleUserInformationDto);
    }

    @GetMapping("/Information/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SimpleUserInformationDto getUserInformation(@PathVariable(value = "id") Long id){
        return simpleUserService.getUserInformation(id);
    }

    @GetMapping("/flash")
    @ResponseStatus(value = HttpStatus.OK)
    public Results<FlashDealDto> getUserFlashDeals(@RequestParam(value = "id") Long id,
                                                   @RequestParam(value = "date") String date){
        return new Results<>(simpleUserService.getUserFlashDeals(id,date));
    }

    @GetMapping("/offer/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Results<ProductDTO> getUserProductOffers(@PathVariable(value = "id") Long id){
        return new Results<>(simpleUserService.getUserProductOffers(id));
    }

    @PostMapping("/follow/store/{id}/{idUser}")
    @ResponseStatus(value = HttpStatus.OK)
    public Response<String> followStore(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "idUser") Long userId){
        return simpleUserService.followStore(id,userId);
    }

    @PostMapping("/stop-follow/store/{id}/{idUser}")
    @ResponseStatus(value = HttpStatus.OK)
    public Response<String> stopFollowingStore(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "idUser") Long userId){
        return simpleUserService.stopFollowingStore(id,userId);
    }

    @GetMapping("/is-follow/store/{id}/{idUser}")
    @ResponseStatus(value = HttpStatus.OK)
    public Response<String> isFollowingStore(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "idUser") Long userId){
        return simpleUserService.isFollowingStore(id,userId);
    }

    @PostMapping("/default-city")
    @ResponseStatus(value = HttpStatus.OK)
    public Response<String> setUserDefaultCity(@RequestBody CityDto cityDto){
        return simpleUserService.setUserDefaultCity(cityDto);
    }

    @GetMapping("/default-city")
    @ResponseStatus(value = HttpStatus.OK)
    public CityDto getUserDefaultCity(@RequestParam(name = "id") Long id){
        return simpleUserService.getUserDefaultCity(id);
    }
}
