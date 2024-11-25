package pl.edu.pg.eti.kask.store.user.dto.function;

import pl.edu.pg.eti.kask.store.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.store.user.entity.User;
import pl.edu.pg.eti.kask.store.user.entity.UserRoles;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToUserFunction implements BiFunction<UUID, PutUserRequest, User> {

    @Override
    public User apply(UUID id, PutUserRequest request) {
        return User.builder()
                .id(id)
                .login(request.getLogin())
                .name(request.getName())
                .email(request.getEmail())
                .birthDate(request.getBirthDate())
                .surname(request.getSurname())
                .password(request.getPassword())
                .roles(List.of(UserRoles.USER))
                .build();
    }
}
