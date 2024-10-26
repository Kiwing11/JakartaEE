package pl.edu.pg.eti.kask.store.knife.model.function;

import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.model.CategoriesModel;

import java.util.List;
import java.util.function.Function;

public class CategoriesToModelFunction implements Function<List<Category>, CategoriesModel> {

    @Override
    public CategoriesModel apply(List<Category> entity) {
        return CategoriesModel.builder()
                .categories(entity.stream()
                        .map(category -> CategoriesModel.Category.builder()
                                .id(category.getId())
                                .name(category.getName())
                                .build())
                        .toList())
                .build();
    }
}
