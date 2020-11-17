package com.VB2.BYTEZ_Backend.Service;

import com.VB2.BYTEZ_Backend.Domain.Restaurant;
import com.VB2.BYTEZ_Backend.Domain.Review;
import com.VB2.BYTEZ_Backend.Domain.User;
import com.VB2.BYTEZ_Backend.Repositories.RestaurantRepository;
import com.VB2.BYTEZ_Backend.Repositories.ReviewRepository;
import com.VB2.BYTEZ_Backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Review> getAllReviews()
    {
        return reviewRepository.findAll();
    }

    public Review getReview(Long id)
    {
        return reviewRepository.findById(id).isPresent() ?
                reviewRepository.findById(id).get(): null;
    }

    public List<Review> getReviewsForRestaurant(Long id)
    {
        return reviewRepository.findByRestaurantId(id);
    }

    public User getReviewAuthor(Long id)
    {
        return reviewRepository.findById(id).isPresent() ?
                reviewRepository.findById(id).get().getAuthor() : null;
    }

    public Restaurant getReviewRestaurant(Long id)
    {
        return reviewRepository.findById(id).isPresent() ?
                reviewRepository.findById(id).get().getRestaurant() : null;
    }

    public Review createReview(Long userId, Long restaurantId, Review review)
    {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        return userRepository.findById(userId)
                .map(user -> {
                    review.setAuthor(user);
                    review.setRestaurant(restaurant);
                    review.setAuthorName(user.getUserName());
                    review.setRestaurantName(restaurant.getRestaurantName());
                    return reviewRepository.save(review);
                })
                .orElse(null);
    }

    public Review updateReview(Long id, Review newReview)
    {
        return reviewRepository.findById(id)
                .map(review -> {
                    review.setServiceScore(newReview.getServiceScore());
                    review.setCleanlinessScore(newReview.getCleanlinessScore());
                    review.setFoodQualityScore(newReview.getFoodQualityScore());
                    review.setOverallScore(newReview.getOverallScore());
                   // review.setRestaurant(newReview.getRestaurant());
                    return reviewRepository.save(review);
                })
                .orElse(null);
    }

    public String deleteReview(Long id)
    {
        reviewRepository.deleteById(id);
        return "{\"status\":\"Success\"}";
    }
}
