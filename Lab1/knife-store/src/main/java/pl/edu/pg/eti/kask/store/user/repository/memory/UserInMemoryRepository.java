package pl.edu.pg.eti.kask.store.user.repository.memory;

import pl.edu.pg.eti.kask.store.datastore.component.DataStore;
import pl.edu.pg.eti.kask.store.user.entity.User;
import pl.edu.pg.eti.kask.store.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserInMemoryRepository implements UserRepository {

    private final DataStore store;

    public UserInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<User> find(UUID id) {
        return store.findAllUsers().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return store.findAllUsers();
    }

    @Override
    public void create(User entity) {
        store.createUser(entity);
    }
    @Override
    public void delete(User entity) {
        store.deleteUser(entity.getId());
    }
    @Override
    public void update(User entity) {
        store.updateUser(entity);
    }

}
