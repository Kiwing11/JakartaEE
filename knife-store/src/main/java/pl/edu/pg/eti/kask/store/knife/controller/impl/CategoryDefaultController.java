package pl.edu.pg.eti.kask.store.knife.controller.impl;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import pl.edu.pg.eti.kask.store.factory.DtoFunctionFactory;
import pl.edu.pg.eti.kask.store.knife.controller.api.CategoryController;
import pl.edu.pg.eti.kask.store.knife.dto.GetCategoriesResponse;
import pl.edu.pg.eti.kask.store.knife.dto.GetCategoryResponse;
import pl.edu.pg.eti.kask.store.knife.dto.PatchCategoryRequest;
import pl.edu.pg.eti.kask.store.knife.dto.PutCategoryRequest;
import pl.edu.pg.eti.kask.store.knife.service.CategoryService;

import java.util.UUID;

@Path("")
public class CategoryDefaultController implements CategoryController {

    private final CategoryService service;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        //ATM in this implementation only HttpServletRequest can be injected with CDI so JAX-RS injection is used.
        this.response = response;
    }

    @Inject
    public CategoryDefaultController(CategoryService service, DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
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
    @SneakyThrows
    public void putCategory(UUID id, PutCategoryRequest request) {
        try {
            service.create(factory.requestToCategory().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(CategoryController.class, "getCategory")
                    .build(id)
                    .toString()
            );
            throw new WebApplicationException(Response.Status.CREATED);
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
