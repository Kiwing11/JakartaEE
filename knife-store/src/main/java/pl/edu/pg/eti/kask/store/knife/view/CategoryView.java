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
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;
import pl.edu.pg.eti.kask.store.knife.model.CategoryModel;
import pl.edu.pg.eti.kask.store.knife.service.CategoryService;
import pl.edu.pg.eti.kask.store.knife.service.KnifeService;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class CategoryView implements Serializable {
    private CategoryService service;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private CategoryModel category;

    @Inject
    public CategoryView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(CategoryService service) {
        this.service = service;
    }

    public void init() throws IOException {
        Optional<Category> category = service.find(id);

        if(category.isPresent()) {
            this.category = factory.categoryToModel().apply(category.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
        }
    }


}
