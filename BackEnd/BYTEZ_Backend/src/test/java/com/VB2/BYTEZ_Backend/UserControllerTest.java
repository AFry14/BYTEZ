package com.VB2.BYTEZ_Backend;

import com.VB2.BYTEZ_Backend.Controllers.UserController;
import com.VB2.BYTEZ_Backend.Domain.User;
import com.VB2.BYTEZ_Backend.Repositories.UserRepository;
import com.VB2.BYTEZ_Backend.Service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc controller;

    @MockBean
    private UserService uService;

    @MockBean
    private UserRepository repo;

    @Test
    public void testUserController() throws Exception{
        List<User> uList = new ArrayList<>();

        when(repo.findAll()).thenReturn(uList);

        when(repo.save(any(User.class)))
                .thenAnswer(x -> {
                    User u = x.getArgument(0);
                    uList.add(u);
                    return null;
        });

        controller.perform(get("/user/{id}", 1L)
                    .contentType("application/json"))
                    .andExpect(status().isOk());

        controller.perform(post("/user/register")
                    .contentType("application/json")
                    .content("{\"firstName\":\"Alex\", \"lastName\":\"Freiberg\", " +
                            "\"userName\":\"AFry14\", \"password\":\"pass\", \"email\":\"email@email.com\"," +
                            "\"userType\":\"ADMIN\", \"favoriteFood\":\"pizza\", \"favoriteRestaurant\": \"Jethro's\"" +
                            ",\"favoriteDrink\":\"beer\"}"))
                    .andExpect(status().isOk());
    }
}
