package pl.edu.pg.eti.kask.store.knife.dto.function;

import pl.edu.pg.eti.kask.store.knife.dto.GetKnifeResponse;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;

import java.util.function.Function;

public class KnifeToResponseFunction implements Function<Knife, GetKnifeResponse> {
    @Override
    public GetKnifeResponse apply(Knife entity){
        return GetKnifeResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .bladeLength(entity.getBladeLength())
                .productionDate(entity.getProductionDate())
                .category(GetKnifeResponse.Category.builder()
                        .id(entity.getCategory().getId())
                        .name(entity.getCategory().getName())
                        .build())
                .version(entity.getVersion())
                .build();
    }
}
