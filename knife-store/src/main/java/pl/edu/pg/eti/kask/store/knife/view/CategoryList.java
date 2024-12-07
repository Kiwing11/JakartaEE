package pl.edu.pg.eti.kask.store.knife.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.store.factory.ModelFunctionFactory;
import pl.edu.pg.eti.kask.store.knife.model.CategoriesModel;
import pl.edu.pg.eti.kask.store.knife.service.CategoryService;

@RequestScoped
@Named
public class CategoryList {
    private CategoryService service;

    private CategoriesModel categories;

    private final ModelFunctionFactory factory;

    @Inject
    public CategoryList(CategoryService service, ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(CategoryService service) {
        this.service = service;
    }

    public CategoriesModel getCategories() {
        if (categories == null) {
            categories = factory.categoriesToModel().apply(service.findAll());
        }
        return categories;
    }

    public void deleteAction(CategoriesModel.Category category) {
        service.delete(category.getId());
        categories = null;
        //return "category_list?faces-redirect=true";
    }
}
