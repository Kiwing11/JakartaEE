package pl.edu.pg.eti.kask.store.knife.model.function;

import pl.edu.pg.eti.kask.store.knife.entity.Knife;
import pl.edu.pg.eti.kask.store.knife.model.KnifeEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class KnifeToEditModelFunction implements Function<Knife, KnifeEditModel>, Serializable {

    @Override
    public KnifeEditModel apply(Knife entity) {
        return KnifeEditModel.builder()
                .name(entity.getName())
                .bladeLength(entity.getBladeLength())
                .productionDate(entity.getProductionDate())
                .version(entity.getVersion())
                .build();
    }

}
