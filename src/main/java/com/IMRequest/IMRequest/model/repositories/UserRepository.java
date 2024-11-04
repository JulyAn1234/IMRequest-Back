package com.IMRequest.IMRequest.model.repositories;

import com.IMRequest.IMRequest.model.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
//    public List<User> getAllUsers();

    public Optional<User> getUserByUsername(String Username);
    public Optional<User> getUserById(String id);
//    public Optional<User> editUser(User user);
//    public Optional<User> deleteUser(String username);
}