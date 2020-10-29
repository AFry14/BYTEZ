package com.VB2.BYTEZ_Backend;

import com.VB2.BYTEZ_Backend.Controllers.FriendshipController;
import com.VB2.BYTEZ_Backend.Domain.Friendship;
import com.VB2.BYTEZ_Backend.Domain.User;
import com.VB2.BYTEZ_Backend.Repositories.FriendshipRepository;
import com.VB2.BYTEZ_Backend.Repositories.UserRepository;
import com.VB2.BYTEZ_Backend.Service.FriendshipService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class FriendshipServiceTest {

    @TestConfiguration
    static class FriendshipTestConfiguration {

        @Bean
        public FriendshipService fService() {
            return new FriendshipService();
        }

        @Bean
        FriendshipRepository getFRepo(){
            return mock(FriendshipRepository.class);
        }

        @Bean
        UserRepository getURepo(){
            return mock(UserRepository.class);
        }
    }

    @Autowired
    private FriendshipService fService;

    @Autowired
    private FriendshipRepository fRepo;

    @Autowired
    private UserRepository uRepo;


    @Test
    public void testFriendshipService(){
        List<Friendship> fList = new ArrayList<>();

        when(fRepo.findAll()).thenReturn(fList);

        when(fRepo.save(any(Friendship.class)))
                .thenAnswer(x -> {
                    Friendship f = x.getArgument(0);
                    fList.add(f);
                    return null;
                });



        assertEquals(fList, fService.getAllFriendships());

    }

}
