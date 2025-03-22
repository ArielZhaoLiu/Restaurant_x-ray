package nbcc.restaurant.services;

import jakarta.servlet.http.HttpSession;
import nbcc.restaurant.entities.UserDetail;
import nbcc.restaurant.entities.UserLogin;
import nbcc.restaurant.repositories.UserLoginRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginServiceImpl implements LoginService {


    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
    private static final String LOGIN_USER_ID = "LoginUserId";
    private static final String IS_USER_LOGGED_IN = "loggedIn";
    private static final String LOGIN_ID = "LoginId";

    private final UserLoginRepository userLoginRepository;
    private final HttpSession session;
    private final UserService userService;

    public LoginServiceImpl(UserLoginRepository userLoginRepository, HttpSession session, UserService userService) {
        this.userLoginRepository = userLoginRepository;
        this.session = session;
        this.userService = userService;
    }

    @Override
    public boolean login(String username, String password) {
        if(userService.valid(username, password)) {
            var user = userService.getUserByUsername(username);

            var userLogin = createNewUserLogin(user);

            log.info("User  {} logged in successfully", username);

            session.setAttribute(LOGIN_ID, userLogin.getId());
            session.setAttribute(LOGIN_USER_ID, user.getId());
            session.setAttribute(IS_USER_LOGGED_IN, true);

            return true;
        }else{
            log.info("User  {} failed to login", username);
        }


        return false;
    }

    private UserLogin createNewUserLogin(UserDetail userDetail) {
        var login = new UserLogin(userDetail);
        userLoginRepository.save(login);
        return login;

    }

    private Long getLoggedInUserId(){
        var userIdAttribute = session.getAttribute(LOGIN_USER_ID);

        if(userIdAttribute == null) {
            return null;
        }

        var userIdString = userIdAttribute.toString();

        try{
            return Long.parseLong(userIdString);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public UserDetail getLoggedInUser(){
        Long userId = getLoggedInUserId();
        if(userId == null) {
            return null;
        }

        return userService.findUser(userId).orElse(null);
    }

    @Override
    public void logout() {
        var login = getCurrentLogin();
        if(login != null) {
            login.setLoggedOut(LocalDateTime.now());
            login.setActive(false);
            userLoginRepository.save(login);
        }
        session.removeAttribute(LOGIN_ID);
        session.removeAttribute(LOGIN_USER_ID);
        session.removeAttribute(IS_USER_LOGGED_IN);

    }

    public boolean IsCurrenLoginValid(){
/*        var user = getLoggedInUser();
        return user != null;*/

        var login  = getCurrentLogin();

        return login != null && login.isActive();
    }

    @Override
    public void updateLastActivity() {
        var login = getCurrentLogin();
        log.info("Session Timeout set to {}", session.getMaxInactiveInterval());
        if(login != null) {
            var lastActivity = LocalDateTime.now();
            log.info("Updating last activity for {} to {}", login.getUserDetail().getUsername(), lastActivity);
            login.setLasActivity(LocalDateTime.now());
            userLoginRepository.save(login);
        }
    }

    private UserLogin getCurrentLogin(){

        var loginAttribute = session.getAttribute(LOGIN_ID);
        if(loginAttribute == null) {
            return null;
        }
        return userLoginRepository.findById(loginAttribute.toString()).orElse(null);
    }

}
