package pl.edu.pg.eti.kask.store.user.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class User implements Serializable {
    private UUID id;
    private String login;
    private String name;
    private String surname;
    @ToString.Exclude
    private String password;
    private LocalDate birthDate;
    @EqualsAndHashCode.Exclude
    private String photo;
}
