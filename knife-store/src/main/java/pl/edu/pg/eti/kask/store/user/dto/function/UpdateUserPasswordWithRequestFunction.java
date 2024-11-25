package pl.edu.pg.eti.kask.store.user.dto.function;

import pl.edu.pg.eti.kask.store.user.dto.PutPasswordRequest;
import pl.edu.pg.eti.kask.store.user.entity.User;

import java.util.function.BiFunction;

public class UpdateUserPasswordWithRequestFunction implements BiFunction<User, PutPasswordRequest, User> {
    @Override
    public User apply(User entity, PutPasswordRequest request){
        return User.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .name(entity.getName())
                .birthDate(entity.getBirthDate())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .photo(entity.getPhoto())
                .password(request.getPassword())
                .build();
    }
}
