package pl.edu.pg.eti.kask.store.knife.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetCategoryResponse {
    private UUID id;
    private String name;
    private String description;
}
