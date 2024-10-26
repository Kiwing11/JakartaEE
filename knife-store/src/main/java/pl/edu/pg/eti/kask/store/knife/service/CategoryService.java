package pl.edu.pg.eti.kask.store.knife.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.repository.api.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class CategoryService {
    private final CategoryRepository repository;

    @Inject
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Optional<Category> find(UUID id){
        return repository.find(id);
    }

    public List<Category> findAll(){
        return repository.findAll();
    }

    public void create(Category category){
        repository.create(category);
    }

    public void update(Category category){
        repository.update(category);
    }

    public void delete(Category category){
        repository.delete(category);
    }

    public void delete(UUID id){
        repository.delete(repository.find(id).orElseThrow());
    }
}
