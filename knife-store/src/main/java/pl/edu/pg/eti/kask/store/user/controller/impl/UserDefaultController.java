package pl.edu.pg.eti.kask.store.user.controller.impl;

import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.store.factory.DtoFunctionFactory;
import pl.edu.pg.eti.kask.store.user.controller.api.UserController;
import pl.edu.pg.eti.kask.store.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.store.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.store.user.dto.PatchUserRequest;
import pl.edu.pg.eti.kask.store.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.store.user.entity.User;
import pl.edu.pg.eti.kask.store.user.service.UserService;

import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class UserDefaultController implements UserController {
    private UserService service;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        //JAX-RS injection
        this.response = response;
    }

    @EJB
    public void setService(UserService service) {
        this.service = service;
    }

    @Inject
    public UserDefaultController(DtoFunctionFactory dtoFunctionFactory, UriInfo uriInfo) {
        this.factory =  dtoFunctionFactory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return service.find(id)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(service.findAll());
    }

    @Override
    public void putUser(UUID id,PutUserRequest request) {
//        try {
//            service.create(factory.requestToUser().apply(id, request));
//        }
//        catch (IllegalArgumentException e) {
//            throw new BadRequestException(e);
//        }
        try {
            service.create(factory.requestToUser().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(UserController.class, "getUser")
                    .build(id)
                    .toString()
            );
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException e) {
            if(e.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, e.getMessage(), e);
                throw new BadRequestException(e);
            }
            throw e;
        }
    }

    @Override
    public void deleteUser(UUID id){
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void patchUser(UUID id, PatchUserRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateUser().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public byte[] getUserPhoto(UUID id) {
        String photoPath = service.find(id)
                .map(User::getPhoto)
                .orElseThrow(NotFoundException::new);
        return service.getPhoto(photoPath);
    }

    @Override
    public void putUserPhoto(UUID id, String photoPath) {
        service.find(id).ifPresentOrElse(
                user -> {
                    if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
                        throw new BadRequestException("User already has a photo");
                    }
                    service.updatePhoto(id, photoPath);
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }


    @Override
    public void patchUserPhoto(UUID id, String photoPath) {
        service.find(id).ifPresentOrElse(user -> {
            try {
                service.updatePhoto(id, photoPath);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }, () -> {
            throw new NotFoundException();
        });
    }

    @Override
    public void deleteUserPhoto(UUID id) {
        service.find(id).ifPresentOrElse(user -> {
            try {
                service.updatePhoto(id, null);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }, () -> {
            throw new NotFoundException();
        });
    }
}
