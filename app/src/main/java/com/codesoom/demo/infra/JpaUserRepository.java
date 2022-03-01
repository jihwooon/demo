package com.codesoom.demo.infra;

import com.codesoom.demo.domain.User;
import com.codesoom.demo.domain.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Primary
public interface JpaUserRepository
    extends UserRepository, JpaRepository<User, Long> {

    User save(User user);

    boolean existsByEmail(String email);

}
