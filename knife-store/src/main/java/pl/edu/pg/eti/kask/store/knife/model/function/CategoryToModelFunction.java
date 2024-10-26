package pl.edu.pg.eti.kask.store.knife.model.function;

import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.model.CategoryModel;

import java.io.Serializable;
import java.util.function.Function;

public class CategoryToModelFunction implements Function<Category, CategoryModel>, Serializable {

    public CategoryToModelFunction() {
    }

    @Override
    public CategoryModel apply(Category entity) {
        return CategoryModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

}
