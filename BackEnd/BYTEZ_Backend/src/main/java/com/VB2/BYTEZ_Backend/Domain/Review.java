package com.VB2.BYTEZ_Backend.Domain;

import javax.persistence.*;

// Review Entity
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User author;
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
    private Double overallScore;
    private Double foodQualityScore;
    private Double serviceScore;
    private Double cleanlinessScore;
    private Double overallValueScore;

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
    
}
