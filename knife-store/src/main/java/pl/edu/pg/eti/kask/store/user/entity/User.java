package pl.edu.pg.eti.kask.store.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    private UUID id;
    private String login;
    private String name;
    private String surname;
    private String email;
    @ToString.Exclude
    private String password;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String photo;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Knife> knives;

    @CollectionTable(name = "user__roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}
