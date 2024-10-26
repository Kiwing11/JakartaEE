package pl.edu.pg.eti.kask.store.knife.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CategoryEditModel {
    private String name;
    private String description;
}
