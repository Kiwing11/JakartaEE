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

    public String deleteAction(KnivesModel.Knife knife) {
        service.delete(knife.getId());
        return "knife_list?faces-redirect=true";
    }

}
