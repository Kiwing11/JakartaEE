package pl.edu.pg.eti.kask.store.knife.model.function;

import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;
import pl.edu.pg.eti.kask.store.knife.model.KnifeEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateKnifeWithModelFunction implements BiFunction<Knife, KnifeEditModel, Knife>, Serializable {

    @Override
    @SneakyThrows
    public Knife apply(Knife entity, KnifeEditModel request) {
        return Knife.builder()
                .id(entity.getId())
                .name(request.getName())
                .bladeLength(request.getBladeLength())
                .productionDate(request.getProductionDate())
                .category(entity.getCategory())
                .build();
    }

}
