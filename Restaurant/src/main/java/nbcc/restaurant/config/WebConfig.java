package nbcc.restaurant.config;

import nbcc.restaurant.interceptors.LoginInterceptor;
import nbcc.restaurant.interceptors.UserActivityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final UserActivityInterceptor userActivityInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor, UserActivityInterceptor userActivityInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.userActivityInterceptor = userActivityInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor).addPathPatterns(
                "/event/delete/**",
                "/event/edit/**",
                "/event/create/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns(
                "/seating/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns(
                "/layout/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns(
                "/menu/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns(
                "/menuItem");

        registry.addInterceptor(userActivityInterceptor);
    }
}
