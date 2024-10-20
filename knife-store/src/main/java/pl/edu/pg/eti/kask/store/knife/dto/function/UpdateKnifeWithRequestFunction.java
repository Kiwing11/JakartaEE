package pl.edu.pg.eti.kask.store.knife.dto.function;

import pl.edu.pg.eti.kask.store.knife.dto.PatchKnifeRequest;
import pl.edu.pg.eti.kask.store.knife.dto.PutKnifeRequest;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;

import java.util.function.BiFunction;

public class UpdateKnifeWithRequestFunction implements BiFunction<Knife, PatchKnifeRequest, Knife> {

    @Override
    public Knife apply(Knife entity, PatchKnifeRequest request) {
        return Knife.builder()
                .id(entity.getId())
                .name(request.getName())
                .bladeLength(request.getBladeLength())
                .productionDate(request.getProductionDate())
                .category(entity.getCategory())
                .build();
    }
}
