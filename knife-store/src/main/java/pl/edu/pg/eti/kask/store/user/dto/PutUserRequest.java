package pl.edu.pg.eti.kask.store.user.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutUserRequest {
    private String login;
    private String name;
    private String surname;
    private String password;
    private LocalDate birthDate;
}
