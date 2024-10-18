package pl.edu.pg.eti.kask.store.factory;
import pl.edu.pg.eti.kask.store.user.dto.function.RequestToUserFunction;
import pl.edu.pg.eti.kask.store.user.dto.function.UpdateUserWithRequestFunction;
import pl.edu.pg.eti.kask.store.user.dto.function.UserToResponseFunction;
import pl.edu.pg.eti.kask.store.user.dto.function.UsersToResponseFunction;

public class DtoFunctionFactory {
    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    public RequestToUserFunction requestToUser() {
        return new RequestToUserFunction();
    }

    public UpdateUserWithRequestFunction updateUser() {
        return new UpdateUserWithRequestFunction();
    }
}
