package com.IMRequest.IMRequest.service;

import com.IMRequest.IMRequest.model.dtos.LoginUserDto;
import com.IMRequest.IMRequest.model.dtos.RegisterUserDto;
import com.IMRequest.IMRequest.model.entities.User;
import com.IMRequest.IMRequest.model.repositories.UserRepository;
import com.IMRequest.IMRequest.model.responses.UserResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        //CHECK IF USER IS ADMIN
        if(input.getRol() == 1){
            //CHECK IF USER EXISTS
            Optional<User> userop = userRepository.getUserByUsername(input.getUsername());

            if (userop.isPresent()) {
                throw new IllegalStateException("Username already taken");
            }
        }else{
            //default password and username
            input.setUsername("WorkerUser");
            input.setPassword("WorkerUser");
        }

        User user = User.builder()
                .username(input.getUsername())
                .name(input.getName())
                .password(passwordEncoder.encode(input.getPassword()))
                .rol(input.getRol())
                .unidad(input.getUnidad())
                .isActive(input.isActive())
                .permissions(input.getPermissions())
                .build();

        return userRepository.save(user);
    }

    public Optional<UserResponse> editUser(String id, RegisterUserDto updatedUser) {
        Optional<User> newUser =  userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setName(updatedUser.getName());
            user.setRol(updatedUser.getRol());
            user.setUnidad(updatedUser.getUnidad());
            user.setActive(updatedUser.isActive());
            user.setPermissions(updatedUser.getPermissions());
            return userRepository.save(user);
        });

        return newUser.map(UserResponse::new);

    }

    public User authenticate(LoginUserDto input) {
        //Calls getUserByUsername and validates password. Raises exception if incorrect
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        Optional<User> userop=  userRepository.getUserByUsername(input.getUsername());

        return userop.orElseThrow();
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username).map(UserResponse::new);
    }
}
