package com.VB2.BYTEZ_Backend.Service;

import com.VB2.BYTEZ_Backend.Domain.Friendship;
import com.VB2.BYTEZ_Backend.Domain.User;
import com.VB2.BYTEZ_Backend.Repositories.FriendshipRepository;
import com.VB2.BYTEZ_Backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FriendshipService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    public List<Friendship> getAllFriendships()
    {
        return friendshipRepository.findAll();
    }

    public Friendship getFriendshipById(Long id)
    {
        return friendshipRepository.findById(id).isPresent() ?
                friendshipRepository.findById(id).get() : null;
    }

    public Friendship requestFriend(Long selfId, Long friendId)
    {
        User friend = userRepository.findById(friendId).get();
        return userRepository.findById(selfId)
                .map(user -> {
                    Friendship f = new Friendship();
                   f.setSelf(user);
                   f.setFriend(friend);
                   f.setDate(new Date());
                   return friendshipRepository.save(f);
                })
                .orElse(null);
    }

    public String removeFriend(Long self_id, Long friend_id)
    {
        friendshipRepository.deleteBySelfIdAndFriendId(self_id, friend_id);
        friendshipRepository.deleteBySelfIdAndFriendId(friend_id, self_id);

        return "{\"Status\":\"Success\"}";
    }

    public void saveFriendship(Long self, Long friend)
    {
        Friendship f = new Friendship();
        f.setDate(new Date());
        f.setSelf(userRepository.findById(self).get());
        f.setFriend(userRepository.findById(friend).get());
        friendshipRepository.save(f);
    }
}
