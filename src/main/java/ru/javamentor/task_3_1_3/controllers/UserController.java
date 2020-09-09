package ru.javamentor.task_3_1_3.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javamentor.task_3_1_3.model.Role;
import ru.javamentor.task_3_1_3.model.User;
import ru.javamentor.task_3_1_3.service.UserService;
import ru.javamentor.task_3_1_3.util.UserValidator;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "registration";
    }

    @PostMapping("registration")
    public ModelAndView saveUser(@ModelAttribute("user") User user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            modelAndView.setViewName("registration");
            return modelAndView;
        }
        userService.saveUser(user);
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @GetMapping(value = "user")
    public ModelAndView userForm(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();//(ModelAndView modelAndView, Principal principal) {
        String email = authentication.getName();
        User user = (User) userService.loadUserByUsername(email);
        modelAndView.addObject("user", user);
        modelAndView.addObject("authentication", authentication);
        modelAndView.setViewName("user");
        return modelAndView;
    }

//

    @GetMapping(value = "login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }
        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        return "login";
    }


    @GetMapping(value = {"/", "welcome"})
    public String welcome(Model model) {
        return "welcome";
    }


}