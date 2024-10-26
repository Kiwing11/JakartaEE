package pl.edu.pg.eti.kask.store.knife.view;

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

    /**
     * Service for managing knives.
     */
    private final KnifeService service;

    /**
     * Knifes list exposed to the view.
     */
    private KnivesModel knives;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * @param service knife service
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public KnifeList(KnifeService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all knives
     */
    public KnivesModel getKnives() {
        if (knives == null) {
            knives = factory.knivesToModel().apply(service.findAll());
        }
        return knives;
    }

    public KnivesModel getCategoryKnives(UUID categoryId) {
        if (knives == null) {
            knives = factory.knivesToModel().apply(service.findAllByCategory(categoryId).orElseThrow());
        }
        return knives;

    }

    /**
     * Action for clicking delete action.
     *
     * @param knife knife to be removed
     * @return navigation case to list_knives
     */
    public String deleteAction(KnivesModel.Knife knife) {
        service.delete(knife.getId());
        return "knife_list?faces-redirect=true";
    }

}
