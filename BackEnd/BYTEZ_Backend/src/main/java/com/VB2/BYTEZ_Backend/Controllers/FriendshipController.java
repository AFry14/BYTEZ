package com.VB2.BYTEZ_Backend.Controllers;

import com.VB2.BYTEZ_Backend.Repositories.FriendshipRepository;
import com.VB2.BYTEZ_Backend.Service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/friendship")
public class FriendshipController {
    @Autowired
    private FriendshipRepository friendshipRepository;

//    @PostMapping(path = "/addFriend/{id}")
//    public @ResponseBody
//    String addFriend(@PathVariable("id") Long id, @RequestParam Long friendId)
//    {
//        friendshipService.saveFriendship(id, friendId);
//
//        return "{\"status\":\"Success\"}";
//    }
}
