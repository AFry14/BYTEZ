package com.example.Bytez_frontend;

public class Review
{
    private int id;
    private Restaurant rest;
    private User reviewer;
    private int reviewerId;
    private float foodQR;
    private float serviceR;
    private float cleanlinessR;
    private String comments;

    public Review(int id, Restaurant place, User reviewer)
    {
        this.id = id;
        rest = place;
        this.reviewer = reviewer;
    }

    public Review(int id, Restaurant place, User reviewer, String comments)
    {
        this.id = id;
        rest = place;
        this.reviewer = reviewer;
        this.comments = comments;
    }

//    public Review(int id, Restaurant place, int reviewer, String comments)
//    {
//        this.id = id;
//        rest = place;
//        this.reviewerId = reviewer;
//        this.comments = comments;
//    }

    public float getFinalRating()
    {
        float overallS = (getFoodQR() + getCleanlinessR() + getServiceR())/3;
        return overallS;
    }

    public static float getFinalRating(float food, float service, float clean)
    {
        float overallS = (food + service + clean)/3;
        return overallS;
    }

    public float getFoodQR()
    {
        return foodQR;
    }

    public float getServiceR()
    {
        return serviceR;
    }

    public float getCleanlinessR()
    {
        return cleanlinessR;
    }

    public String getComments() { return comments; }

    public User getReviewer()
    {
        return reviewer;
    }

    public Restaurant getRest()
    {
        return rest;
    }

    public int getId() { return id; }

    public void setFoodQR(float score)
    {
        foodQR = score;
    }

    public void setServiceR(float score)
    {
        serviceR = score;
    }

    public void setCleanlinessR(float score)
    {
        cleanlinessR = score;
    }

    public void setComments(String comments) { this.comments = comments; }

}
