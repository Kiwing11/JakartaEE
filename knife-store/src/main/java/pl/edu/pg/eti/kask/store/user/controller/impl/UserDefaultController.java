package pl.edu.pg.eti.kask.store.user.controller.impl;

import pl.edu.pg.eti.kask.store.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.store.controller.servlet.exception.NotFoundException;
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

public class UserDefaultController implements UserController {
    private final UserService service;
    private final DtoFunctionFactory factory;

    public UserDefaultController(UserService userService, DtoFunctionFactory dtoFunctionFactory) {
        this.service = userService;
        this.factory =  dtoFunctionFactory;
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
        try {
            service.create(factory.requestToUser().apply(id, request));
        }
        catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
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
    public String getUserPhoto(UUID id) {
        return service.find(id)
                .map(User::getPhoto)
                .orElseThrow(NotFoundException::new);
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
