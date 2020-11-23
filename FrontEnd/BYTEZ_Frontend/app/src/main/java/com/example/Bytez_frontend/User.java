package com.example.Bytez_frontend;

import android.os.Parcel;
import android.os.Parcelable;

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

    // Actual constructor
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
