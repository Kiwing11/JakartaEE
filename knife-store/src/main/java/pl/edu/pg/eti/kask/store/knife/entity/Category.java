package pl.edu.pg.eti.kask.store.knife.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "categories")
public class Category implements Serializable {
    @Id
    private UUID id;
    private String name;
    private String description;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "category", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Knife> knives;
}
