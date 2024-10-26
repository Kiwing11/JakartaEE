package pl.edu.pg.eti.kask.store.knife.view;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.store.factory.ModelFunctionFactory;
import pl.edu.pg.eti.kask.store.knife.model.CategoryCreateModel;
import pl.edu.pg.eti.kask.store.knife.service.CategoryService;

import java.io.Serializable;
import java.util.UUID;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class CategoryCreate implements Serializable {

    private final CategoryService service;

    private final ModelFunctionFactory factory;

    @Getter
    private CategoryCreateModel category;

    private final Conversation conversation;

    @Inject
    public CategoryCreate(
            CategoryService service,
            ModelFunctionFactory factory,
            Conversation conversation
    ) {
        this.service = service;
        this.factory = factory;
        this.conversation = conversation;
    }

    public void init() {
        if(conversation.isTransient()) {
            category = CategoryCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    public Object goToBasicAction() {
        return "/category/category_create__basic.xhtml?faces-redirect=true";
    }

    public String goToConfirmAction() {
        return "/category/category_create__confirm.xhtml?faces-redirect=true";
    }

    public String cancelAction() {
        conversation.end();
        return "/category/category_list.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        service.create(factory.modelToCategory().apply(category));
        conversation.end();
        return "/category/category_list.xhtml?faces-redirect=true";
    }

    public String getConversationId() {
        return conversation.getId();
    }
}
