package com.VB2.BYTEZ_Backend;

import com.VB2.BYTEZ_Backend.Controllers.RestaurantController;
import com.VB2.BYTEZ_Backend.Domain.Restaurant;
import com.VB2.BYTEZ_Backend.Repositories.RestaurantRepository;
import com.VB2.BYTEZ_Backend.Service.RestaurantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc controller;

    @MockBean
    private RestaurantService rService;

    @MockBean
    private RestaurantRepository repo;

    @Test
    public void testRestaurantController() throws Exception{
        List<Restaurant> restaurantList = new ArrayList<>();

        when(repo.findAll()).thenReturn(restaurantList);

        when(repo.save(any(Restaurant.class)))
                .thenAnswer(x -> {
                    Restaurant r = x.getArgument(0);
                    restaurantList.add(r);
                    return null;
                });
        
        Long id = 1L;
        when(rService.getRestaurant(id)).thenReturn(new Restaurant());

        controller.perform(get("/restaurant/")
                .contentType("application/json"))
                .andExpect(status().isOk());

        controller.perform(get("/restaurant/1")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
