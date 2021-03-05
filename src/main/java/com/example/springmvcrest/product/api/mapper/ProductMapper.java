package com.example.springmvcrest.product.api.mapper;

import com.example.springmvcrest.product.api.dto.AttributeValueDto;
import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.product.domain.*;
import com.example.springmvcrest.store.domain.CustomCategory;
import com.example.springmvcrest.store.service.CustomCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { CustomCategoryService.class})
public interface ProductMapper {

    @Mapping(source = "attributeValue.attribute.name", target = "attribute")
    AttributeValueDto ToDto(AttributeValue attributeValue);


    @Mappings({
            @Mapping(source = "attributeValueDto.value", target = "value"),
            @Mapping(source = "attributeValueDto.attribute", target = "attribute", qualifiedByName = "binType2")
    })
    AttributeValue ToModel(AttributeValueDto attributeValueDto);
    @Named("binType2")
    default Attribute locationToBinType(String attribute) {
        return  Attribute.builder().name(attribute).build();
    }

    @Mapping(source = "product.customCategory.id", target = "customCategory")
    ProductDTO ToDto(Product product);

    @Mapping(source = "productDTO.customCategory", target = "customCategory")
    Product ToModel(ProductDTO productDTO);



}
