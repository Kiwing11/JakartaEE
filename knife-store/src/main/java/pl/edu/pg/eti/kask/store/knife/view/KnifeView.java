package pl.edu.pg.eti.kask.store.knife.view;

import jakarta.ejb.EJB;
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

    private KnifeService service;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private KnifeModel knife;

    @Inject
    public KnifeView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(KnifeService service) {
        this.service = service;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Knife> knife = service.findForCallerPrincipal(id);
        if (knife.isPresent()) {
            this.knife = factory.knifeToModel().apply(knife.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Knife not found");
        }
    }

}
