package pl.edu.pg.eti.kask.store.knife.model;

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
public class CategoriesModel implements Serializable {

        @Getter
        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        @ToString
        @EqualsAndHashCode
        public static class Category {

            private UUID id;
            private String name;

        }

        @Singular("category")
        private List<Category> categories;
}
