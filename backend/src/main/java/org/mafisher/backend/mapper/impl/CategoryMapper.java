package org.mafisher.backend.mapper.impl;

import lombok.AllArgsConstructor;
import org.mafisher.backend.dto.response.Category;
import org.mafisher.backend.entity.CategoryEntity;
import org.mafisher.backend.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryMapper implements Mapper<CategoryEntity, Category> {

    private final ModelMapper modelMapper;

    @Override
    public Category mapTo(CategoryEntity categoryEntity) {
        return modelMapper.map(categoryEntity, Category.class);
    }

    @Override
    public CategoryEntity mapFrom(Category category) {
        return modelMapper.map(category, CategoryEntity.class);
    }
}
