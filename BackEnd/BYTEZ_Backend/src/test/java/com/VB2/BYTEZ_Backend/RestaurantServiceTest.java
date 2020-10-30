package com.VB2.BYTEZ_Backend;

import com.VB2.BYTEZ_Backend.Domain.Restaurant;
import com.VB2.BYTEZ_Backend.Domain.Review;
import com.VB2.BYTEZ_Backend.Repositories.RestaurantRepository;
import com.VB2.BYTEZ_Backend.Repositories.ReviewRepository;
import com.VB2.BYTEZ_Backend.Repositories.UserRepository;
import com.VB2.BYTEZ_Backend.Service.RestaurantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RestaurantServiceTest {

    @TestConfiguration
    static class RestaurantTestConfiguration{
        @Bean
        public RestaurantService getRService(){
            return new RestaurantService();
        }

        @Bean
        public RestaurantRepository getRestRepo(){
            return mock(RestaurantRepository.class);
        }

        @Bean
        public ReviewRepository getRevRepo(){
            return mock(ReviewRepository.class);
        }

        @Bean
        public UserRepository getURepo(){
            return mock(UserRepository.class);
        }
    }

    @Autowired
    private RestaurantService rService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testRestaurantService(){
        List<Restaurant> restList = new ArrayList<>();
        List<Review> revList = new ArrayList<>();

        when(restaurantRepository.findAll()).thenReturn(restList);

        when(restaurantRepository.save(any(Restaurant.class)))
                .thenAnswer(x -> {
                   Restaurant r = x.getArgument(0);
                   restList.add(r);
                   return null;
                });

        assertEquals(restList, rService.getAllRestaurants());
        assertEquals(revList, rService.getReviewsForRestaurant(1L));
    }
}
