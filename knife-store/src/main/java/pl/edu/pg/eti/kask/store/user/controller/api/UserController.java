package pl.edu.pg.eti.kask.store.user.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.store.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.store.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.store.user.dto.PatchUserRequest;
import pl.edu.pg.eti.kask.store.user.dto.PutUserRequest;
import java.util.UUID;

@Path("")
public interface UserController {

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUserResponse getUser(@PathParam("id") UUID id);

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    GetUsersResponse getUsers();

    @PUT
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void putUser(@PathParam("id") UUID id, PutUserRequest request);

    @DELETE
    @Path("/users/{id}")
    void deleteUser(@PathParam("id") UUID id);

    @PATCH
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchUser(@PathParam("id") UUID id, PatchUserRequest request);

    @GET
    @Path("/users/{id}/photo")
    @Produces(MediaType.APPLICATION_JSON)
    byte[] getUserPhoto(@PathParam("id") UUID id);

    @PUT
    @Path("/users/{id}/photo")
    @Consumes(MediaType.APPLICATION_JSON)
    void putUserPhoto(@PathParam("id") UUID id, String photoPath);

    @PATCH
    @Path("/users/{id}/photo")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchUserPhoto(@PathParam("id") UUID id, String photoPath);

    @DELETE
    @Path("/users/{id}/photo")
    void deleteUserPhoto(@PathParam("id") UUID id);
}
