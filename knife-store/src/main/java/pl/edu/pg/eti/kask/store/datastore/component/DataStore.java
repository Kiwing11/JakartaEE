package pl.edu.pg.eti.kask.store.datastore.component;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;
import pl.edu.pg.eti.kask.store.serialization.component.CloningUtility;
import pl.edu.pg.eti.kask.store.user.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {
    private final Set<User> users = new HashSet<>();

    private final Set<Knife> knives = new HashSet<>();

    private final Set<Category> categories = new HashSet<>();

    private final CloningUtility cloningUtility;
    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch(user -> user.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getId()));
        }
        users.add(cloningUtility.clone(value));
    }

    public synchronized void updateUser(User value) throws IllegalArgumentException {
        if (users.removeIf(user -> user.getId().equals(value.getId()))) {
            users.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteUser(UUID id) {
        if (!users.removeIf(user -> user.getId().equals(id))) {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized List<Knife> findAllKnives() {
        return knives.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createKnife(Knife value) throws IllegalArgumentException {
        if (knives.stream().anyMatch(knife -> knife.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The knife id \"%s\" is not unique".formatted(value.getId()));
        }
        Knife entity = cloneWithRelationships(value);
        knives.add(entity);
    }

    public synchronized void updateKnife(Knife value) throws IllegalArgumentException {
        Knife entity = cloneWithRelationships(value);
        if(knives.removeIf(knife -> knife.getId().equals(value.getId()))){
            knives.add(entity);
        } else {
            throw new IllegalArgumentException("The knife with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteKnife(UUID id) {
        if (!knives.removeIf(knife -> knife.getId().equals(id))) {
            throw new IllegalArgumentException("The knife with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized List<Category> findAllCategories(){
        return categories.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createCategory(Category value) throws IllegalArgumentException {
        if (categories.stream().anyMatch(category -> category.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The category id \"%s\" is not unique".formatted(value.getId()));
        }
        categories.add(cloningUtility.clone(value));
    }

    public synchronized void deleteCategory(UUID id) {
        if (!categories.removeIf(category -> category.getId().equals(id))) {
            throw new IllegalArgumentException("The category with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized void updateCategory(Category entity) {
        if(categories.removeIf(category -> category.getId().equals(entity.getId()))){
            categories.add(cloningUtility.clone(entity));
        } else {
            throw new IllegalArgumentException("The category with id \"%s\" does not exist".formatted(entity.getId()));
        }
    }


    private Knife cloneWithRelationships(Knife value){
        Knife entity = cloningUtility.clone(value);

        if(entity.getUser() != null){
            entity.setUser(users.stream()
                    .filter(user -> user.getId().equals(value.getUser().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No user with id \"%s\".".formatted(value.getUser().getId()))));
        }

        if(entity.getCategory() != null){
            entity.setCategory(categories.stream()
                    .filter(category -> category.getId().equals(value.getCategory().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No category with id \"%s\".".formatted(value.getCategory().getId()))));
        }

        return entity;
    }

}



