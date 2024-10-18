package pl.edu.pg.eti.kask.store.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.store.user.entity.User;
import pl.edu.pg.eti.kask.store.user.service.UserService;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

@WebListener
public class InitializedData implements ServletContextListener {

    private UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        userService = (UserService) event.getServletContext().getAttribute("userService");
        init();
    }
    @SneakyThrows
    private void init() {
        User zbigniew = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .login("ztestowy")
                .name("Zbigniew")
                .surname("Testowy")
                .password("123")
                .birthDate(LocalDate.of(1990, 1, 1))
                .photo("photo1.jpg")
                .build();

        User jacek = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a7"))
                .login("jtestowy")
                .name("Jacek")
                .surname("Testowy")
                .password("123")
                .birthDate(LocalDate.of(1990, 2, 1))
                .photo("photo2.jpg")
                .build();

        User mariusz = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a8"))
                .login("mtestowy")
                .name("Mariusz")
                .surname("Testowy")
                .password("123")
                .birthDate(LocalDate.of(1990, 3, 1))
                .photo("photo3.jpg")
                .build();

        User blazej = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a9"))
                .login("btestowy")
                .name("Blazej")
                .surname("Testowy")
                .password("123")
                .birthDate(LocalDate.of(1990, 3, 1))
                .photo("photo4.jpg")
                .build();

        userService.create(zbigniew);
        userService.create(jacek);
        userService.create(mariusz);
        userService.create(blazej);
    }
}
