package pl.edu.pg.eti.kask.store.knife.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.store.factory.ModelFunctionFactory;
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.model.CategoryModel;
import pl.edu.pg.eti.kask.store.knife.model.KnifeCreateModel;
import pl.edu.pg.eti.kask.store.knife.service.CategoryService;
import pl.edu.pg.eti.kask.store.knife.service.KnifeService;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * View bean for rendering single knife create form. Creating a knife is divided into number of steps where each
 * step is separate JSF view. In order to use single bean, conversation scope is used.
 */
@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class KnifeCreate implements Serializable {

    /**
     * Service for managing knives.
     */
    private KnifeService knifeService;

    /**
     * Service for managing categorys.
     */
    private CategoryService categoryService;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * knife exposed to the view.
     */
    @Getter
    private KnifeCreateModel knife;

    /**
     * Available categorys.
     */
    @Getter
    private List<CategoryModel> categories;

    /**
     * Injected conversation.
     */
    private final Conversation conversation;

    @Inject
    public KnifeCreate(
            ModelFunctionFactory factory,
            Conversation conversation
    ) {
        this.factory = factory;
        this.conversation = conversation;
    }

    @EJB
    public void setKnifeService(KnifeService knifeService) {
        this.knifeService = knifeService;
    }

    @EJB
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view. @PostConstruct method is called after h:form header is already
     * rendered. Conversation should be started in f:metadata/f:event.
     */
    public void init() {
        if (conversation.isTransient()) {
            categories = categoryService.findAll().stream()
                    .map(factory.categoryToModel())
                    .collect(Collectors.toList());
            knife = KnifeCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    /**
     * @return category navigation case
     */
    public String goToCategoryAction() {
        return "/knife/knife_create__category.xhtml?faces-redirect=true";
    }

    /**
     * @return basic information navigation case
     */
    public Object goToBasicAction() {
        return "/knife/knife_create__basic.xhtml?faces-redirect=true";
    }

    /**
     * Cancels knife creation process.
     *
     * @return knives list navigation case
     */
    public String cancelAction() {
        conversation.end();
        return "/knife/knife_list.xhtml?faces-redirect=true";
    }

    /**
     * Sets default knife properties (leve land health).
     *
     * @return confirmation navigation case
     */
    public String goToConfirmAction() {
        return "/knife/knife_create__confirm.xhtml?faces-redirect=true";
    }

    /**
     * Stores new knife and ends conversation.
     *
     * @return knives list navigation case
     */
    public String saveAction() {
        knifeService.create(factory.modelToKnife().apply(knife));
        conversation.end();
        return "/knife/knife_list.xhtml?faces-redirect=true";
    }

    /**
     * @return current conversation id
     */
    public String getConversationId() {
        return conversation.getId();
    }


}
