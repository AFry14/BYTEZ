package com.example.Bytez_frontend;

public class Review
{
    Restaurant rest;
    User reviewer;
    double foodQR;
    double serviceR;
    double cleanlinessR;
    public Review(Restaurant place, User reviewer)
    {
        rest = place;
        this.reviewer = reviewer;
    }

    public double getFinalReview()
    {
        double overallS = (getFoodQR() + getCleanlinessR() + getServiceR())/3;
        return overallS;
    }

    public double getFoodQR()
    {
        return foodQR;
    }

    public double getServiceR()
    {
        return serviceR;
    }

    public double getCleanlinessR()
    {
        return cleanlinessR;
    }

    public User getReviewer()
    {
        return reviewer;
    }

    public Restaurant getRest()
    {
        return rest;
    }

    public void setFoodQR(int score)
    {
        foodQR = score;
    }

    public void setServiceR(int score)
    {
        serviceR = score;
    }

    public void setCleanlinessR(int score)
    {
        cleanlinessR = score;
    }

}
