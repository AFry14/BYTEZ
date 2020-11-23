package com.example.Bytez_frontend.Features;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.User;

public class ProfileActivity extends AppCompatActivity {

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent i = getIntent();
        currentUser = i.getParcelableExtra("user");

        // If there is no user data, or the user is the logged in user, use the current logged in user
        if (currentUser == null || currentUser.getId() == (SharedPrefManager.getInstance(this).getUser().getId())) {
            currentUser = SharedPrefManager.getInstance(this).getUser();
        } else {
            // If the user is not the current user, disable the edit profile button
            Button editProfile = findViewById(R.id.editButton);
            editProfile.setEnabled(false);
        }
        setTitle(currentUser.getUsername() + "'s Profile");


        Button username = findViewById(R.id.usernameButton);
        Button email = findViewById(R.id.emailButton);
        Button firstName = findViewById(R.id.firstNameButton);
        Button lastName = findViewById(R.id.lastNameButton);
        Button favFood = findViewById(R.id.favFoodButton);
        Button favDrink = findViewById(R.id.favDrinkButton);
        Button favRest = findViewById(R.id.favRestButton);

        // Set the user traits
        username.setText(currentUser.getUsername());
        email.setText(currentUser.getEmail());
        firstName.setText(currentUser.getfName());
        lastName.setText(currentUser.getlName());
        favFood.setText(currentUser.getFavFood());
        favDrink.setText(currentUser.getFavDrink());
        favRest.setText(currentUser.getFavRestaurant());
    }

    /**
     * Launches the edit profile activity
     * @param view
     */
    public void editProfile(View view) {
        Intent editProfActivity = new Intent(this, EditProfileActivity.class);
        editProfActivity.putExtra("user", currentUser);
        startActivity(editProfActivity);
    }

    /**
     * Launches the friends activity
     * @param view
     */
    public void launchFriends(View view) {
        Intent friendsActivity = new Intent(this, FriendsActivity.class);
        friendsActivity.putExtra("user", currentUser);
        startActivity(friendsActivity);
    }

    public void launchMyReviews(View view) {
        Intent myReviewsActivity = new Intent(this, MyReviewsActivity.class);
        startActivity(myReviewsActivity);
    }

    /**
     * Launches the review activity
     * @param view
     */
    public void launchReviews(View view) {
        //
    }

    /**
     * Launches the settings activity
     * @param view
     */
    public void launchSettings(View view) {
        //
    }

    /**
     * Launches google maps when the restaurant is clicked
     * The restaurant is entered as the search term in google maps
     * @param view
     */
    public void restaurantToMap(View view) {
        Button favRestaurant = (Button) view;
        String restaurantAddress = favRestaurant.getText().toString();

        // Launch google maps with the restaurant name as the search term
        // Searches for closest restaurant in that area (the emulator's location is Google's headquarters)
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + restaurantAddress);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}