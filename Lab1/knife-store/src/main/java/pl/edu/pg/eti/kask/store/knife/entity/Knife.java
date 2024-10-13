package pl.edu.pg.eti.kask.store.knife.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.store.user.entity.User;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode
public class Knife {
    private String name;
    private User user;
    private Category category;
    private double bladeLength;
    private KnifeType type;
    private LocalDate productionDate;
}
