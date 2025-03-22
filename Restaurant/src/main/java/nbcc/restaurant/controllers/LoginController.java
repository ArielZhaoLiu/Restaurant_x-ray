package nbcc.restaurant.controllers;

import jakarta.validation.Valid;
import nbcc.restaurant.entities.UserDetail;
import nbcc.restaurant.services.LoginService;
import nbcc.restaurant.services.UserService;
import nbcc.restaurant.viewmodels.LoginViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final UserService userService;

    public LoginController(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new LoginViewModel());
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute("login") LoginViewModel loginViewModel) {

        if(loginService.login(loginViewModel.getUsername(), loginViewModel.getPassword())) {
            return "redirect:/"; // login was successful;
        }

        model.addAttribute("message", "Invalid username or password");

        return "login";
    }

    @PostMapping("/logout")
    public String logout(){
        loginService.logout();
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userDetail", new UserDetail());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid UserDetail userDetail, BindingResult bindingResult, Model model) {
        var username = userDetail.getUsername();

        if(username == null || username.isBlank()){
            return "registration";
        }

        var exists = userService.usernameExists(userDetail.getUsername());
        if(exists){
            return "registration"; // already exists
        }

        var userPassword = userDetail.getPassword();
        var userEmail = userDetail.getEmail();
        var UserFirstName = userDetail.getFirstName();
        var UserLastName = userDetail.getLastName();

        if (userPassword == null || userPassword.isBlank() || userEmail == null || userEmail.isBlank()){
            return "registration";
        }

        if(userService.register(username, userPassword, userEmail, UserFirstName, UserLastName)!=null){
            System.out.println("Admin user registered : " + username);
        }
        else{
            System.out.println("Admin user could not registered : " + username);
        }
        return "redirect:/login";
    }
}
