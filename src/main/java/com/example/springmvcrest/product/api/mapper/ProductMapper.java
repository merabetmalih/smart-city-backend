package com.example.springmvcrest.product.api.mapper;

import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.product.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper

public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "product.customCategory.id", target = "customCategoryDto")
    ProductDTO ToDto(Product product);


    Product ToModel(ProductDTO productDTO);
    /*@BeforeMapping
    public static void saveInstances(@MappingTarget ProductDTO productDTO){
        List<String> attributes=new ArrayList<>();
        for (ProductVariant productVariant:productDTO.getProductVariants()
             ) {
            for (AttributeValue attributeValue :productVariant.getAttributeValues()
                 ) {
                attributes.add(attributeValue.getAttribute().getName());
            }
        }
        attributes=attributes.stream().distinct().collect(Collectors.toList());

        for (String name:attributes
             ) {
            Attribute color=new Attribute();
            color.setName(name);
            attributeRepository.save(color);
        }


        System.out.println("********************************"+attributes.toString());
    }*/



    @AfterMapping
    public static Product setTags(@MappingTarget Product product){
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
