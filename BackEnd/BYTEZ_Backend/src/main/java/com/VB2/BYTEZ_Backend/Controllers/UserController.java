package com.VB2.BYTEZ_Backend.Controllers;

import com.VB2.BYTEZ_Backend.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.VB2.BYTEZ_Backend.Repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping(path="/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/")
    public @ResponseBody Iterable<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody Optional<User> getUserById(@PathVariable("id") Long id)
    {
        return userRepository.findById(id);
    }

    @GetMapping(path="/login")
    public @ResponseBody Optional<User> login(@RequestParam String email, @RequestParam String password)
    {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @PostMapping(path="/register")
    public @ResponseBody String addNewUserBody(@RequestBody User user)
    {
        userRepository.save(user);

        return "{\"status\":\"Success\"}";
    }

    @PostMapping(path="/register/params")
    public @ResponseBody String addNewUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String userName,
                                           @RequestParam String password, @RequestParam String email, @RequestParam String userType)
    {
        // Create new user
        User n = new User();

        // Set all the fields
        n.setFirstName(firstName);
        n.setLastName(lastName);
        n.setUserName(userName);
        n.setPassword(password);
        n.setEmail(email);
        n.setUserType(userType);

        // Save to repository
        userRepository.save(n);

        return "{\"status\":\"Success\"}";
    }

    // TODO - Add more PUT Requests depending on what frontend needs

    // TODO - Figure out if this request needs to be a string param or if it needs to be body entity
    @PutMapping(path = "/updateUserName/{id}")
    public @ResponseBody User updateUserName(@PathVariable("id") Long id, @RequestBody User newUser)
    {
        return userRepository.findById(id)
        .map(user -> {
            user.setUserName(newUser.getUserName());
            return userRepository.save(user);
        })
        .orElse(null);
    }

    // TODO - Figure out if this needs to be a string param or body entity.
    @PutMapping(path = "/updateUserInfo/{id}")
    public @ResponseBody User updateInfo(@PathVariable("id") Long id, @RequestParam String firstName)
    {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(firstName);
                    return userRepository.save(user);
                })
                .orElse(null);
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody String deleteUserById(@PathVariable("id") Long id)
    {
        userRepository.deleteById(id);
        return "{\"status\":\"Success\"}";
    }
}
