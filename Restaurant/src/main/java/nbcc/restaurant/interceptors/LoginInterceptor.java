package nbcc.restaurant.interceptors;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nbcc.restaurant.repositories.UserLoginRepository;
import nbcc.restaurant.services.LoginService;
import nbcc.restaurant.services.LoginServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private final LoginService loginService;

    public LoginInterceptor(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request,
                             @Nonnull HttpServletResponse response,
                             @Nonnull Object handler) throws Exception {


        if(loginService.IsCurrenLoginValid()){
            return true;
        }

            response.sendRedirect(request.getContextPath() + "/login");

            return false;
    }
}
