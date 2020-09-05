package ru.javamentor.task_3_1_3.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.task_3_1_3.model.User;
import ru.javamentor.task_3_1_3.service.UserService;
import ru.javamentor.task_3_1_3.util.UserValidator;

import java.util.List;

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
    public  ResponseEntity<List<User>> getUserList() {
        return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return new ResponseEntity<User>(userService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PostMapping("/save")
    public ResponseEntity<Void> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}

