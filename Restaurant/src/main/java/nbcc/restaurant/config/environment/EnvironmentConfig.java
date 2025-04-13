package nbcc.restaurant.config.environment;

import nbcc.restaurant.config.EmailSenderConfig;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentConfig implements DefaultAdminConfig, EmailSenderConfig {

    private final Environment environment;

    public EnvironmentConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String getDefaultAdminUsername() {
        return environment.getProperty("Default.Admin.Username");
    }

    @Override
    public String getDefaultAdminPassword() {
        return environment.getProperty("Default.Admin.Password");
    }

    @Override
    public String getDefaultAdminEmail() {
        return environment.getProperty("Default.Admin.Email");
    }

    @Override
    public String getDefaultFrom() {
        return environment.getProperty("Default.From.Email");
    }
}
