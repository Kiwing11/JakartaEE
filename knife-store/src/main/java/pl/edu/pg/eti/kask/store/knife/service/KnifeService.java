package pl.edu.pg.eti.kask.store.knife.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;
import pl.edu.pg.eti.kask.store.knife.repository.api.CategoryRepository;
import pl.edu.pg.eti.kask.store.knife.repository.api.KnifeRepository;
import pl.edu.pg.eti.kask.store.user.entity.User;
import pl.edu.pg.eti.kask.store.user.entity.UserRoles;
import pl.edu.pg.eti.kask.store.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class KnifeService {
    private final KnifeRepository knifeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final SecurityContext securityContext;

    @Inject
    public KnifeService(KnifeRepository knifeRepository, CategoryRepository categoryRepository, UserRepository userRepository, CategoryService categoryService,  @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.knifeRepository = knifeRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.securityContext = securityContext;
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Knife> find(UUID id){
        return knifeRepository.find(id);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Knife> find(User user, UUID id){
        return knifeRepository.findByIdAndUser(id, user);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Knife> findAll(){
        return knifeRepository.findAll();
    }

    @RolesAllowed(UserRoles.USER)
    public List<Knife> findAll(User user) {
        return knifeRepository.findAllByUser(user);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Knife> findAll(User user, Category category){
        return knifeRepository.findAllByUserAndCategory(user, category);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Knife> findForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return find(id);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return find(user, id);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Knife> findAllForCallerPrincipal() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return findAll();
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return findAll(user);
    }


    @RolesAllowed(UserRoles.USER)
    public void create(Knife knife){
        if(knifeRepository.find(knife.getId()).isPresent()){
            throw new IllegalArgumentException("Knife with given id already exists");
        }
//        if(knifeRepository.find(knife.getCategory().getId()).isEmpty()){
//            throw new IllegalArgumentException("Category does not exist");
//        }
        knifeRepository.create(knife);
    }

    @RolesAllowed(UserRoles.USER)
    public void createForCallerPrincipal(Knife knife) {
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        if(knifeRepository.find(knife.getId()).isPresent()){
            throw new IllegalArgumentException("Knife with given id already exists");
        }
        knife.setUser(user);
        System.out.println(knife.getUser());
        knifeRepository.create(knife);
    }

    @RolesAllowed(UserRoles.USER)
    public void update(Knife knife){
        checkAdminRoleOrOwner(knifeRepository.find(knife.getId()));
        knifeRepository.update(knife);
    }

    @RolesAllowed(UserRoles.USER)
    public void delete(Knife knife){
        checkAdminRoleOrOwner(knifeRepository.find(knife.getId()));
        knifeRepository.delete(knife);
    }

    @RolesAllowed(UserRoles.USER)
    public void delete(UUID id){
        checkAdminRoleOrOwner(knifeRepository.find(id));
        knifeRepository.delete(knifeRepository.find(id).orElseThrow());
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<List<Knife>> findAllByCategory(UUID id) {
        if(securityContext.isCallerInRole(UserRoles.ADMIN)){
            return categoryRepository.find(id).map(knifeRepository::findAllByCategory);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        Category category = categoryRepository.find(id).orElseThrow(NotFoundException::new);

        return Optional.of(knifeRepository.findAllByUserAndCategory(user, category));
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<List<Knife>> findAllByUser(UUID id) {
        return userRepository.find(id).map(knifeRepository::findAllByUser);
    }

    private void checkAdminRoleOrOwner(Optional<Knife> knife) throws EJBAccessException {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(UserRoles.USER)
                && knife.isPresent()
                && knife.get().getUser().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }

}
