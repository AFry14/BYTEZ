package com.VB2.BYTEZ_Backend;

import com.VB2.BYTEZ_Backend.Domain.User;
import com.VB2.BYTEZ_Backend.Repositories.RestaurantRepository;
import com.VB2.BYTEZ_Backend.Repositories.ReviewRepository;
import com.VB2.BYTEZ_Backend.Repositories.UserRepository;
import com.VB2.BYTEZ_Backend.Service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @TestConfiguration
    static class userTestConfig{
        @Bean
        public UserService getUserService(){
            return new UserService();
        }

        @Bean
        public UserRepository getURepo(){
            return mock(UserRepository.class);
        }

        @Bean
        public ReviewRepository getRevRepo(){
            return mock(ReviewRepository.class);
        }

        @Bean
        public RestaurantRepository getRestRepo(){
            return mock(RestaurantRepository.class);
        }
    }

    @Autowired
    private UserService uService;

    @Autowired
    private UserRepository uRepo;

    @Autowired
    private ReviewRepository revRepo;

    @Autowired
    private RestaurantRepository restRepo;

    @Test
    public void testUserService(){
        List<User> uList = new ArrayList<>();

        when(uRepo.findAll()).thenReturn(uList);

        when(uRepo.save(any(User.class)))
                .thenAnswer(x -> {
                    User u = x.getArgument(0);
                    uList.add(u);
                    return null;
                });

        assertEquals(uList, uService.getAllUsers());
    }
}
