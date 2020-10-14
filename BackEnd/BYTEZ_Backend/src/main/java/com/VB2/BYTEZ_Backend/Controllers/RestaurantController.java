package com.VB2.BYTEZ_Backend.Controllers;

import com.VB2.BYTEZ_Backend.Domain.Restaurant;
import com.VB2.BYTEZ_Backend.Repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping(path = "/")
    public @ResponseBody Iterable<Restaurant> getAllRestaurants()
    {
        return restaurantRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Optional<Restaurant> getRestaurantById(@PathVariable("id") Long id)
    {
        return restaurantRepository.findById(id);
    }

    @PostMapping(path = "/add")
    public @ResponseBody String addRestaurant(@RequestBody Restaurant restaurant)
    {
        restaurantRepository.save(restaurant);
        return "{\"status\":\"Success\"}";
    }
}
