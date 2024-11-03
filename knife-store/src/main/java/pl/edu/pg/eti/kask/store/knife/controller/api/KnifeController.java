package pl.edu.pg.eti.kask.store.knife.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.store.knife.dto.GetKnifeResponse;
import pl.edu.pg.eti.kask.store.knife.dto.GetKnivesResponse;
import pl.edu.pg.eti.kask.store.knife.dto.PatchKnifeRequest;
import pl.edu.pg.eti.kask.store.knife.dto.PutKnifeRequest;

import java.util.UUID;

@Path("")
public interface KnifeController {

    @GET
    @Path("/knives")
    @Produces(MediaType.APPLICATION_JSON)
    GetKnivesResponse getKnives();

    @GET
    @Path("/categories/{id}/knives")
    @Produces(MediaType.APPLICATION_JSON)
    GetKnivesResponse getKnivesByCategory(@PathParam("id") UUID id);

    @GET
    @Path("/users/{id}/knives")
    @Produces(MediaType.APPLICATION_JSON)
    GetKnivesResponse getKnivesByUser(@PathParam("id") UUID id);

    @GET
    @Path("/knives/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetKnifeResponse getKnife(@PathParam("id") UUID id);

    @PUT
    @Path("/knives/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putKnife(@PathParam("id") UUID id, PutKnifeRequest request);

    @PATCH
    @Path("/knives/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void patchKnife(@PathParam("id") UUID id, PatchKnifeRequest request);

    @DELETE
    @Path("/knives/{id}")
    void deleteKnife(@PathParam("id") UUID id);
}
