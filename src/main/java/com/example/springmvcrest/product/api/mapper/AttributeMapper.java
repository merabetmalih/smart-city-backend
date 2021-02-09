package com.example.springmvcrest.product.api.mapper;

import com.example.springmvcrest.product.api.dto.AttributeDto;
import com.example.springmvcrest.product.domain.Attribute;
import com.example.springmvcrest.product.domain.AttributeValue;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AttributeMapper {
    @Mapping(source = "attribute.attributeValues", target = "attributeValues")
    AttributeDto ToDto(Attribute attribute);
    default Set<String> mapAttribute(Set<AttributeValue> attributeValues ){
        return attributeValues
                .stream()
                .map(AttributeValue::getValue)
                .collect(Collectors.toSet());
    }


    Attribute ToModel(AttributeDto attributeDto);
    default Set<AttributeValue> mapAttributeDto(Set<String> list ){
        Set<AttributeValue> attributeValueSet=new HashSet<>();
        for (String item:list
             ) {
            AttributeValue value =new AttributeValue();
            value.setValue(item);
            attributeValueSet.add(value);
        }
        return attributeValueSet;
    }
    @AfterMapping
     static Attribute setValue(@MappingTarget Attribute attribute){
        if(attribute.getAttributeValues()!=null){
            Set<AttributeValue> tags=attribute.getAttributeValues();
            for (AttributeValue value:tags
            ) {
                value.setAttribute(attribute);
            }

        }
        return attribute;
    }
}
