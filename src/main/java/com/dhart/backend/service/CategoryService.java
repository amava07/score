package com.dhart.backend.service;

import com.dhart.backend.exceptions.NotFoundException;
import com.dhart.backend.exceptions.RegisteredResourceException;
import com.dhart.backend.model.Category;
import com.dhart.backend.model.dto.CategoryDTO;
import com.dhart.backend.repository.CategoryRepository;
import com.dhart.backend.utils.CategoryMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> findAllCategories(){

        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<CategoryDTO>();
        for (Category category: categories) {
            CategoryDTO categoryDTO = CategoryMapper.categoryToCategoryDto(category);
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }

    public CategoryDTO saveCategory(CategoryDTO categoryDTO) throws RegisteredResourceException {
        Category category = CategoryMapper.categoryDtoToCategory(null, categoryDTO);

        if(existsCategoryByTitle(category.getTitle())) throw new RegisteredResourceException("This category already created");
        else{categoryRepository.save(category);}

        return categoryDTO;
    }

    public boolean existsCategoryByTitle(String title) {
        return categoryRepository.findByTitle(title).isPresent();
    }

    public Optional<CategoryDTO> findCategoryById(Long id) throws NotFoundException {
        Optional<CategoryDTO> categoryDTOOptional = null;
        Optional<Category> category = categoryRepository.findById(id);        if(category.isPresent()){
            CategoryDTO categoryDTO = CategoryMapper.categoryToCategoryDto(category.get());
            categoryDTOOptional = Optional.of(categoryDTO);
        }
        else{
            throw new NotFoundException("This category doesn't exists");
        }
        return categoryDTOOptional;

    }

    public List<CategoryDTO> findCategoryByName(String text){
        List<Category> categories = categoryRepository.findByName(text);
        List<CategoryDTO> categoryDTOS = new ArrayList<>();

        for (Category category: categories) {
            categoryDTOS.add(CategoryMapper.categoryToCategoryDto(category));
        }
        return categoryDTOS;
    }

    public void deleteCategory(Long id) throws NotFoundException {
        findCategoryById(id);
        categoryRepository.deleteById(id);
    }

}
