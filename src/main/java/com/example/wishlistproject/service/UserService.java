package com.example.wishlistproject.service;

import com.example.wishlistproject.model.User;
import com.example.wishlistproject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail()); /** Check if a user with the given email already exists **/

        if (existingUser.isPresent()){
            return false; /** User with that email already exists **/
        } else{
            userRepository.save(user);
            return true; /** User created sucesfully **/
        }
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User loginUser(String email, String password){
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            User aUser = user.get();
            if (aUser.getPassword().equals(password)){
                return aUser;
            } else {
                return null; /** Password is incorrect**/
            }
        }
        return null; /** User not found **/
    }

    public Optional<User> createUser(User user){
        Optional <User> existingUser = userRepository.findByEmail((user.getEmail()));
        if(existingUser.isPresent()){
            return Optional.empty(); /** User with that email already exists **/
        } else{
            return userRepository.save(user);
        }
    }


}