package com.VB2.BYTEZ_Backend.Controllers;

import com.VB2.BYTEZ_Backend.Repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) { this.userRepository = userRepository; }

    @RequestMapping("/Users")
    public String getUsers(Model model){

        model.addAttribute("Users", userRepository.findAll());

        return "UserList";
    }
}
