package org.mafisher.backend.service.impl;

import lombok.AllArgsConstructor;
import org.mafisher.backend.dto.request.CreateCategoryRequest;
import org.mafisher.backend.dto.response.Category;
import org.mafisher.backend.entity.CategoryEntity;
import org.mafisher.backend.entity.UserEntity;
import org.mafisher.backend.handler.BusinessErrorCodes;
import org.mafisher.backend.handler.CustomException;
import org.mafisher.backend.mapper.Mapper;
import org.mafisher.backend.repository.CategoryRepository;
import org.mafisher.backend.repository.UserRepository;
import org.mafisher.backend.service.CategoryService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private Mapper<CategoryEntity, Category> mapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public Category create(CreateCategoryRequest category, Principal principal) {

        UserEntity userEntity = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userEntity.getCategory().forEach((c) -> {
            if(c.getName().equals(category.getName())) {
                throw new CustomException(BusinessErrorCodes.DUPLICATE_CATEGORY);
            }
        });

        CategoryEntity newCategoryEntity = CategoryEntity.builder()
                .name(category.getName())
                .user(userEntity)
                .build();

        return mapper.mapTo(categoryRepository.save(newCategoryEntity));
    }

    @Override
    public Category edit(long id, CreateCategoryRequest category, Principal principal) {
        UserEntity userEntity = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(BusinessErrorCodes.CATEGORY_NOT_FOUND));

        if(!categoryEntity.getUser().getId().equals(userEntity.getId())) {
            throw new CustomException(BusinessErrorCodes.BAD_CREDENTIALS);
        }

        userEntity.getCategory().forEach((c) ->{
            if(c.getName().equals(category.getName())) {
                throw new CustomException(BusinessErrorCodes.DUPLICATE_CATEGORY);
            }
        });

        categoryEntity.setName(category.getName());
        return mapper.mapTo(categoryRepository.save(categoryEntity));
    }

    @Override
    public void delete(long id, Principal principal) {
        UserEntity userEntity = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(BusinessErrorCodes.CATEGORY_NOT_FOUND));

        if(!categoryEntity.getUser().getId().equals(userEntity.getId())) {
            throw new CustomException(BusinessErrorCodes.BAD_CREDENTIALS);
        }
        categoryRepository.delete(categoryEntity);

    }

    @Override
    public List<Category> getAll(Principal principal) {
        UserEntity userEntity = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<CategoryEntity> categoryEntityList = categoryRepository.findByUserId(userEntity.getId());
        return categoryEntityList.stream().map(mapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public Category getById(long id, Principal principal) {
        UserEntity userEntity = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(BusinessErrorCodes.CATEGORY_NOT_FOUND));

        if(!category.getUser().getId().equals(userEntity.getId())) {
            throw new CustomException(BusinessErrorCodes.BAD_CREDENTIALS);
        }

        return mapper.mapTo(category);
    }
}
