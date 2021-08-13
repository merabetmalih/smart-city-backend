package com.example.springmvcrest.store.controller;

import com.example.springmvcrest.store.api.dto.*;
import com.example.springmvcrest.store.service.CustomCategoryService;
import com.example.springmvcrest.store.service.StoreService;
import com.example.springmvcrest.utils.Response;
import com.example.springmvcrest.utils.Results;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@AllArgsConstructor
    @RequestMapping("/api/store")
public class StoreController {
    private final StoreService storeService;
    private final CustomCategoryService customCategoryService;

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public StoreCreationDto createStore(@RequestPart(value = "store") StoreCreationDto storeCreationDto,
                                        @RequestPart("image") MultipartFile multipartFile) throws IOException{
       // storeDto.setImageStore(FileUploadUtil.saveFile(multipartFile));
        return storeService.create(storeCreationDto);
    }

    @PostMapping("/Information")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Response<String> setStoreInformation(@RequestBody StoreInformationCreationDto storeInformationCreationDto){
        return storeService.setStoreInformation(storeInformationCreationDto);
    }

    @GetMapping("/Information/{id}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public StoreInformationDto getStoreInformation(@PathVariable(value = "id") Long id){
        return storeService.getStoreInformation(id);
    }

    @GetMapping("/Information-store/{id}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public StoreInformationDto getStoreInformationById(@PathVariable(value = "id") Long id){
        return storeService.getStoreInformationByStoreId(id);
    }

    /*@GetMapping("/category/all")
    @ResponseStatus(value = HttpStatus.OK)
    public Results<String> getAllCategoryStore(){
        return new Results<>(defaultCategoryService.findAll());
    }*/


    @PostMapping("/customCategory/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CustomCategoryDto createCustomCategory(@ModelAttribute CustomCategoryDto customCategoryDto){
        return customCategoryService.create(customCategoryDto);
    }

    @DeleteMapping("/customCategory/delete/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Response<String> deleteCustomCategory(@PathVariable Long id){
         return customCategoryService.delete(id);
    }

    @PutMapping("/customCategory/update")
    @ResponseStatus(value = HttpStatus.OK)
    public CustomCategoryDto updateCustomCategory(@ModelAttribute CustomCategoryDto customCategoryDto){
        return customCategoryService.update(customCategoryDto);
    }

    @GetMapping("/customCategory/all/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Results<CustomCategoryDto> getAllCustomCategoryByProvider(@PathVariable Long id){
        return new Results<>(customCategoryService.getAllByProvider(id));
    }

    @GetMapping("/customCategory/store/all/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Results<CustomCategoryDto> getAllCustomCategoryByStore(@PathVariable Long id){
        return new Results<>(customCategoryService.getAllByStore(id));
    }

    @GetMapping("/store-around")
    @ResponseStatus(value = HttpStatus.OK)
    public Results<StoreDto> findStoreByDistance(@RequestParam("distance") double distance,
                                                 @RequestParam("longitude") double longitude,
                                                 @RequestParam("latitude") double latitude,
                                                 @RequestParam(value = "category", required = false, defaultValue = "") String category){
        return new Results<>(storeService.findStoreByDistance(latitude,longitude,distance,category));
    }
}
