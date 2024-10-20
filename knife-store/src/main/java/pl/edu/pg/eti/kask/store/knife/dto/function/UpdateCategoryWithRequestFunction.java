package pl.edu.pg.eti.kask.store.knife.dto.function;

import pl.edu.pg.eti.kask.store.knife.dto.PatchCategoryRequest;
import pl.edu.pg.eti.kask.store.knife.entity.Category;

import java.util.function.BiFunction;

public class UpdateCategoryWithRequestFunction implements BiFunction<Category, PatchCategoryRequest, Category> {

    @Override
    public Category apply(Category entity, PatchCategoryRequest request) {
        return Category.builder()
                .id(entity.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

}
