package com.example.omaapinions.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.omaapinions.dto.UserDto;
import com.example.omaapinions.models.UserSurvey;
import com.example.omaapinions.models.UserSurvey.RoleEnum;
import com.example.omaapinions.repository.UserRepository;

@Service
public class UserService {

    @SuppressWarnings("FieldMayBeFinal")
    private UserRepository userRepository;
    @SuppressWarnings("FieldMayBeFinal")
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(UserDto userDto) {
        UserSurvey user = new UserSurvey();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Set<RoleEnum> roles = new HashSet<>();

        roles.add(RoleEnum.USER);
        user.setRoles(roles);

        if (userDto.getEmail().endsWith("@admin.com")) {
            roles.add(RoleEnum.ADMIN);
        }

        user.setRoles(roles);

        this.userRepository.save(user);
    }

    public UserSurvey findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public UserSurvey findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
