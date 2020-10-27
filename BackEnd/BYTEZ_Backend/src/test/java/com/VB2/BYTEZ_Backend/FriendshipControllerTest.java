package com.VB2.BYTEZ_Backend;


import com.VB2.BYTEZ_Backend.Controllers.FriendshipController;
import com.VB2.BYTEZ_Backend.Domain.Friendship;
import com.VB2.BYTEZ_Backend.Domain.User;
import com.VB2.BYTEZ_Backend.Repositories.FriendshipRepository;
import com.VB2.BYTEZ_Backend.Service.FriendshipService;
import org.junit.Test;
import org.junit.runner.RunWith;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@WebMvcTest(FriendshipController.class)
public class FriendshipControllerTest {

    @Autowired
    private MockMvc controller;

    @MockBean
    private FriendshipService fService;

    @MockBean
    private FriendshipRepository repo;

    @Test
    public void testFriendshipController() throws Exception{
        List<Friendship> friendshipList = new ArrayList<>();

        when(repo.findAll()).thenReturn(friendshipList);

        when(repo.save(any(Friendship.class)))
                .thenAnswer(x -> {
                    Friendship f = x.getArgument(0);
                    friendshipList.add(f);
                    return null;
                });

        User self = new User();
        User friend = new User();
        self.setId(1L);
        friend.setId(2L);
        when(fService.requestFriend(self.getId(), friend.getId())).thenAnswer(x -> {
            Friendship f = new Friendship();
            f.setSelf(self);
            f.setFriend(friend);
            f.setDate(new Date());
            return repo.save(f);
        });

        controller.perform(post("/friendship/friendRequest/{selfId}/{friendId}", 1L, 2L)
                .contentType("application/json"))
                .andExpect(status().isOk());

        controller.perform(get("/friendship/")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
