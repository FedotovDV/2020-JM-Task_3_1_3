package ru.javamentor.task_3_1_3.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.javamentor.task_3_1_3.model.User;
import ru.javamentor.task_3_1_3.repository.UserRepository;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }


    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public User loadUserByUsername(String email){
        return userRepository.findByEmail(email);
    }
}
