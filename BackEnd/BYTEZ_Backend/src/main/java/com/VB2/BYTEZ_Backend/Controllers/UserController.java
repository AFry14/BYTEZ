package com.VB2.BYTEZ_Backend.Controllers;

import com.VB2.BYTEZ_Backend.Domain.User;
import org.json.JSONException;
import org.json.JSONObject;
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
    private Long id;

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

        return "New User Added Successfully!";
    }


    @GetMapping(path="/login")
    public @ResponseBody User login(@RequestParam String email, @RequestParam String password)
    {
        return userRepository.findByEmailAndPassword(email, password);
    }

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
}
