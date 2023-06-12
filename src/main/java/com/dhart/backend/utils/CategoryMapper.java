package com.dhart.backend.utils;

import com.dhart.backend.model.Category;
import com.dhart.backend.model.dto.CategoryDTO;

public class CategoryMapper {

    public static Category categoryDtoToCategory(Long id, CategoryDTO categoryDTO){
        Category category = Category.builder()
                .id(id)
                .title(categoryDTO.getTitle())
                .description(categoryDTO.getDescription())
                .imagePath(categoryDTO.getImagePath())
                .imageUrl(categoryDTO.getImageUrl())
                .build();
        return category;
    }

    public static CategoryDTO categoryToCategoryDto(Category category){

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(category.getId())
                .title(category.getTitle())
                .description(category.getDescription())
                .imagePath(category.getImagePath())
                .imageUrl(category.getImageUrl())
                .build();
        return categoryDTO;
    }
}
