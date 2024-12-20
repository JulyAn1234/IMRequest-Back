package com.IMRequest.IMRequest.controller;

import com.IMRequest.IMRequest.model.dtos.LoginUserDto;
import com.IMRequest.IMRequest.model.dtos.RegisterUserDto;
import com.IMRequest.IMRequest.model.entities.User;
import com.IMRequest.IMRequest.model.responses.LoginResponse;
import com.IMRequest.IMRequest.model.responses.UserResponse;
import com.IMRequest.IMRequest.service.AuthenticationService;
import com.IMRequest.IMRequest.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/getUser/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<UserResponse> user = authenticationService.getUserByUsername(username);
        return user.map(ResponseEntity::ok) // Return the user
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Handle user not found
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        List<UserResponse> userList = authenticationService.getAllUsers();
        return userList.isEmpty()? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(userList);
    }

    //Send all the user object with the modified fields
    @PostMapping("/editUser/{id}")
    public ResponseEntity<?> editUser(@PathVariable String id, @RequestBody RegisterUserDto user) {
        Optional<UserResponse> updatedUser = authenticationService.editUser(id, user);
        return updatedUser.map(ResponseEntity::ok) // Return the updated user
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Handle user not found
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        System.out.println(registerUserDto);
        try {
            User registeredUser = authenticationService.signup(registerUserDto);

            //Sending back jwt
            return ResponseEntity.ok(generateLoginResponse(registeredUser));
        }
        catch (IllegalStateException e){
            return ResponseEntity.badRequest().build();
        }
        //Catch all other exceptions
        catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        System.out.println(loginUserDto);
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);

            return ResponseEntity.ok(generateLoginResponse(authenticatedUser));
        }
        catch (Exception e){
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }

    }

    private LoginResponse generateLoginResponse(User authenticatedUser){
        String jwtToken = jwtService.generateToken(authenticatedUser);

        return LoginResponse.builder()
                .Token(jwtToken)
                .ExpiresIn(jwtService.getExpirationTime())
                .User(UserResponse.builder()
                        .Username(authenticatedUser.getUsername())
                        .Name(authenticatedUser.getName())
                        .Rol(authenticatedUser.getRol())
                        .Unidad(authenticatedUser.getUnidad())
                        .isActive(authenticatedUser.isActive())
                        .Permissions(authenticatedUser.getPermissions())
                        .build())
                .build();
    }


}