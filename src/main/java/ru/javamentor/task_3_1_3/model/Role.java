package ru.javamentor.task_3_1_3.model;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {

    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
