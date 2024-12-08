package pl.edu.pg.eti.kask.store.knife.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.repository.api.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class CategoryPersistenceRepository implements CategoryRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Category> find(UUID id) {
        return Optional.ofNullable(em.find(Category.class, id));
    }

    @Override
    public List<Category> findAll() {
        //return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Category entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Category entity) {
        em.refresh(em.find(Category.class, entity.getId()));
        em.remove(em.find(Category.class, entity.getId()));
    }

    @Override
    public void update(Category entity) {
        em.refresh(em.find(Category.class, entity.getId()));
        em.merge(entity);
    }
}
