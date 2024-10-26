package pl.edu.pg.eti.kask.store.knife.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class KnifeModel {

    private String name;

    private double bladeLength;

    private LocalDate productionDate;

    private String category;
}
