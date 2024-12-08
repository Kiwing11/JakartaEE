package pl.edu.pg.eti.kask.store.knife.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class KnivesModel implements Serializable {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Knife {

        private UUID id;

        private String name;

        private Long version;

        private LocalDateTime creationDateTime;

        private LocalDateTime editionDateTime;

    }

    @Singular("knife")
    private List<Knife> knives;

}
