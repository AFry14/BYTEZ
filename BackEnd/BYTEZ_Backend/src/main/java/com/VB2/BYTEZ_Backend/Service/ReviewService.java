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
import java.util.Set;

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

    public String likeReview(Long userId, Long reviewId)
    {
        Set<Review> likedReviews = userRepository.findById(userId).get().getLikedReviews();
        Set<User> likes = reviewRepository.findById(reviewId).get().getLikes();
        likedReviews.add(reviewRepository.findById(reviewId).get());
        likes.add(userRepository.findById(userId).get());
        userRepository.findById(reviewId).get().setLikedReviews(likedReviews);
        reviewRepository.findById(userId).get().setLikes(likes);
        return "{\"status\":\"Success\"}";
    }

    public String helpfulReview(Long userId, Long reviewId)
    {
        Set<Review> helpfulReviews = userRepository.findById(userId).get().getHelpfulReviews();
        Set<User> helpfuls = reviewRepository.findById(reviewId).get().getHelpfuls();
        helpfulReviews.add(reviewRepository.findById(reviewId).get());
        helpfuls.add(userRepository.findById(userId).get());
        userRepository.findById(reviewId).get().setLikedReviews(helpfulReviews);
        reviewRepository.findById(userId).get().setLikes(helpfuls);
        return "{\"status\":\"Success\"}";
    }

    public String dislikeReview(Long userId, Long reviewId)
    {
        Set<Review> dislikedReviews = userRepository.findById(userId).get().getDislikedReviews();
        Set<User> dislikes = reviewRepository.findById(reviewId).get().getDislikes();
        dislikedReviews.add(reviewRepository.findById(reviewId).get());
        dislikes.add(userRepository.findById(userId).get());
        userRepository.findById(reviewId).get().setLikedReviews(dislikedReviews);
        reviewRepository.findById(userId).get().setLikes(dislikes);
        return "{\"status\":\"Success\"}";
    }
}
