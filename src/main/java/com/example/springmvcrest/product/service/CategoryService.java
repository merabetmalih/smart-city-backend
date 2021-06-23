package com.example.springmvcrest.product.service;

import com.example.springmvcrest.product.api.dto.CategoryDto;
import com.example.springmvcrest.product.api.mapper.CategoryMapper;
import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.product.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAllCategory(){
        return categoryRepository.findAll()
                .stream()
                .filter(category -> category.getParentCategory()==null)
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public Category findCategoryByName(String name){
        return categoryRepository.findByName(name)
                .orElse(null);
    }

    @Named("getCategoriesList")
    public List<CategoryDto> getCategoriesList(Set<Category> categories){
        Map<Category,List<Category>> map=new HashMap<>();
        for (Category category :categories) {
            Category parent=category.getParentCategory();
            if(map.containsKey(parent)){
                map.get(parent).add(category);
            }else {
                map.put(parent,new ArrayList<>(Collections.singletonList(category)));
            }
        }

        return map.keySet().stream()
                .peek(key -> key.setSubCategorys(new HashSet<>(map.get(key))))
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
