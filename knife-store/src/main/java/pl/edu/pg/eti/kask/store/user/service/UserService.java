package pl.edu.pg.eti.kask.store.user.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.store.user.entity.User;
import pl.edu.pg.eti.kask.store.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class UserService {
    private final UserRepository repository;

    @Inject
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> find(UUID id){
        return repository.find(id);
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    @Transactional
    public void create(User user){
        repository.create(user);
    }

    @Transactional
    public void delete(UUID id){
        repository.delete(repository.find(id).orElseThrow());
    }

    @Transactional
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
