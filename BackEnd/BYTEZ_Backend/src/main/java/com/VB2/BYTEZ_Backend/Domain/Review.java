package com.VB2.BYTEZ_Backend.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

// Review Entity
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonIgnore
    private User author;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    private Double overallScore;
    private Double foodQualityScore;
    private Double serviceScore;
    private Double cleanlinessScore;
    private Double overallValueScore;
    private String comment;
    private String restaurantName;
    private String authorName;

    @ManyToMany(mappedBy = "likedReviews")
    @JsonIgnore
    private Set<User> likes;

    @ManyToMany(mappedBy = "helpfulReviews")
    @JsonIgnore
    private Set<User> helpfuls;

    @ManyToMany(mappedBy = "dislikedReviews")
    @JsonIgnore
    private Set<User> dislikes;

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public Set<User> getHelpfuls() {
        return helpfuls;
    }

    public void setHelpfuls(Set<User> helpfuls) {
        this.helpfuls = helpfuls;
    }

    public Set<User> getDislikes() {
        return dislikes;
    }

    public void setDislikes(Set<User> dislikes) {
        this.dislikes = dislikes;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Double getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Double overallScore) {
        this.overallScore = overallScore;
    }

    public Double getFoodQualityScore() {
        return foodQualityScore;
    }

    public void setFoodQualityScore(Double foodQualityScore) {
        this.foodQualityScore = foodQualityScore;
    }

    public Double getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(Double serviceScore) {
        this.serviceScore = serviceScore;
    }

    public Double getCleanlinessScore() {
        return cleanlinessScore;
    }

    public void setCleanlinessScore(Double cleanlinessScore) {
        this.cleanlinessScore = cleanlinessScore;
    }

    public Double getOverallValueScore() {
        return overallValueScore;
    }

    public void setOverallValueScore(Double overallValueScore) {
        this.overallValueScore = (cleanlinessScore+overallValueScore+foodQualityScore+serviceScore)/4;
    }

    public User getAuthor()
    {
        return  author;
    }

    public void setAuthor(User author)
    {
     this.author = author;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }
}
