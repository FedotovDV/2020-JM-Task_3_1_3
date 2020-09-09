package ru.javamentor.task_3_1_3.service;


import ru.javamentor.task_3_1_3.model.User;

import java.util.List;

public interface UserService  {
    User findById(Long id);

    List<User> findAll();

    User saveUser(User user);

    User updateUser(User user);

    void deleteById(Long id);


    User loadUserByUsername(String email);
}
