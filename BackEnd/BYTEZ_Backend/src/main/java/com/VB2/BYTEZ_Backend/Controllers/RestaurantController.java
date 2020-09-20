package com.VB2.BYTEZ_Backend.Controllers;

import com.VB2.BYTEZ_Backend.Domain.Restaurant;
import com.VB2.BYTEZ_Backend.Repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/restaurant")
public class RestaurantController {
    @Autowired

    private RestaurantRepository restaurantRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addRestaurant(@RequestBody Restaurant restaurant)
    {
        restaurantRepository.save(restaurant);
        return "Success!";
    }

    @GetMapping(path = "/")
    public @ResponseBody Iterable<Restaurant> getAllRestaurants()
    {
        return restaurantRepository.findAll();
    }
}
