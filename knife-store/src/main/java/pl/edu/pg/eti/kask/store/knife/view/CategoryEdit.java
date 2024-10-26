package pl.edu.pg.eti.kask.store.knife.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.store.factory.ModelFunctionFactory;
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.model.CategoryEditModel;
import pl.edu.pg.eti.kask.store.knife.service.CategoryService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class CategoryEdit implements Serializable {

    private final CategoryService service;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private CategoryEditModel category;

    @Inject
    public CategoryEdit(CategoryService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Category> category = service.find(id);
        if(category.isPresent()) {
          this.category = factory.categoryToEditModel().apply(category.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
        }
    }

    public String saveAction() {
        service.update(factory.updateCategory().apply(service.find(id).orElseThrow(), category));
        return "/category/category_list.xhtml?faces-redirect=true";
    }
}
