package pl.edu.pg.eti.kask.store.user.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.store.user.entity.User;
import pl.edu.pg.eti.kask.store.user.entity.UserRoles;
import pl.edu.pg.eti.kask.store.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@LocalBean
@Stateless
@Log
@NoArgsConstructor(force = true)
public class UserService {
    private final UserRepository repository;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public UserService(UserRepository repository, Pbkdf2PasswordHash passwordHash) {
        this.repository = repository;
        this.passwordHash = passwordHash;
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<User> find(String login){
        return repository.findByLogin(login);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<User> find(UUID id){
        return repository.find(id);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public List<User> findAll(){
        return repository.findAll();
    }

    @RolesAllowed(UserRoles.ADMIN)
    public Optional<User> findByEmail(String email){
        return repository.findByEmail(email);
    }

    @PermitAll
    public void create(User user){
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        repository.create(user);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id){
        repository.delete(repository.find(id).orElseThrow());
    }

    @RolesAllowed(UserRoles.ADMIN)
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

    public byte[] getPhoto(String path) {
       try {
           return Files.readAllBytes(java.nio.file.Path.of(path));
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }

    @PermitAll
    public boolean verify(String login, String password){
        return repository.findByLogin(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }
}
