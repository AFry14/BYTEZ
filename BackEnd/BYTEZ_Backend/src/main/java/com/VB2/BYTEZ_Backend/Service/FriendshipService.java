package com.VB2.BYTEZ_Backend.Service;

import com.VB2.BYTEZ_Backend.Domain.Friendship;
import com.VB2.BYTEZ_Backend.Repositories.FriendshipRepository;
import com.VB2.BYTEZ_Backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class FriendshipService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    public void saveFriendship(Long self, Long friend)
    {
        Friendship f = new Friendship();
        f.setDate(new Date());
        f.setSelf(userRepository.findById(self).get());
        f.setFriend(userRepository.findById(friend).get());
        friendshipRepository.save(f);
    }
}
