package com.example.springmvcrest.store.controller;

import com.example.springmvcrest.store.api.StoreDto;
import com.example.springmvcrest.store.service.CategoryStoreService;
import com.example.springmvcrest.store.service.StoreService;
import com.example.springmvcrest.utils.FileUploadUtil;
import com.example.springmvcrest.utils.Results;
import com.example.springmvcrest.utils.Slug;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/store")
public class StoreController {
    private final StoreService storeService;
    private final CategoryStoreService categoryStoreService;

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
        return new Results<>(categoryStoreService.findAll());
    }


}
