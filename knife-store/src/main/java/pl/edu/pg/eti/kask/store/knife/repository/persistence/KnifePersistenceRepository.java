package pl.edu.pg.eti.kask.store.knife.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;
import pl.edu.pg.eti.kask.store.knife.repository.api.KnifeRepository;
import pl.edu.pg.eti.kask.store.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class KnifePersistenceRepository implements KnifeRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Knife> find(UUID id) {
        return Optional.ofNullable(em.find(Knife.class, id));
    }

    @Override
    public List<Knife> findAll() {
        return em.createQuery("SELECT k FROM Knife k", Knife.class).getResultList();
    }

    @Override
    public void create(Knife entity) {
        em.persist(entity);
        em.refresh(em.find(Category.class, entity.getCategory().getId()));
    }

    @Override
    public void update(Knife entity) {
        em.refresh(em.find(Knife.class, entity.getId()));
        em.merge(entity);
        em.refresh(em.find(Category.class, entity.getCategory().getId()));
    }

    @Override
    public void delete(Knife entity) {
        em.refresh(em.find(Knife.class, entity.getId()));
        em.remove(em.find(Knife.class, entity.getId()));
    }

    @Override
    public Optional<Knife> findByIdAndUser(UUID id, User user){
        try{
          return Optional.of(em.createQuery("SELECT k FROM Knife k WHERE k.id = :id AND k.user = :user", Knife.class)
                  .setParameter("id", id)
                  .setParameter("user", user)
                  .getSingleResult());
        } catch (NoResultException e){
            return Optional.empty();
        }
    }
    @Override
    public List<Knife> findAllByUser(User user){
        return em.createQuery("SELECT k FROM Knife k WHERE k.user = :user", Knife.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Knife> findAllByCategory(Category category){
//        return em.createQuery("SELECT k FROM Knife k WHERE k.category = :category", Knife.class)
//                .setParameter("category", category)
//                .getResultList();
        return category.getKnives();
    }
}
