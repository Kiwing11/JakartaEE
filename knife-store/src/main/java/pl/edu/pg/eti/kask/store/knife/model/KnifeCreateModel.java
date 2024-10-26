package pl.edu.pg.eti.kask.store.knife.model;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class KnifeCreateModel {

    private UUID id;

    private String name;

    private double bladeLength;

    private LocalDate productionDate;

    private CategoryModel category;

}
