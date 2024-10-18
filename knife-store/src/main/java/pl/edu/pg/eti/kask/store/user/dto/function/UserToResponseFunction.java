package pl.edu.pg.eti.kask.store.user.dto.function;

import pl.edu.pg.eti.kask.store.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.store.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.store.user.entity.User;

import java.util.function.Function;

public class UserToResponseFunction implements Function<User, GetUserResponse> {
    @Override
    public GetUserResponse apply(User entity) {
        return GetUserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .build();
    }
}
