package pl.edu.pg.eti.kask.store.knife.dto;

import lombok.*;
import pl.edu.pg.eti.kask.store.knife.entity.KnifeType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetKnifeResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Category {
        private UUID id;
        private String name;
    }

    private UUID id;
    private String name;
    private double bladeLength;
    private LocalDate productionDate;
    private Category category;
    private Long version;
}
