package pl.edu.pg.eti.kask.store.user.service;

import pl.edu.pg.eti.kask.store.user.entity.User;
import pl.edu.pg.eti.kask.store.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> find(UUID id){
        return repository.find(id);
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    public void create(User user){
        repository.create(user);
    }

    public void delete(UUID id){
        repository.delete(repository.find(id).orElseThrow());
    }

    public void update(User user){
        repository.update(user);
    }
    public void updatePhoto(UUID id, String photoPath) {
        repository.find(id).ifPresent(user -> {
            user.setPhoto(photoPath);
            repository.update(user);
            System.out.println("User updated: " + user);
        });
    }

}
