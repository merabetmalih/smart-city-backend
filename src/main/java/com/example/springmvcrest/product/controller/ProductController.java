package com.example.springmvcrest.product.controller;

import com.example.springmvcrest.product.api.dto.AttributeDto;
import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.product.domain.Product;
import com.example.springmvcrest.product.service.AttributeService;
import com.example.springmvcrest.product.service.ProductSearchService;
import com.example.springmvcrest.product.service.ProductService;
import com.example.springmvcrest.storage.FileStorage;
import com.example.springmvcrest.storage.FileStorageException;
import com.example.springmvcrest.utils.FileUploadUtil;
import com.example.springmvcrest.utils.Results;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
@Slf4j
public class ProductController {
    private final ProductSearchService  productSearchService;
    private final ProductService productService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Results searchProduct(@RequestParam(name = "search", required = false) String query) {

        if (query != null) {
            Set<ProductDTO> result = productSearchService.search(query);
            //Predicate<Product> byParentProductNull = product -> product.getParentProduct() == null;
            //result = result.stream().filter(byParentProductNull).collect(Collectors.toList());
            return new Results(result);
        }
        return  new Results(productSearchService.findAllProduct());
    }

    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ProductDTO createCustomCategory(@RequestPart(value = "product") ProductDTO productDTO,
                                             @RequestPart(value = "productImagesFile",required = false)List<MultipartFile>  productImages,
                                                 @RequestPart(value = "variantesImagesFile",required = false)List<MultipartFile>  variantsImages)  {


        return productService.create(productDTO,productImages,variantsImages);
    }


}
