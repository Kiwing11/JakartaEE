package pl.edu.pg.eti.kask.store.knife.dto.function;

import pl.edu.pg.eti.kask.store.knife.dto.GetCategoryResponse;
import pl.edu.pg.eti.kask.store.knife.entity.Category;

import java.util.function.Function;

public class CategoryToResponseFunction implements Function<Category, GetCategoryResponse> {
    @Override
    public GetCategoryResponse apply(Category entity){
        return GetCategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
