package pl.edu.pg.eti.kask.store.knife.model;

import lombok.*;
import pl.edu.pg.eti.kask.store.user.entity.User;
import pl.edu.pg.eti.kask.store.user.model.UserModel;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class KnifeEditModel {

    private String name;
    private double bladeLength;
    private LocalDate productionDate;
}
