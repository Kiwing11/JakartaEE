package pl.edu.pg.eti.kask.store.knife.controller.api;

import pl.edu.pg.eti.kask.store.knife.dto.GetCategoriesResponse;
import pl.edu.pg.eti.kask.store.knife.dto.GetCategoryResponse;
import pl.edu.pg.eti.kask.store.knife.dto.PatchCategoryRequest;
import pl.edu.pg.eti.kask.store.knife.dto.PutCategoryRequest;

import java.util.UUID;

public interface CategoryController {
    GetCategoriesResponse getCategories();

    GetCategoryResponse getCategory(UUID id);

    void patchCategory(UUID id, PatchCategoryRequest request);

    void putCategory(UUID id, PutCategoryRequest request);

    void deleteCategory(UUID id);

}
