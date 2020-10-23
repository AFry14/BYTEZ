package com.VB2.BYTEZ_Backend.Controllers;

import com.VB2.BYTEZ_Backend.Domain.Friendship;
import com.VB2.BYTEZ_Backend.Repositories.FriendshipRepository;
import com.VB2.BYTEZ_Backend.Service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/friendship")
public class FriendshipController {
    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private FriendshipService friendshipService;

    @GetMapping("/")
    public @ResponseBody List<Friendship> getAllFriendships()
    {
        return friendshipService.getAllFriendships();
    }

    @PostMapping("/friendRequest/{selfId}/{friendId}")
    public @ResponseBody Friendship requestFriend(@PathVariable("selfId")Long selfId, @PathVariable("friendId")Long friendId)
    {
        return friendshipService.requestFriend(selfId, friendId);
    }
}
