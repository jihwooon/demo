package com.codesoom.demo.application;

import com.codesoom.demo.domain.User;
import com.codesoom.demo.domain.UserRepository;
import com.codesoom.demo.dto.UserRegistrationData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserService( Mapper dozerMapper, UserRepository userRepository) {
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }

    public User registerUser(UserRegistrationData registrationData) {
        User user = mapper.map(registrationData, User.class);

    /*No Dozer Mapper*/
//        User user = User.builder()
//                .email(userRegistrationData.getEmail())
//                .name(userRegistrationData.getName())
//                .password(userRegistrationData.getPassword())
//                .build();

        return userRepository.save(user);
    }

}
