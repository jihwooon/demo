package com.codesoom.demo.application;

import com.codesoom.demo.domain.User;
import com.codesoom.demo.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser() {
        return null;
    }

    public void deleteUser() {

    }

}
