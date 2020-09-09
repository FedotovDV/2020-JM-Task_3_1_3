package ru.javamentor.task_3_1_3.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        modelAndView.addObject("authentication", authentication);
        modelAndView.addObject("usernew", new User());
        modelAndView.addObject("roles", Role.values());
        modelAndView.setViewName("admin-page");
        return modelAndView;
    }


    @GetMapping("/list")
    public ResponseEntity<List<User>> getUserList() {
        System.out.println(" getUserList()");
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        System.out.println(" getUser" + id);
        return new ResponseEntity<User>(userService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        System.out.println("Update User " + user);
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        System.out.println("Delete User " + id);
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/add")
    @ResponseBody
    public User addGet() {
        return new User();
    }

    @GetMapping("/role")
    @ResponseBody
    public Set<Role> roleGet() {
        Set<Role> roles = new HashSet<>();
        Collections.addAll(roles, Role.values());
        return roles;
    }

}

