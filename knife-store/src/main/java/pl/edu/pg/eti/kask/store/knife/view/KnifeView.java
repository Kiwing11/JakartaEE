package pl.edu.pg.eti.kask.store.knife.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.store.factory.ModelFunctionFactory;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;
import pl.edu.pg.eti.kask.store.knife.model.KnifeModel;
import pl.edu.pg.eti.kask.store.knife.service.KnifeService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

/**
 * View bean for rendering single knife information.
 */
@ViewScoped
@Named
public class KnifeView implements Serializable {

    /**
     * Service for managing knives.
     */
    private final KnifeService service;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * Knife id.
     */
    @Setter
    @Getter
    private UUID id;

    /**
     * Knife exposed to the view.
     */
    @Getter
    private KnifeModel knife;


    /**
     * @param service service for managing knives
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public KnifeView(KnifeService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Knife> knife = service.find(id);
        if (knife.isPresent()) {
            this.knife = factory.knifeToModel().apply(knife.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Knife not found");
        }
    }

}
