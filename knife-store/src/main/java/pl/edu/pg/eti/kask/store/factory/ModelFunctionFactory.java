package pl.edu.pg.eti.kask.store.factory;

import jakarta.enterprise.context.ApplicationScoped;
import pl.edu.pg.eti.kask.store.knife.model.function.*;

@ApplicationScoped
public class ModelFunctionFactory {

    public KnifeToModelFunction knifeToModel(){
        return new KnifeToModelFunction();
    }

    public KnivesToModelFunction knivesToModel(){
        return new KnivesToModelFunction();
    }

    public KnifeToEditModelFunction knifeToEditModel(){
        return new KnifeToEditModelFunction();
    }

    public ModelToKnifeFunction modelToKnife(){
        return new ModelToKnifeFunction();
    }

    public CategoryToModelFunction categoryToModel(){
        return new CategoryToModelFunction();
    }

    public UpdateKnifeWithModelFunction updateKnife(){
        return new UpdateKnifeWithModelFunction();
    }

    public CategoriesToModelFunction categoriesToModel(){
        return new CategoriesToModelFunction();
    }

    public CategoryToEditModelFunction categoryToEditModel(){
        return new CategoryToEditModelFunction();
    }

    public ModelToCategoryFunction modelToCategory(){
        return new ModelToCategoryFunction();
    }

    public UpdateCategoryWithModelFunction updateCategory(){
        return new UpdateCategoryWithModelFunction();
    }
}
