package com.VB2.BYTEZ_Backend.Controllers;

import com.VB2.BYTEZ_Backend.Domain.Friendship;
import com.VB2.BYTEZ_Backend.Domain.Restaurant;
import com.VB2.BYTEZ_Backend.Domain.Review;
import com.VB2.BYTEZ_Backend.Domain.User;
import com.VB2.BYTEZ_Backend.Repositories.FriendshipRepository;
import com.VB2.BYTEZ_Backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.VB2.BYTEZ_Backend.Repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping(path="/")
    public @ResponseBody List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody User getUserById(@PathVariable("id") Long id)
    {
        return userService.getUser(id);
    }

    @GetMapping(path="/login")
    public @ResponseBody User login(@RequestParam String email, @RequestParam String password)
    {
        return userService.loginUser(email, password);
    }

    @GetMapping(path = "/reviews/{userId}")
    public @ResponseBody List<Review> getReviewsById(@PathVariable("userId") Long id)
    {
        return userService.getAllReviewsByUser(id);
    }

    @GetMapping(path = "/restaurant/{userId}")
    public @ResponseBody Restaurant getUserRestaurant(@PathVariable("userId") Long id)
    {
        return userService.getUserRestaurant(id);
    }

    @GetMapping(path = "/getFriends/{userId}")
    public @ResponseBody List<User> getFriends(@PathVariable("userId")Long id)
    {
        return userService.getFriends(id);
    }

    @GetMapping(path = "/getFriendRequests/{userId}")
    public @ResponseBody List<User> getFriendRequests(@PathVariable("userId")Long id)
    {
        return userService.getFriendRequests(id);
    }

    @PostMapping(path="/register")
    public @ResponseBody String addNewUserBody(@RequestBody User user)
    {
       return userService.registerUser(user);
    }

    @GetMapping(path = "/getCritFood/{userId}")
    public @ResponseBody int getCritFood(@PathVariable("userId") Long userId){
        return userService.getCritFood(userId);
    }

    @GetMapping(path = "/getCritService/{userId}")
    public @ResponseBody int getCritService(@PathVariable("userId") Long userId){
        return userService.getCritService(userId);
    }

    @GetMapping(path = "/getCritClean/{userId}")
    public @ResponseBody int getCritClean(@PathVariable("userId") Long userId){
        return userService.getCritClean(userId);
    }

    @PutMapping(path = "/changeCritFood/{userId}")
    public @ResponseBody String changeCritFood(@PathVariable("userId") Long userId, @RequestParam int value){
        userService.changeCritFood(userId, value);
        return "{\"status\":\"Success\"}";
    }

    @PutMapping(path = "/changeCritService/{userId}")
    public @ResponseBody String changeCritService(@PathVariable("userId") Long userId, @RequestParam int value){
        userService.changeCritService(userId, value);
        return "{\"status\":\"Success\"}";
    }

    @PutMapping(path = "/changeCritClean/{userId}")
    public @ResponseBody String changeCritClean(@PathVariable("userId") Long userId, @RequestParam int value){
        userService.changeCritClean(userId, value);
        return "{\"status\":\"Success\"}";
    }

    @PutMapping(path = "/changeCritValues/{userId}")
    public @ResponseBody String changeCritValues(@PathVariable("userId") Long userId, @RequestParam int critFood,
                                                 @RequestParam int critClean, @RequestParam int critService){
       userService.changeCritValues(userId, critFood, critClean, critService);
       return "{\"status\":\"Success\"}";
    }




    /* This should not be used anymore
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
    */

    @PutMapping(path = "/updateUserName/{userId}")
    public @ResponseBody User updateUserName(@PathVariable("userId") Long id, @RequestParam String userName)
    {
        return userService.updateUserUserName(id, userName);
    }

    @PutMapping(path = "/updateUserInfo/{id}")
    public @ResponseBody User updateInfo(@PathVariable("id") Long id, @RequestBody User newUser)
    {
        return userService.updateUserInfo(id, newUser);
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody String deleteUserById(@PathVariable("id") Long id)
    {
        return userService.deleteUser(id);
    }
}
