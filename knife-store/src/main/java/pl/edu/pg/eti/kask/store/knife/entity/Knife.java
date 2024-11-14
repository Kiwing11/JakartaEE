package pl.edu.pg.eti.kask.store.knife.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "knives")
public class Knife implements Serializable {
    @Id
    private UUID id;
    private String name;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    private User user;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private double bladeLength;

    @CollectionTable(name = "knives_types", joinColumns = @JoinColumn(name = "knife_id"))
    @Column(name = "type")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> type;
    private LocalDate productionDate;
}
