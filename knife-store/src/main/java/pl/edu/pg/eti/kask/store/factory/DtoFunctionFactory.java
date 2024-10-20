package pl.edu.pg.eti.kask.store.factory;
import jakarta.enterprise.context.ApplicationScoped;
import pl.edu.pg.eti.kask.store.knife.dto.function.*;
import pl.edu.pg.eti.kask.store.user.dto.function.RequestToUserFunction;
import pl.edu.pg.eti.kask.store.user.dto.function.UpdateUserWithRequestFunction;
import pl.edu.pg.eti.kask.store.user.dto.function.UserToResponseFunction;
import pl.edu.pg.eti.kask.store.user.dto.function.UsersToResponseFunction;

@ApplicationScoped
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

    public KnifeToResponseFunction knifeToResponse() {
        return new KnifeToResponseFunction();
    }

    public KnivesToResponseFunction knivesToResponse() {
        return new KnivesToResponseFunction();
    }

    public RequestToKnifeFunction requestToKnife() {
        return new RequestToKnifeFunction();
    }

    public UpdateKnifeWithRequestFunction updateKnife() {
        return new UpdateKnifeWithRequestFunction();
    }

    public CategoryToResponseFunction categoryToResponse() {
        return new CategoryToResponseFunction();
    }

    public CategoriesToResponseFunction categoriesToResponse() {
        return new CategoriesToResponseFunction();
    }
    public RequestToCategoryFunction requestToCategory() {
        return new RequestToCategoryFunction();
    }
    public UpdateCategoryWithRequestFunction updateCategory() {
        return new UpdateCategoryWithRequestFunction();
    }
}
