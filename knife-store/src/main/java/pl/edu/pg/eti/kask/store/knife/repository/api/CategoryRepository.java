package pl.edu.pg.eti.kask.store.knife.repository.api;

import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;
import pl.edu.pg.eti.kask.store.repository.api.Repository;

import java.util.UUID;

public interface CategoryRepository extends Repository<Category, UUID> {
}
