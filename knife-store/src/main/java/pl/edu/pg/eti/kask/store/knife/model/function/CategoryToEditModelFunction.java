package pl.edu.pg.eti.kask.store.knife.model.function;

import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.model.CategoryEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class CategoryToEditModelFunction implements Function<Category, CategoryEditModel>, Serializable {

    @Override
    public CategoryEditModel apply(Category entity) {
        return CategoryEditModel.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

}
