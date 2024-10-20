package pl.edu.pg.eti.kask.store.knife.dto;

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
public class PutKnifeRequest {
    private String name;
    private double bladeLength;
    private LocalDate productionDate;
    private UUID category;
}
