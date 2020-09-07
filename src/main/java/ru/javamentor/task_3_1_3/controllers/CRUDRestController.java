package ru.javamentor.task_3_1_3.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javamentor.task_3_1_3.model.Role;
import ru.javamentor.task_3_1_3.model.User;
import ru.javamentor.task_3_1_3.service.UserService;
import ru.javamentor.task_3_1_3.util.UserValidator;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class CRUDRestController {

    private final UserService userService;
    private final UserValidator userValidator;

    public CRUDRestController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }


    @GetMapping
    public ModelAndView admin(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        String email = authentication.getName();
        User user = (User) userService.loadUserByUsername(email);
//        List<User> users = userService.findAll();
//        modelAndView.addObject("users", users);
        modelAndView.addObject("authentication", authentication);
        modelAndView.addObject("usernew", new User());
        modelAndView.addObject("roles", Role.values());
        modelAndView.setViewName("admin-page");
        return modelAndView;
    }



    @GetMapping("/list")
    public  ResponseEntity<List<User>> getUserList() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/save")
    public ResponseEntity<Void> saveUser(@RequestBody  User user) {
//        System.out.println(user);
        System.out.println(user.toString());
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }





//    @GetMapping("/update")
//    public ModelAndView updateUser(@RequestParam("id") Long id, ModelAndView modelAndView) {
//        User user = userService.findById(id);
//        modelAndView.addObject("userEdit", user);
//        modelAndView.setViewName("edit-user");
//        return modelAndView;
//    }

//
//    @PostMapping("/update")
//    public ModelAndView updatePost(@ModelAttribute("userEdit") User user) {
//        userService.saveUser(user);
//        return new ModelAndView("redirect:/admin");
//    }


//    @GetMapping("/delete")
//    public ModelAndView deleteUser(@RequestParam("id") Long id, ModelAndView modelAndView) {
//        User user = userService.findById(id);
//        modelAndView.addObject("userDelete", user);
//        modelAndView.setViewName("delete-user");
//        return modelAndView;
//    }


//    @PostMapping("/admin/delete{id}")
//    public ModelAndView deletePost(@RequestParam("id") Long id, ModelAndView model) {
//        userService.deleteById(id);
//        return new ModelAndView("redirect:/admin");
//    }


    @GetMapping("/add")
    @ResponseBody
    public User addGet() {
        return new User();
    }
//
//    @PostMapping({"/admin/add"})
//    public ModelAndView addPost(@ModelAttribute("usernew") User user, BindingResult result) {
//
//        userValidator.validate(user, result);
//        if (result.hasErrors()) {
//            ModelAndView modelAndView = new ModelAndView();
//            modelAndView.setViewName("/admin");
//            return modelAndView;
//        }
//        userService.saveUser(user);
//        return new ModelAndView("redirect:/admin");
//    }

//
//    @GetMapping("/admin/all")
//    @ResponseBody
//    public List<User> allGet() {
//        return userService.findAll();
//    }

//
    @GetMapping("/role")
    @ResponseBody
    public Set<Role> roleGet() {
        Set<Role> roles = new HashSet<>();
        Collections.addAll(roles, Role.values());
        return roles;
    }

}

