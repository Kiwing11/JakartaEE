package pl.edu.pg.eti.kask.store.knife.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.store.knife.dto.GetCategoriesResponse;
import pl.edu.pg.eti.kask.store.knife.dto.GetCategoryResponse;
import pl.edu.pg.eti.kask.store.knife.dto.PatchCategoryRequest;
import pl.edu.pg.eti.kask.store.knife.dto.PutCategoryRequest;

import java.util.UUID;

@Path("")
public interface CategoryController {
    @GET
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    GetCategoriesResponse getCategories();

    @GET
    @Path("/categories/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetCategoryResponse getCategory(@PathParam("id") UUID id);

    @PATCH
    @Path("/categories/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void patchCategory(@PathParam("id") UUID id, PatchCategoryRequest request);

    @PUT
    @Path("/categories/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putCategory(@PathParam("id") UUID id, PutCategoryRequest request);

    @DELETE
    @Path("/categories/{id}")
    void deleteCategory(@PathParam("id") UUID id);

}
