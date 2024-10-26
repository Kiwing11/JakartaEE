package pl.edu.pg.eti.kask.store.knife.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;
import pl.edu.pg.eti.kask.store.knife.repository.api.CategoryRepository;
import pl.edu.pg.eti.kask.store.knife.repository.api.KnifeRepository;
import pl.edu.pg.eti.kask.store.user.entity.User;
import pl.edu.pg.eti.kask.store.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class KnifeService {
    private final KnifeRepository knifeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Inject
    public KnifeService(KnifeRepository knifeRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.knifeRepository = knifeRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Optional<Knife> find(UUID id){
        return knifeRepository.find(id);
    }

    public Optional<Knife> find(User user, UUID id){
        return knifeRepository.findByIdAndUser(id, user);
    }

    public List<Knife> findAll(){
        return knifeRepository.findAll();
    }

    public void create(Knife knife){
        knifeRepository.create(knife);
    }

    public void update(Knife knife){
        knifeRepository.update(knife);
    }

    public void delete(Knife knife){
        knifeRepository.delete(knife);
    }

    public void delete(UUID id){
        knifeRepository.delete(knifeRepository.find(id).orElseThrow());
    }

    public Optional<List<Knife>> findAllByCategory(UUID id) {
        return categoryRepository.find(id).map(knifeRepository::findAllByCategory);
    }

    public Optional<List<Knife>> findAllByUser(UUID id) {
        return userRepository.find(id).map(knifeRepository::findAllByUser);
    }
}
