package pl.edu.pg.eti.kask.store.knife.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchKnifeRequest {
    private String name;
    private double bladeLength;
    private LocalDate productionDate;
    private Long version;
}
