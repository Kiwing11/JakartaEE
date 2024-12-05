package pl.edu.pg.eti.kask.store.configuration.observer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.ServletContextListener;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.store.knife.entity.Category;
import pl.edu.pg.eti.kask.store.knife.entity.Knife;
import pl.edu.pg.eti.kask.store.knife.entity.KnifeType;
import pl.edu.pg.eti.kask.store.knife.service.CategoryService;
import pl.edu.pg.eti.kask.store.knife.service.KnifeService;
import pl.edu.pg.eti.kask.store.user.entity.User;
import pl.edu.pg.eti.kask.store.user.entity.UserRoles;
import pl.edu.pg.eti.kask.store.user.service.UserService;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@NoArgsConstructor
@DependsOn("InitializeAdminService")
@DeclareRoles({UserRoles.ADMIN, UserRoles.USER})
@RunAs(UserRoles.ADMIN)
@Log
public class InitializedData{

    private UserService userService;

    private KnifeService knifeService;

    private CategoryService categoryService;

    @Inject
    private SecurityContext securityContext;

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @EJB
    public void setKnifeService(KnifeService knifeService) {
        this.knifeService = knifeService;
    }

    @EJB
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (userService.find("admin").isEmpty()) {
            System.out.println("Creating initial data");
            User admin = User.builder()
                    .id(UUID.randomUUID())
                    .name("Admin")
                    .surname("System")
                    .email("admin@knifestore.example.com")
                    .login("admin")
                    .password("admin")
                    .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                    .photo("photo1.jpg")
                    .build();

            User zbigniew = User.builder()
                    .id(UUID.randomUUID())
                    .login("ztestowy")
                    .name("Zbigniew")
                    .surname("Testowy")
                    .password("123")
                    .birthDate(LocalDate.of(1990, 1, 1))
                    .photo("photo1.jpg")
                    .roles(List.of(UserRoles.USER))
                    .build();

            User jacek = User.builder()
                    .id(UUID.randomUUID())
                    .login("jtestowy")
                    .name("Jacek")
                    .surname("Testowy")
                    .password("123")
                    .birthDate(LocalDate.of(1990, 2, 1))
                    .photo("photo2.jpg")
                    .roles(List.of(UserRoles.USER))
                    .build();

            User mariusz = User.builder()
                    .id(UUID.randomUUID())
                    .login("mtestowy")
                    .name("Mariusz")
                    .surname("Testowy")
                    .password("123")
                    .birthDate(LocalDate.of(1990, 3, 1))
                    .photo("photo3.jpg")
                    .roles(List.of(UserRoles.USER))
                    .build();

            User blazej = User.builder()
                    .id(UUID.randomUUID())
                    .login("btestowy")
                    .name("Blazej")
                    .surname("Testowy")
                    .password("123")
                    .birthDate(LocalDate.of(1990, 3, 1))
                    .photo("photo4.jpg")
                    .roles(List.of(UserRoles.USER))
                    .build();

            userService.create(admin);
            userService.create(zbigniew);
            System.out.println("User created: " + zbigniew);
            userService.create(jacek);
            userService.create(mariusz);
            userService.create(blazej);

            Category japanese = Category.builder()
                    .id(UUID.randomUUID())
                    .name("Japanese")
                    .description("Knives from Japan")
                    .build();

            Category thai = Category.builder()
                    .id(UUID.randomUUID())
                    .name("Thai")
                    .description("Knives from Thailand")
                    .build();

            Category chinese = Category.builder()
                    .id(UUID.randomUUID())
                    .name("Chinese")
                    .description("Knives from China")
                    .build();

            categoryService.create(japanese);
            categoryService.create(thai);
            categoryService.create(chinese);

            Knife knife1 = Knife.builder()
                    .id(UUID.randomUUID())
                    .name("Santoku")
                    .category(japanese)
                    .user(zbigniew)
                    .bladeLength(18.0)
                    .type(List.of(KnifeType.CHEF))
                    .productionDate(LocalDate.of(2020, 1, 1))
                    .build();

            Knife knife2 = Knife.builder()
                    .id(UUID.randomUUID())
                    .name("Nakiri")
                    .category(japanese)
                    .user(mariusz)
                    .bladeLength(16.0)
                    .type(List.of(KnifeType.CHEF))
                    .productionDate(LocalDate.of(2020, 2, 1))
                    .build();

            Knife knife3 = Knife.builder()
                    .id(UUID.randomUUID())
                    .name("Cleaver")
                    .category(chinese)
                    .user(blazej)
                    .bladeLength(20.0)
                    .type(List.of(KnifeType.CLEAVER))
                    .productionDate(LocalDate.of(2020, 3, 1))
                    .build();

            Knife knife4 = Knife.builder()
                    .id(UUID.randomUUID())
                    .name("Santoku")
                    .category(thai)
                    .user(zbigniew)
                    .bladeLength(18.0)
                    .type(List.of(KnifeType.CHEF))
                    .productionDate(LocalDate.of(2020, 4, 1))
                    .build();

            knifeService.create(knife1);
            knifeService.create(knife2);
            knifeService.create(knife3);
            knifeService.create(knife4);
        }
    }
}
