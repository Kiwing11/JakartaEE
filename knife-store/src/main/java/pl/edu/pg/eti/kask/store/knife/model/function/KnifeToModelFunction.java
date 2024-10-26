package pl.edu.pg.eti.kask.store.knife.model.function;

import pl.edu.pg.eti.kask.store.knife.entity.Knife;
import pl.edu.pg.eti.kask.store.knife.model.KnifeModel;

import java.io.Serializable;
import java.util.function.Function;

public class KnifeToModelFunction implements Function<Knife, KnifeModel>, Serializable {

    @Override
    public KnifeModel apply(Knife entity) {
        return KnifeModel.builder()
                .name(entity.getName())
                .bladeLength(entity.getBladeLength())
                .productionDate(entity.getProductionDate())
                .category(entity.getCategory().getName())
                .build();
    }

}
