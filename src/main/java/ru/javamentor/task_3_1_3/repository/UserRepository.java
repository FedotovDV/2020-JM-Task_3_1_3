package ru.javamentor.task_3_1_3.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.javamentor.task_3_1_3.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
