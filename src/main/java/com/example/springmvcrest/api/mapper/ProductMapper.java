package com.example.springmvcrest.api.mapper;

import com.example.springmvcrest.api.model.ProductDTO;
import com.example.springmvcrest.domain.Category;
import com.example.springmvcrest.domain.Product;
import com.example.springmvcrest.domain.Tags;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO productToProductDTO(Product product);

    Product productDtoToProduct(ProductDTO productDTO);
    @AfterMapping
    public static Product updateProduct(@MappingTarget Product product){
        if (product.getTags()!=null){
            Set<Tags> tags=product.getTags();
            for (Tags tag:tags
            ) {
                tag.setProduct(product);
            }
            product.setTags(tags);
        }
        return product;
    }

}
