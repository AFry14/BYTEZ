package com.VB2.BYTEZ_Backend.Domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// User entity will create a table in the database with name user
@Entity
public class User {
    // Variables can be thought of as columns in the table
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String userType;

    private String favoriteFood;

    private String favoriteRestaurant;

    private String favoriteDrink;

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "user_user", joinColumns = {@JoinColumn(name = "self_id")}, inverseJoinColumns = {@JoinColumn(name = "friend_id")})
//    private Set<User> friends = new HashSet<>();

//    public Set<Review> getReviews() {
//        return reviews;
//    }

//    public void setWrittenReview(Set<Review> writtenReview) {
//        this.reviews = writtenReview;
//    }

//   public Set<User> getFriends() {
//        return friends;
//    }
//
//    public void setFriends(Set<User> friends) {
//        this.friends = friends;
//    }

// Getters and Setters

    /**
     * Set the id of the user in the DB.
     * @param id - Long. Auto generated value.
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public String getUserType() { return userType; }

    public void setUserType(String userType) { this.userType = userType; }

    //public Restaurant getRestaurant() { return restaurant; }

    //public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }

    public String getFavoriteFood() {
        return favoriteFood;
    }

    public void setFavoriteFood(String favoriteFood) {
        this.favoriteFood = favoriteFood;
    }

    public String getFavoriteRestaurant() {
        return favoriteRestaurant;
    }

    public void setFavoriteRestaurant(String favoriteRestaurant) {
        this.favoriteRestaurant = favoriteRestaurant;
    }

    public String getFavoriteDrink() {
        return favoriteDrink;
    }

    public void setFavoriteDrink(String favoriteDrink) {
        this.favoriteDrink = favoriteDrink;
    }

//    public void setReviews(Set<Review> reviews) {
//        this.reviews = reviews;
//    }
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        User user = (User) o;
//        return id.equals(user.id) &&
//                firstName.equals(user.firstName) &&
//                lastName.equals(user.lastName) &&
//                userName.equals(user.userName) &&
//                email.equals(user.email);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, firstName, lastName, userName, email);
//    }
}
