package pl.edu.pg.eti.kask.store.knife.model.function;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;
import pl.edu.pg.eti.kask.store.knife.model.KnivesModel;

import java.util.List;
import java.util.function.Function;

public class KnivesToModelFunction implements Function<List<Knife>, KnivesModel> {

    @Override
    public KnivesModel apply(List<Knife> entity) {
        return KnivesModel.builder()
                .knives(entity.stream()
                        .map(knife -> KnivesModel.Knife.builder()
                                .id(knife.getId())
                                .name(knife.getName())
                                .build())
                        .toList())
                .build();
    }

}
