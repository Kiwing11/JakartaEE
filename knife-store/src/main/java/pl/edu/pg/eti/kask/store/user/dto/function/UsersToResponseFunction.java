package pl.edu.pg.eti.kask.store.user.dto.function;

import pl.edu.pg.eti.kask.store.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.store.user.entity.User;

import java.util.List;
import java.util.function.Function;

public class UsersToResponseFunction implements Function<List<User>, GetUsersResponse> {

    @Override
    public GetUsersResponse apply(List<User> entities) {
        return GetUsersResponse.builder()
                .users(entities.stream()
                        .map(user -> GetUsersResponse.User.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .surname(user.getSurname())
                                .photo(user.getPhoto())
                                .build())
                        .toList())
                .build();
    }
}
