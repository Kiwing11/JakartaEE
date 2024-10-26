package pl.edu.pg.eti.kask.store.knife.model.function;

import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.model.CategoryEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateCategoryWithModelFunction implements BiFunction<Category, CategoryEditModel, Category>, Serializable {

    @Override
    @SneakyThrows
    public Category apply(Category entity, CategoryEditModel request) {
        return Category.builder()
                .id(entity.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
