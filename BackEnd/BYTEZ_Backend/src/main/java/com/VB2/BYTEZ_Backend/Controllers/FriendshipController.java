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

    @GetMapping(path = "/")
    public @ResponseBody List<Friendship> getAllFriendships()
    {
        return friendshipService.getAllFriendships();
    }

    @GetMapping(path = "/{friendshipId}")
    public @ResponseBody Friendship getFriendshipById(@PathVariable("friendshipId") Long id)
    {
        return friendshipService.getFriendshipById(id);
    }

    @PostMapping("/friendRequest/{selfId}/{friendId}")
    public @ResponseBody Friendship requestFriend(@PathVariable("selfId")Long selfId, @PathVariable("friendId")Long friendId)
    {
        return friendshipService.requestFriend(selfId, friendId);
    }

    @DeleteMapping(path = "/removeFriend/{selfId}/{friendId}")
    public @ResponseBody String removeFriend(@PathVariable("selfId")Long self_id, @PathVariable("friendId")Long friend_id)
    {
        return friendshipService.removeFriend(self_id, friend_id);
    }
}
