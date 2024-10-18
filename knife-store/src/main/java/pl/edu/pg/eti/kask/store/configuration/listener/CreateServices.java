package pl.edu.pg.eti.kask.store.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.edu.pg.eti.kask.store.datastore.component.DataStore;
import pl.edu.pg.eti.kask.store.service.PhotoService;
import pl.edu.pg.eti.kask.store.user.repository.api.UserRepository;
import pl.edu.pg.eti.kask.store.user.repository.memory.UserInMemoryRepository;
import pl.edu.pg.eti.kask.store.user.service.UserService;

@WebListener
public class CreateServices implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent event) {
            DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");
            PhotoService photoService = (PhotoService) event.getServletContext().getAttribute("photoService");
            UserRepository userRepository = new UserInMemoryRepository(dataSource);
            event.getServletContext().setAttribute("userService", new UserService(userRepository));
            event.getServletContext().setAttribute("photoService", new PhotoService(event.getServletContext().getInitParameter("photoDirectory")));
        }
}
