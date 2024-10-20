package pl.edu.pg.eti.kask.store.knife.dto.function;

import pl.edu.pg.eti.kask.store.knife.dto.PutKnifeRequest;
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToKnifeFunction implements BiFunction<UUID, PutKnifeRequest, Knife> {
    @Override
    public Knife apply(UUID id, PutKnifeRequest request) {
        return Knife.builder()
                .id(id)
                .name(request.getName())
                .bladeLength(request.getBladeLength())
                .productionDate(request.getProductionDate())
                .category(Category.builder()
                        .id(request.getCategory())
                        .build())
                .build();
    }
}
