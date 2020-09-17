package com.VB2.BYTEZ_Backend.Controllers;

import com.VB2.BYTEZ_Backend.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.VB2.BYTEZ_Backend.Repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@Controller
@RequestMapping(path="/user")
public class UserController {
    @Autowired

    private UserRepository userRepository;
    private Long id;

    @PostMapping(path="/addNewUser")
    public @ResponseBody String addNewUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String userName,
                                           @RequestParam String password, @RequestParam String email)
    {
        // Create new user
        User n = new User();

        // Set all the fields
        n.setFirstName(firstName);
        n.setLastName(lastName);
        n.setUserName(userName);
        n.setPassword(password);
        n.setEmail(email);

        // Save to repository
        userRepository.save(n);

        return "New User Added Successfully!";
    }

    @GetMapping(path="/getAllUsers")
    public @ResponseBody Iterable<User> getAllUsers()
    {
       return userRepository.findAll();
    }

    @GetMapping(path="/getUser/{id}")
    public @ResponseBody Iterable<User> getUserById(@PathVariable("id") Long id)
    {
        return userRepository.findAllById(Collections.singleton(id));
    }
}
