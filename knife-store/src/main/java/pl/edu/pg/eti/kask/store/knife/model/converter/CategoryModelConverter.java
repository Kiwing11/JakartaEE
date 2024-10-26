package pl.edu.pg.eti.kask.store.knife.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.store.factory.ModelFunctionFactory;
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.model.CategoryModel;
import pl.edu.pg.eti.kask.store.knife.service.CategoryService;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = CategoryModel.class, managed = true)
public class CategoryModelConverter implements Converter<CategoryModel> {

    private final CategoryService service;

    private final ModelFunctionFactory factory;


    @Inject
    public CategoryModelConverter(CategoryService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public CategoryModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Category> category = service.find(UUID.fromString(value));
        return category.map(factory.categoryToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, CategoryModel value) {
        return value == null ? "" : value.getId().toString();
    }

}
