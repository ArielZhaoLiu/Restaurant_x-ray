package nbcc.restaurant.initialize;

import nbcc.restaurant.config.environment.DefaultAdminConfig;
import nbcc.restaurant.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class AuthInitializer {

    private final UserService userService;
    private final DefaultAdminConfig defaultAdminConfig;

    public AuthInitializer(UserService userService, DefaultAdminConfig defaultAdminConfig) {
        this.userService = userService;
        this.defaultAdminConfig = defaultAdminConfig;
    }

    public void initialAdminUsers(){
        var adminUsername= defaultAdminConfig.getDefaultAdminUsername();
        if(adminUsername == null || adminUsername.isBlank()){
            return;
        }

        var exists = userService.usernameExists(adminUsername);
        if(exists){
            return; //admin already exists
        }

        var adminPassword = defaultAdminConfig.getDefaultAdminPassword();
        var adminmEmail = defaultAdminConfig.getDefaultAdminEmail();

        if (adminPassword == null || adminPassword.isBlank() || adminmEmail == null || adminmEmail.isBlank()){
            return;
        }

       if(userService.register(adminUsername, adminPassword, adminmEmail, "Admin", "Admin")!=null){
           System.out.println("Admin user registered : " + adminUsername);
       }
       else{
           System.out.println("Admin user could not registered : " + adminUsername);
       }


    }
}
