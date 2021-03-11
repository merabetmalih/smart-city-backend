package com.example.springmvcrest.product.controller;

import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.product.service.ProductSearchService;
import com.example.springmvcrest.product.service.ProductService;
import com.example.springmvcrest.utils.Response;
import com.example.springmvcrest.utils.Results;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
@Slf4j
public class ProductController {
    private final ProductSearchService  productSearchService;
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Results<ProductDTO> searchProduct(@RequestParam(name = "search", required = false) String query,
                                             @RequestParam(name = "page",defaultValue = "1",required = false) int page) {
        if (query != null && !query.equals(""))
            return new Results<>(productSearchService.search(query,page));
        return  new Results<>(productSearchService.findAllProduct(page));
    }

    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ProductDTO createProduct(@RequestPart(value = "product") ProductDTO productDTO,
                                    @RequestPart(value = "productImagesFile",required = false)List<MultipartFile>  productImages,
                                    @RequestPart(value = "variantesImagesFile",required = false)List<MultipartFile>  variantsImages)  {

        return productService.create(productDTO,productImages,variantsImages);
    }

    @PutMapping(value = "/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ProductDTO updateProduct(@RequestPart(value = "product") ProductDTO productDTO,
                                    @RequestPart(value = "productImagesFile",required = false)List<MultipartFile>  productImages,
                                    @RequestPart(value = "variantesImagesFile",required = false)List<MultipartFile>  variantsImages)  {

        return productService.updateProduct(productDTO,productImages,variantsImages);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Response<String> deleteCustomCategory(@PathVariable Long id){
        return productService.deleteProduct(id);
    }

    @GetMapping("/all/category/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Results<ProductDTO> getProductByCustomCategoryId(@PathVariable("id") long id){
        return new Results<>(productService.getProductByCustomCategoryId(id));
    }

    @GetMapping("/all/provider/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Results<ProductDTO> getProductByCustomCategoryStoreProviderId(@PathVariable("id") long id){
        return new Results<>(productService.getProductByCustomCategoryStoreProviderId(id));
    }

    @GetMapping(value = "/image/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getProductImage(@PathVariable("filename") String filename) {
        return productService.downloadImage(filename);
    }
}
