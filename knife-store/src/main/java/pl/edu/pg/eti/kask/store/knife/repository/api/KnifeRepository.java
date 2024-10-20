package pl.edu.pg.eti.kask.store.knife.repository.api;

import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;
import pl.edu.pg.eti.kask.store.repository.api.Repository;
import pl.edu.pg.eti.kask.store.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KnifeRepository extends Repository<Knife, UUID> {
    Optional<Knife> findByIdAndUser(UUID id, User user);

    List<Knife> findAllByUser(User user);

    List<Knife> findAllByCategory(Category category);
}
