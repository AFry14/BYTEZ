package com.VB2.BYTEZ_Backend.Controllers;

import com.VB2.BYTEZ_Backend.Domain.Review;
import com.VB2.BYTEZ_Backend.Repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping(path = "/review")
public class ReviewController {
 @Autowired
 private ReviewRepository reviewRepository;

 @GetMapping(path = "/")
 public @ResponseBody Iterable<Review> getAllReviews()
 {
  return reviewRepository.findAll();
 }

 @GetMapping(path = "/{id}")
 public @ResponseBody Optional<Review> getReviewById(@PathVariable("id") Long id)
 {
  return reviewRepository.findById(id);
 }

 @PostMapping(path = "/add")
 public @ResponseBody String addReview(@RequestBody Review review)
 {
  reviewRepository.save(review);
  return "{\"status\":\"Success\"}";
 }
}
