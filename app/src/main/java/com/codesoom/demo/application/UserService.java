package com.codesoom.demo.application;

import com.codesoom.demo.domain.User;
import com.codesoom.demo.dto.UserRegistrationData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    public User registerUser(UserRegistrationData userRegistrationData) {
            return null;
    }

}
