package com.codesoom.demo.infra;

import com.codesoom.demo.domain.User;
import com.codesoom.demo.domain.UserRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface JpaUserRepository
        extends UserRepository, CrudRepository<User, Long> {

    User save(User user);

    Optional<User> findByEmail(String email);

}
