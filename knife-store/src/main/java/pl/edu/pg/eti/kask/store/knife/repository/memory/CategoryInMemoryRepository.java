package pl.edu.pg.eti.kask.store.knife.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.store.datastore.component.DataStore;
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.repository.api.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class CategoryInMemoryRepository implements CategoryRepository {
    private final DataStore store;

    @Inject
    public CategoryInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Category> find(UUID id) {
        return store.findAllCategories().stream()
                .filter(category -> category.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Category> findAll() {
        return store.findAllCategories();
    }

    @Override
    public void create(Category entity) {
        store.createCategory(entity);
    }

    @Override
    public void delete(Category entity) {
        store.findAllKnives().stream()
                .filter(knife -> knife.getCategory().equals(entity))
                .forEach(knife -> store.deleteKnife(knife.getId()));
        store.deleteCategory(entity.getId());
    }

    @Override
    public void update(Category entity) {
        store.updateCategory(entity);
    }
}
