package pl.edu.pg.eti.kask.store.user.repository.api;

import pl.edu.pg.eti.kask.store.repository.api.Repository;
import pl.edu.pg.eti.kask.store.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {

    @Override
    Optional<User> find(UUID id);

    @Override
    List<User> findAll();

    @Override
    void create(User entity);

    @Override
    void delete(User entity);

    @Override
    void update(User entity);
}
