package pl.edu.pg.eti.kask.store.knife.dto.function;

import pl.edu.pg.eti.kask.store.knife.dto.GetKnivesResponse;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;

import java.util.List;
import java.util.function.Function;

public class KnivesToResponseFunction implements Function<List<Knife>, GetKnivesResponse> {
    @Override
    public GetKnivesResponse apply(List<Knife> entities) {
        return GetKnivesResponse.builder()
                .knives(entities.stream()
                .map(knife -> GetKnivesResponse.Knife.builder()
                        .id(knife.getId())
                        .name(knife.getName())
                        .build()
                ).toList())
            .build();
    }
}
