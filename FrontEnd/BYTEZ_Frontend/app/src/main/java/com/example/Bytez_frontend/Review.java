package com.example.Bytez_frontend;

import java.util.ArrayList;

public class Review
{
    private int id;
    private Restaurant rest;
    private User reviewer;
    String userName;
    String restName;
    private int reviewerId;
    private float foodQR;
    private float serviceR;
    private float cleanlinessR;
    private float overallR;
    private String comments;
    private ArrayList<Integer> agrees;
    private ArrayList<Integer> disagrees;
    private ArrayList<Integer> helpfuls;

    public Review(int id, Restaurant place, User reviewer)
    {
        this.id = id;
        rest = place;
        this.reviewer = reviewer;
        userName = reviewer.getUsername();
        restName = place.getName();
    }

    public Review(int id, float overallR, String userName, String restName)
    {
        this.id = id;
        this.overallR = overallR;
        this.userName = userName;
        this.restName = restName;
    }

    public Review(int id, float rating, Restaurant place, User reviewer)
    {
        this.id = id;
        overallR = rating;
        rest = place;
        this.reviewer = reviewer;
        userName = reviewer.getUsername();
        restName = place.getName();
    }

    public Review(float rating, Restaurant place, User reviewer)
    {
        this.id = id;
        overallR = rating;
        rest = place;
        this.reviewer = reviewer;
        userName = reviewer.getUsername();
        restName = place.getName();
    }

    public Review(int id, float rating, String userName, String rest, float foodQR, float cleanlinessR, float serviceR, String comments)
    {
        this.id = id;
        overallR = rating;
        this.userName = userName;
        restName = rest;
        this.foodQR = foodQR;
        this.cleanlinessR = cleanlinessR;
        this.serviceR = serviceR;
        this.comments = comments;
    }

    public Review(int id, float rating, String userName, String rest, float foodQR, float cleanlinessR, float serviceR, String comments, ArrayList<Integer> agrees, ArrayList<Integer> disagrees, ArrayList<Integer> helpfuls)
    {
        this.id = id;
        overallR = rating;
        this.userName = userName;
        restName = rest;
        this.foodQR = foodQR;
        this.cleanlinessR = cleanlinessR;
        this.serviceR = serviceR;
        this.comments = comments;
        this.agrees = agrees;
        this.disagrees = disagrees;
        this.helpfuls =  helpfuls;
    }

    public Review(int id, float rating, float foodQR, float cleanlinessR, float serviceR, String comments)
    {
        this.id = id;
        overallR = rating;
//        this.userName = userName;
//        restName = rest;
        this.foodQR = foodQR;
        this.cleanlinessR = cleanlinessR;
        this.serviceR = serviceR;
        this.comments = comments;
    }

    public Review(int id, Restaurant place, User reviewer, String comments)
    {
        this.id = id;
        rest = place;
        this.reviewer = reviewer;
        userName = reviewer.getUsername();
        restName = place.getName();
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
        overallR = overallS;
        return overallS;
    }

    public float getOverallR()
    {
        return overallR;
    }

//    public static float[] getWeights(int foodCrit, int serviceCrit, int cleanCrit)
//    {
//
//        return result;
//    }

    /**
     * determines the overallscore taking into account of the criteria values
     * @param foodScore
     * @param serviceScore
     * @param cleanScore
     * @param foodCrit
     * @param serviceCrit
     * @param cleanCrit
     * @return
     */
    public static float getFinalRating(float foodScore, float serviceScore, float cleanScore, int foodCrit, int serviceCrit, int cleanCrit)
    {
        //takes the average of the criterias and then sets the individul weights of each field by taking the crit valu/ crit average
        float critAvg = (float) (foodCrit + serviceCrit + cleanCrit)/3;
        float foodWeight = foodCrit/critAvg;
        float serviceWeight = serviceCrit/critAvg;
        float cleanWeight = cleanCrit/critAvg;
        float weights[] = new float[3];
        weights[0] = foodWeight;
        weights[1] = serviceWeight;
        weights[2] = cleanWeight;

        //multiply weights by raw score
        float foodAS = weights[0]*foodScore;
        float serviceAS = weights[1]*serviceScore;
        float cleanAS = weights[2]*cleanScore;
        float overallS = (foodAS + serviceAS + cleanAS)/3;
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

    public String getReviewerString() { return userName; }

    public String getRestString() { return restName; }

    public Restaurant getRest()
    {
        return rest;
    }

    public int getId() { return id; }

    public ArrayList<Integer> getHelpfuls() { return helpfuls; }

    public ArrayList<Integer> getAgrees() { return agrees; }

    public ArrayList<Integer> getDisagrees() { return disagrees; }

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
