package com.example.BYTEZ_Frontend;

import com.example.Bytez_frontend.Restaurant;
import com.example.Bytez_frontend.Review;
import com.example.Bytez_frontend.ReviewPackage.ReviewContract;
import com.example.Bytez_frontend.ReviewPackage.ReviewPresenter;
import com.example.Bytez_frontend.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ReviewPresenterTest
{
    private ReviewContract.Presenter reviewPresenter;

    @Mock
    private ReviewContract.View reviewView;

    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        reviewPresenter = new ReviewPresenter(reviewView);
    }

    @Test
    public void getFinalRating_AllZeros_Zero()
    {
        Restaurant rest = new Restaurant("Taco Bell", "123 Hello Ave.");
        User user = new User(1, "Hello", "Hello@gmail.com");
        Review review = new Review(0, rest, user);
        reviewPresenter.getFinalRating(review);
        verify(reviewView).
    }

    @Test
    public float getFinalRating_AllFives_Five()
    {
        return 0;
    }

    @Test
    public float getFinalRating_OneThreeFive_Three()
    {
        return 0;
    }

    @Test
    public void setFoodQR_setZero_Zero()
    {

    }

    @Test
    public void setFoodQR_setFive_Five()
    {

    }


}