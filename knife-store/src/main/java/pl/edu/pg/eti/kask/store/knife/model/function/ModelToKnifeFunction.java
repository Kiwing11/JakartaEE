package pl.edu.pg.eti.kask.store.knife.model.function;

import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;
import pl.edu.pg.eti.kask.store.knife.model.KnifeCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToKnifeFunction implements Function<KnifeCreateModel, Knife>, Serializable {

    @Override
    @SneakyThrows
    public Knife apply(KnifeCreateModel model) {
        return Knife.builder()
                .id(model.getId())
                .name(model.getName())
                .bladeLength(model.getBladeLength())
                .productionDate(model.getProductionDate())
                .category(Category.builder()
                        .id(model.getCategory().getId())
                        .build())
                .build();
    }

}
