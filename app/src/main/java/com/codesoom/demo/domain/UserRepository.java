package com.codesoom.demo.domain;

public interface UserRepository {
    User save(User user);

    boolean existsByEmail(String email);
}
