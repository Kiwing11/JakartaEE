package pl.edu.pg.eti.kask.store.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.edu.pg.eti.kask.store.factory.DtoFunctionFactory;
import pl.edu.pg.eti.kask.store.service.PhotoService;
import pl.edu.pg.eti.kask.store.user.controller.api.UserController;
import pl.edu.pg.eti.kask.store.user.controller.impl.UserDefaultController;
import pl.edu.pg.eti.kask.store.user.service.UserService;

@WebListener
public class CreateControllers implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent event) {
            PhotoService photoService = (PhotoService) event.getServletContext().getAttribute("photoService");
            UserService userService = (UserService) event.getServletContext().getAttribute("userService");

            event.getServletContext().setAttribute("userController", new UserDefaultController(
                    userService,
                    new DtoFunctionFactory()
            ));
            event.getServletContext().setAttribute("photoService", new PhotoService(event.getServletContext().getInitParameter("photoDirectory")));
        }
}
