package pl.edu.pg.eti.kask.store.user.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.store.user.entity.User;
import pl.edu.pg.eti.kask.store.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class UserPersistenceRepository implements UserRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> find(UUID id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void create(User entity) {
        em.persist(entity);
    }
    @Override
    public void delete(User entity) {
        em.refresh(em.find(User.class, entity.getId()));
        em.remove(em.find(User.class, entity.getId()));
    }
    @Override
    public void update(User entity) {
        em.merge(entity);
    }

}
