package pl.edu.pg.eti.kask.store.user.controller.api;

import pl.edu.pg.eti.kask.store.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.store.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.store.user.dto.PatchUserRequest;
import pl.edu.pg.eti.kask.store.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.store.user.entity.User;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface UserController {
    GetUserResponse getUser(UUID id);
    GetUsersResponse getUsers();
    void putUser(UUID id, PutUserRequest request);

    void deleteUser(UUID id);

    void patchUser(UUID id, PatchUserRequest request);

    String getUserPhoto(UUID id);

    void putUserPhoto(UUID id, String photoPath);

    void patchUserPhoto(UUID id, String photoPath);

    void deleteUserPhoto(UUID id);
}
