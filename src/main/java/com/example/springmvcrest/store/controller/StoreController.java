package com.example.springmvcrest.store.controller;

import com.example.springmvcrest.store.api.dto.CustomCategoryDto;
import com.example.springmvcrest.store.api.dto.StoreDto;
import com.example.springmvcrest.store.service.CustomCategoryService;
import com.example.springmvcrest.store.service.DefaultCategoryService;
import com.example.springmvcrest.store.service.StoreService;
import com.example.springmvcrest.utils.FileUploadUtil;
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
    private final DefaultCategoryService defaultCategoryService;
    private final CustomCategoryService customCategoryService;

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public StoreDto createStore( StoreDto storeDto,
                                @RequestPart("image") MultipartFile multipartFile) throws IOException{
        storeDto.setImageStore(FileUploadUtil.saveFile(multipartFile));
        return storeService.create(storeDto);
    }

    @GetMapping("/category/all")
    @ResponseStatus(value = HttpStatus.OK)
    public Results<String> getAllCategoryStore(){
        return new Results<>(defaultCategoryService.findAll());
    }


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

}
