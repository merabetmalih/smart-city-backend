package com.example.springmvcrest.product.api.mapper;

import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.product.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);


    ProductDTO productToProductDTO(Product product);


   /* Product productDtoToProduct(ProductDTO productDTO);
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
    }*/

}
