package pl.edu.pg.eti.kask.store.knife.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.store.user.entity.User;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode
public class Knife implements Serializable {
    private UUID id;
    private String name;
    private User user;
    private Category category;
    private double bladeLength;
    private List<String> type;
    private LocalDate productionDate;
}
