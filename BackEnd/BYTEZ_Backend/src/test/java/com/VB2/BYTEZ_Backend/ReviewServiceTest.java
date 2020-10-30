package com.VB2.BYTEZ_Backend;

import com.VB2.BYTEZ_Backend.Domain.Restaurant;
import com.VB2.BYTEZ_Backend.Domain.Review;
import com.VB2.BYTEZ_Backend.Repositories.RestaurantRepository;
import com.VB2.BYTEZ_Backend.Repositories.ReviewRepository;
import com.VB2.BYTEZ_Backend.Repositories.UserRepository;
import com.VB2.BYTEZ_Backend.Service.ReviewService;
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
public class ReviewServiceTest {

    @TestConfiguration
    static class ReviewTestConfig{
        @Bean
        public ReviewService getRService(){
            return new ReviewService();
        }

        @Bean
        public ReviewRepository getRevRepo(){
            return mock(ReviewRepository.class);
        }

        @Bean
        public RestaurantRepository getRestRepo(){
            return mock(RestaurantRepository.class);
        }

        @Bean
        public UserRepository getURepo(){
            return mock(UserRepository.class);
        }
    }

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testReviewService(){
        List<Review> revList = new ArrayList<>();
        List<Restaurant> restList = new ArrayList<>();

        when(reviewRepository.findAll()).thenReturn(revList);

        when(reviewRepository.save(any(Review.class)))
                .thenAnswer(x -> {
                    Review r = x.getArgument(0);
                    revList.add(r);
                    return null;
                });

        assertEquals(revList, reviewService.getAllReviews());
        assertEquals(restList, reviewService.getReviewRestaurant(1L));
    }
}
