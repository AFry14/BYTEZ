package com.example.Bytez_frontend;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable
{
    private int id;
    private String username;
    private String email;
    private String password;
    private String favFood;
    private String favDrink;
    private String favRestaurant;
    private String fName;
    private String lName;
    private String userType;
    private int critFood = 1;
    private int critService = 1;
    private int critClean = 1;
    ArrayList<Integer> agrees;
    ArrayList<Integer> disagrees;
    ArrayList<Integer> helpfuls;

    public User(int id, String username, String email)
    {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public User(int id, String username, String email, String favFood, String favDrink, String favRestaurant, String fName, String lName)
    {
        this.id = id;
        this.username = username;
        this.email = email;
        this.favFood = favFood;
        this.favDrink = favDrink;
        this.favRestaurant = favRestaurant;
        this.fName = fName;
        this.lName = lName;
    }

    public User(int id, String username, String email, String password, String favFood, String favDrink, String favRestaurant, String fName, String lName,
                String userType)
    {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.favFood = favFood;
        this.favDrink = favDrink;
        this.favRestaurant = favRestaurant;
        this.fName = fName;
        this.lName = lName;
        this.userType = userType;
        this.critFood = 1;
        this.critService = 1;
        this.critClean = 1;
    }

    // Actual constructor
    public User(int id, String username, String email, String password, String favFood, String favDrink, String favRestaurant, String fName, String lName,
                String userType, int critFood, int critService, int critClean)
    {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.favFood = favFood;
        this.favDrink = favDrink;
        this.favRestaurant = favRestaurant;
        this.fName = fName;
        this.lName = lName;
        this.userType = userType;
        this.critFood = critFood;
        this.critService = critService;
        this.critClean = critClean;
    }

    public User(int id, String username, String email, String password, String favFood, String favDrink, String favRestaurant, String fName, String lName,
                String userType, int critFood, int critService, int critClean, ArrayList<Integer> helpfuls, ArrayList<Integer> agrees, ArrayList<Integer> disagrees)
    {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.favFood = favFood;
        this.favDrink = favDrink;
        this.favRestaurant = favRestaurant;
        this.fName = fName;
        this.lName = lName;
        this.userType = userType;
        this.critFood = critFood;
        this.critService = critService;
        this.critClean = critClean;
        this.helpfuls = helpfuls;
        this.agrees = agrees;
        this.disagrees = disagrees;
    }

    // Parcelable constructors
    protected User(Parcel in) {
        id = in.readInt();
        username = in.readString();
        email = in.readString();

        // Same order
        password = in.readString();
        favFood = in.readString();
        favDrink = in.readString();
        favRestaurant = in.readString();
        fName = in.readString();
        lName = in.readString();

        userType = in.readString();




    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    // Getter methods
    public int getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword() { return password; }

    public String getFavFood() { return favFood; }

    public String getFavDrink() { return favDrink; }

    public String getFavRestaurant() { return favRestaurant; }

    public String getfName() { return fName; }

    public String getlName() { return lName; }

    public String getUserType() { return userType; }

    public int getCritFood() { return critFood; }

    public int getCritService() { return critService; }

    public int getCritClean() { return critClean; }

    public ArrayList<Integer> getHelpfuls() { return helpfuls; }

    public ArrayList<Integer> getAgrees() { return agrees; }

    public ArrayList<Integer> getDisagrees() { return disagrees; }

    // Setter methods
    public void setUsername(String userName) {
        username = userName;
    }

    public void setEmail(String Email) {
        email = Email;
    }

    public void setPassword(String Password) {
        password = Password;
    }

    public void setFavFood(String FavFood) {
        favFood = FavFood;
    }

    public void setFavDrink(String FavDrink) {
        favDrink = FavDrink;
    }

    public void setFavRestaurant(String FavRest) {
        favRestaurant = FavRest;
    }

    public void setFirstName(String firstName) {
        fName = firstName;
    }

    public void setLastName(String lastName) {
        lName = lastName;
    }

    public void setUserType(String usertype) {
        userType = usertype;
    }

    public void setCritFood(int score)
    {
        critFood = score;
    }

    public void setCritService(int score)
    {
        critService = score;
    }

    public void setCritClean(int score)
    {
        critClean = score;
    }


    // Parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // Must be in same order from above parcel constructor
        parcel.writeInt(id);
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(favFood);
        parcel.writeString(favDrink);
        parcel.writeString(favRestaurant);
        parcel.writeString(fName);
        parcel.writeString(lName);
        parcel.writeString(userType);
    }
}
