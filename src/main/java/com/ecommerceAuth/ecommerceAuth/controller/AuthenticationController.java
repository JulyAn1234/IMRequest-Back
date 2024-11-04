package com.ecommerceAuth.ecommerceAuth.controller;

import com.ecommerceAuth.ecommerceAuth.model.dtos.LoginUserDto;
import com.ecommerceAuth.ecommerceAuth.model.dtos.RegisterUserDto;
import com.ecommerceAuth.ecommerceAuth.model.entities.User;
import com.ecommerceAuth.ecommerceAuth.model.responses.LoginResponse;
import com.ecommerceAuth.ecommerceAuth.model.responses.UserResponse;
import com.ecommerceAuth.ecommerceAuth.service.AuthenticationService;
import com.ecommerceAuth.ecommerceAuth.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        return ResponseEntity.ok(generateLoginResponse(authenticatedUser));
    }

    private LoginResponse generateLoginResponse(User authenticatedUser){
        String jwtToken = jwtService.generateToken(authenticatedUser);

        return LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
    }


}