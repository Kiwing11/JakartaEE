package pl.edu.pg.eti.kask.store.knife.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.repository.api.CategoryRepository;
import pl.edu.pg.eti.kask.store.user.entity.UserRoles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
@Log
public class CategoryService {
    private final CategoryRepository repository;

    @Inject
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Optional<Category> find(UUID id){
        return repository.find(id);
    }

    @PermitAll
    public List<Category> findAll(){
        return repository.findAll();
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void create(Category category){
        repository.create(category);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void update(Category category){
        repository.update(category);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(Category category){
        repository.delete(category);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id){
        repository.delete(repository.find(id).orElseThrow());
    }
}
