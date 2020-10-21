package com.VB2.BYTEZ_Backend.Service;

import com.VB2.BYTEZ_Backend.Domain.Restaurant;
import com.VB2.BYTEZ_Backend.Domain.Review;
import com.VB2.BYTEZ_Backend.Repositories.RestaurantRepository;
import com.VB2.BYTEZ_Backend.Repositories.ReviewRepository;
import com.VB2.BYTEZ_Backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Restaurant> getAllRestaurants()
    {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurant(Long id)
    {
        return restaurantRepository.findById(id).isPresent() ?
                restaurantRepository.findById(id).get() : null;
    }

    public List<Review> getReviewsForRestaurant(Long id)
    {
        return reviewRepository.findByRestaurantId(id);
    }

    public Restaurant createRestaurant(Long ownerId, Restaurant restaurant)
    {
        return userRepository.findById(ownerId)
                .map(user -> {
                    restaurant.setOwner(user);
                    return restaurantRepository.save(restaurant);
                })
                .orElse(null);
    }

    public String deleteRestaurant(Long id)
    {
        restaurantRepository.deleteById(id);
        return "{\"Status\":\"Success\"}";
    }

}
