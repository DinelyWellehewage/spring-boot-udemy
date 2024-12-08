package com.example.spring_boot_jwt.controller;


import com.example.spring_boot_jwt.exceptionHanlder.ResourceNotFoundException;
import com.example.spring_boot_jwt.model.User;
import com.example.spring_boot_jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController // It's a convenience annotation that is itself annotated with
// @Controller and @ResponseBody
//This annotation tells spring that the class is a web controller
// and that the return value of each method should be written
// directly to the HTTP response body rather that being rendered as a view.
@RequestMapping("/api/users")
//This annotation used to map web requests to specific handler classes or handler methods
public class UserController {

    @Autowired // do dependency injection
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping
    //this is a composed annotation that acts as a GET method
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with id "+id));
    }
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,@RequestBody User user){
        User userData = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found with id"+id));
        userData.setEmail(user.getEmail());
        userData.setName(user.getName());
        return userRepository.save(userData);
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not found"));
        userRepository.delete(user);
        return ResponseEntity.ok().body(user);
    }
}
