package com.example.springmvcrest.product.controller;

import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.product.service.ProductSearchService;
import com.example.springmvcrest.user.controller.Results;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
@Slf4j
public class ProductController {
    private final ProductSearchService  productSearchService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Results searchProduct(@RequestParam(name = "search", required = false) String query) {

        if (query != null) {
            List<ProductDTO> result = productSearchService.search(query);
            //Predicate<Product> byParentProductNull = product -> product.getParentProduct() == null;
            //result = result.stream().filter(byParentProductNull).collect(Collectors.toList());
            return new Results(result);
        }
        return  new Results(productSearchService.findAllProduct());
    }


}
