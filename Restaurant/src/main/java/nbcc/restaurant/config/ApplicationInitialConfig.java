package nbcc.restaurant.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import nbcc.restaurant.initialize.AuthInitializer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationInitialConfig implements ServletContextInitializer {

    private final AuthInitializer authInitializer;

    public ApplicationInitialConfig(AuthInitializer authInitializer) {
        this.authInitializer = authInitializer;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        //servletContext.setSessionTimeout(1); //  minutes

        authInitializer.initialAdminUsers();
    }
}
