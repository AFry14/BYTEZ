package com.VB2.BYTEZ_Backend;

import com.VB2.BYTEZ_Backend.Controllers.ReviewController;
import com.VB2.BYTEZ_Backend.Domain.Review;
import com.VB2.BYTEZ_Backend.Repositories.ReviewRepository;
import com.VB2.BYTEZ_Backend.Service.ReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc controller;

    @MockBean
    private ReviewService rService;

    @MockBean
    private ReviewRepository repo;

    @Test
    public void testReviewController() throws Exception{
        List<Review> rList = new ArrayList<>();

        when(repo.findAll()).thenReturn(rList);

        when(repo.save(any(Review.class)))
                .thenAnswer(x -> {
                    Review r = x.getArgument(0);
                    rList.add(r);
                    return null;
                });

        controller.perform(get("/review/")
                .contentType("application/json"))
                .andExpect(status().isOk());

        controller.perform(post("/review/add/{userId}/{restaurantId}", 1L, 2L)
                .contentType("application/json")
                .content("{\"overallScore\":5 , \"foodQualityScore\":5, " +
                        "\"serviceScore\":5, \"cleanlinessScore\":5, \"overallValueScore\":5}"))
                .andExpect(status().isOk());
    }
}
