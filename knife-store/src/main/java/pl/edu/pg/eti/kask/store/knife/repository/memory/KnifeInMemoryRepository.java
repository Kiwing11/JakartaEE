package pl.edu.pg.eti.kask.store.knife.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.store.datastore.component.DataStore;
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;
import pl.edu.pg.eti.kask.store.knife.repository.api.KnifeRepository;
import pl.edu.pg.eti.kask.store.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class KnifeInMemoryRepository implements KnifeRepository {
    private final DataStore store;

    @Inject
    public KnifeInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Knife> find(UUID id) {
        return store.findAllKnives().stream()
                .filter(knife -> knife.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Knife> findAll() {
        return store.findAllKnives();
    }

    @Override
    public void create(Knife entity) {
        store.createKnife(entity);
    }

    @Override
    public void update(Knife entity) {
        store.updateKnife(entity);
    }

    @Override
    public void delete(Knife entity) {
        store.deleteKnife(entity.getId());
    }

    @Override
    public Optional<Knife> findByIdAndUser(UUID id, User user){
        return store.findAllKnives().stream()
                .filter(knife -> knife.getUser().equals(user))
                .filter(knife -> knife.getId().equals(id))
                .findFirst();
    }
    @Override
    public List<Knife> findAllByUser(User user){
        System.out.println("USER: " + user.getName());
        return store.findAllKnives().stream()
                .filter(knife -> {
                    System.out.println(user.equals(knife.getUser()));
                    return user.equals(knife.getUser());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Knife> findAllByCategory(Category category){
        System.out.println("CATEGORY: " + category);
        return store.findAllKnives().stream()
                .filter(knife -> {
                    System.out.println("KNIFE: " + knife.getName() + " " + knife.getCategory());
                    System.out.println(category.equals(knife.getCategory()));
                    return category.equals(knife.getCategory());})
                .collect(Collectors.toList());
    }
}
