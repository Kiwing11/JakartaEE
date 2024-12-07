package pl.edu.pg.eti.kask.store.knife.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.store.factory.ModelFunctionFactory;
import pl.edu.pg.eti.kask.store.knife.model.KnivesModel;
import pl.edu.pg.eti.kask.store.knife.service.KnifeService;

import java.util.UUID;

/**
 * View bean for rendering list of knives.
 */
@RequestScoped
@Named
public class KnifeList {

    private KnifeService service;

    private KnivesModel knives;

    private final ModelFunctionFactory factory;

    @Inject
    public KnifeList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(KnifeService service) {
        this.service = service;
    }

    public KnivesModel getKnives() {
        if (knives == null) {
            knives = factory.knivesToModel().apply(service.findAllForCallerPrincipal());
        }
        return knives;
    }

    public KnivesModel getCategoryKnives(UUID categoryId) {
        if (knives == null) {
            knives = factory.knivesToModel().apply(service.findAllByCategory(categoryId).orElseThrow());
        }
        return knives;

    }

    public void deleteAction(KnivesModel.Knife knife) {
        UUID categoryId = service.find(knife.getId())
                .map(k -> k.getCategory().getId())
                .orElse(null);
        service.delete(knife.getId());
        knives = null;
        try{
            getCategoryKnives(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        knives = null;
//        try {
//            getKnives();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
