package pl.edu.pg.eti.kask.store.knife.controller.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import pl.edu.pg.eti.kask.store.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.store.factory.DtoFunctionFactory;
import pl.edu.pg.eti.kask.store.knife.controller.api.CategoryController;
import pl.edu.pg.eti.kask.store.knife.dto.GetCategoriesResponse;
import pl.edu.pg.eti.kask.store.knife.dto.GetCategoryResponse;
import pl.edu.pg.eti.kask.store.knife.dto.PatchCategoryRequest;
import pl.edu.pg.eti.kask.store.knife.dto.PutCategoryRequest;
import pl.edu.pg.eti.kask.store.knife.service.CategoryService;
import pl.edu.pg.eti.kask.store.controller.servlet.exception.NotFoundException;

import java.util.UUID;

@Path("")
public class CategoryDefaultController implements CategoryController {

    private final CategoryService service;

    private final DtoFunctionFactory factory;

    @Inject
    public CategoryDefaultController(CategoryService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetCategoriesResponse getCategories() {
        return factory.categoriesToResponse().apply(service.findAll());
    }

    @Override
    public GetCategoryResponse getCategory(UUID id) {
        return service.find(id)
                .map(factory.categoryToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putCategory(UUID id, PutCategoryRequest request) {
        try {
            service.create(factory.requestToCategory().apply(id, request));
        } catch (IllegalArgumentException e){
            throw new BadRequestException(e);
        }
    }

    @Override
    public void patchCategory(UUID id, PatchCategoryRequest request){
        service.find(id)
                .ifPresentOrElse(entity -> {
                    service.update(factory.updateCategory().apply(entity, request));
                }, () -> {
                    throw new NotFoundException();
                });
    }

    @Override
    public void deleteCategory(UUID id) {
        service.find(id)
                .ifPresentOrElse(service::delete, () -> {
                    throw new NotFoundException();
                });
    }

}
