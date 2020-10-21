package com.VB2.BYTEZ_Backend.Controllers;

import com.VB2.BYTEZ_Backend.Domain.Restaurant;
import com.VB2.BYTEZ_Backend.Domain.Review;
import com.VB2.BYTEZ_Backend.Repositories.RestaurantRepository;
import com.VB2.BYTEZ_Backend.Service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping(path = "/")
    public @ResponseBody List<Restaurant> getAllRestaurants()
    {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Restaurant getRestaurantById(@PathVariable("id") Long id)
    {
        return restaurantService.getRestaurant(id);
    }

    @GetMapping(path = "/reviews/{restaurantId}")
    public @ResponseBody List<Review> getReviewsForRestaurant(@PathVariable("restaurantId") Long id)
    {
        return restaurantService.getReviewsForRestaurant(id);
    }

    @PostMapping(path = "/add/{userId}")
    public @ResponseBody Restaurant addRestaurant(@PathVariable("userId") Long id, @RequestBody Restaurant restaurant)
    {
        return restaurantService.createRestaurant(id, restaurant);
    }

    // Currently only replaces the restaurantName
    @PutMapping(path = "/updateRestaurant/{id}")
    public @ResponseBody Restaurant updateRestaurant(@PathVariable("id") Long id, @RequestBody Restaurant newRestaurant)
    {
        return restaurantRepository.findById(id)
                .map(restaurant -> {
                    restaurant.setRestaurantName(newRestaurant.getRestaurantName());
                    return restaurantRepository.save(restaurant);
                })
                .orElse(null);
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody String deleteRestaurantById(@PathVariable("id") Long id)
    {
        return restaurantService.deleteRestaurant(id);
    }
}
