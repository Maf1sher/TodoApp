package org.mafisher.backend.service;

import org.mafisher.backend.dto.request.CreateCategoryRequest;
import org.mafisher.backend.dto.response.Category;

import java.security.Principal;
import java.util.List;

public interface CategoryService {
    Category create(CreateCategoryRequest category, Principal principal);

    Category edit(long id, CreateCategoryRequest category, Principal principal);

    void delete(long id, Principal principal);

    List<Category> getAll(Principal principal);
}
