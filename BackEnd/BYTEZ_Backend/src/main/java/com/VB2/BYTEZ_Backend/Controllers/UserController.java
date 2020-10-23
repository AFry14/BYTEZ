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

    @GetMapping(path = "/reviews/{id}")
    public @ResponseBody List<Review> getReviewsById(@PathVariable("id") Long id)
    {
        return userService.getAllReviewsByUser(id);
    }

    @GetMapping(path = "/restaurant/{id}")
    public @ResponseBody Restaurant getUserRestaurant(@PathVariable("id") Long id)
    {
        return userService.getUserRestaurant(id);
    }

//    @GetMapping(path = "/getFriends")
//    public @ResponseBody Iterable<User> getFriends(@RequestBody User user)
//    {
//       Optional<User> u = userRepository.findById(user.getId());
//       if (u.isPresent())
//       {
//           Set<Friendship> friends = u.get().getFriends();
//           ArrayList<User> list = new ArrayList<User>();
//           for (Friendship f: friends)
//           {
//                list.add(f.getFriend());
//           }
//           return list;
//       }
//       return null;
//    }

    @PostMapping(path="/register")
    public @ResponseBody String addNewUserBody(@RequestBody User user)
    {
       return userService.registerUser(user);
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

    @PutMapping(path = "/updateUserName/{id}")
    public @ResponseBody User updateUserName(@PathVariable("id") Long id, @RequestParam String userName)
    {
        return userService.updateUserUserName(id, userName);
    }

    // TODO - Figure out if this needs to be a string param or body entity.
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
