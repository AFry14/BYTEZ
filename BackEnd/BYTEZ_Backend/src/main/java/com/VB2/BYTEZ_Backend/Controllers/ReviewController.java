package com.VB2.BYTEZ_Backend.Controllers;

import com.VB2.BYTEZ_Backend.Domain.Restaurant;
import com.VB2.BYTEZ_Backend.Domain.Review;
import com.VB2.BYTEZ_Backend.Domain.User;
import com.VB2.BYTEZ_Backend.Repositories.ReviewRepository;
import com.VB2.BYTEZ_Backend.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/review")
public class ReviewController
{

  @Autowired
  private ReviewService reviewService;

  @GetMapping(path = "/")
  public @ResponseBody
  List<Review> getAllReviews()
  {
    return reviewService.getAllReviews();
  }

  @GetMapping(path = "/{id}")
  public @ResponseBody
  Review getReviewById(@PathVariable("id") Long id)
  {
    return reviewService.getReview(id);
  }

  @GetMapping(path = "/author/{reviewId}")
  public @ResponseBody
  User getReviewAuthor(@PathVariable("reviewId") Long id)
  {
    return reviewService.getReviewAuthor(id);
  }

  @GetMapping(path = "/restaurant/{reviewId}")
  public @ResponseBody
  Restaurant getReviewRestaurant(@PathVariable("reviewId") Long id)
  {
    return reviewService.getReviewRestaurant(id);
  }

  @GetMapping(path = "/restaurantReviews/{restaurantId}")
  public @ResponseBody List<Review> getReviewsForRestaurant(@PathVariable("restaurantId") Long id)
  {
    return reviewService.getReviewsForRestaurant(id);
  }

  @PostMapping(path = "/add/{userId}/{restaurantId}")
  public @ResponseBody
  Review addReview(@PathVariable("userId")Long userId, @PathVariable("restaurantId") Long restaurantId, @RequestBody Review review)
  {
    return reviewService.createReview(userId, restaurantId, review);
  }

  // Currently only replaces the overallScore
  @PutMapping(path = "/updateReview/{id}")
  public @ResponseBody
  Review updateReview(@PathVariable("id") Long id, @RequestBody Review newReview)
  {
    return reviewService.updateReview(id, newReview);
  }

  @DeleteMapping(path = "/delete/{id}")
  public @ResponseBody
  String deleteReview(@PathVariable("id") Long id)
  {
    return reviewService.deleteReview(id);
  }

  @PostMapping(path = "/like/{userId}/{reviewId}")
  public @ResponseBody
  String likeReview(@PathVariable("userId") Long userId, @PathVariable("reviewId") Long reviewId)
  {
    return reviewService.
  }
}
