package org.mafisher.backend.mapper.impl;

import lombok.AllArgsConstructor;
import org.mafisher.backend.dto.response.Category;
import org.mafisher.backend.dto.response.Task;
import org.mafisher.backend.entity.CategoryEntity;
import org.mafisher.backend.entity.TaskEntity;
import org.mafisher.backend.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CategoryMapper implements Mapper<CategoryEntity, Category> {

    private final ModelMapper modelMapper;
    private final Mapper<TaskEntity, Task> taskMapper;

    @Override
    public Category mapTo(CategoryEntity categoryEntity) {

        return Category.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .tasks((categoryEntity.getTasks() == null) ? List.of():
                        categoryEntity.getTasks()
                        .stream().map(taskMapper::mapTo)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public CategoryEntity mapFrom(Category category) {
        return modelMapper.map(category, CategoryEntity.class);
    }
}
