package com.example.simpletictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Plays a turn of the game when a button is pressed. This game has no logic to determine a winner
     * @param button
     */
    public void play(View button) {
        // Disable button pressed
        button.setEnabled(false);

        String turn = ((TextView) findViewById(R.id.turnText)).getText().toString();
        if (turn == getString(R.string.xTurn)) {
            // Place an X in the button pressed and switch to O's turn
            ((Button) button).setText(getString(R.string.xSpot));
            ((Button) button).setTextColor(Color.RED);
            ((Button) button).setTextSize(32);
            ((TextView) findViewById((R.id.turnText))).setText(getString(R.string.oTurn));

            // Check if the game is complete
            gameStatus();
        } else {
            // Place an O in the button pressed and switch to X's turn
            ((Button) button).setText(getString(R.string.oSpot));
            ((Button) button).setTextColor(Color.BLUE);
            ((Button) button).setTextSize(32);
            ((TextView) findViewById((R.id.turnText))).setText(getString(R.string.xTurn));

            // Check if the game is complete
            gameStatus();
        }
    }

    /**
     * Restarts the game when the restart button is pressed
     * @param reset
     */
    public void restart(View reset) {
        // Not the best way to do this
        ((Button) findViewById(R.id.topLeft)).setText("");
        ((Button) findViewById(R.id.topLeft)).setTextColor(Color.BLACK);
        ((Button) findViewById(R.id.topLeft)).setEnabled(true);

        ((Button) findViewById(R.id.topMiddle)).setText("");
        ((Button) findViewById(R.id.topMiddle)).setTextColor(Color.BLACK);
        ((Button) findViewById(R.id.topMiddle)).setEnabled(true);

        ((Button) findViewById(R.id.topRight)).setText("");
        ((Button) findViewById(R.id.topRight)).setTextColor(Color.BLACK);
        ((Button) findViewById(R.id.topRight)).setEnabled(true);

        ((Button) findViewById(R.id.middleLeft)).setText("");
        ((Button) findViewById(R.id.middleLeft)).setTextColor(Color.BLACK);
        ((Button) findViewById(R.id.middleLeft)).setEnabled(true);

        ((Button) findViewById(R.id.middleMiddle)).setText("");
        ((Button) findViewById(R.id.middleMiddle)).setTextColor(Color.BLACK);
        ((Button) findViewById(R.id.middleMiddle)).setEnabled(true);

        ((Button) findViewById(R.id.middleRight)).setText("");
        ((Button) findViewById(R.id.middleRight)).setTextColor(Color.BLACK);
        ((Button) findViewById(R.id.middleRight)).setEnabled(true);

        ((Button) findViewById(R.id.bottomLeft)).setText("");
        ((Button) findViewById(R.id.bottomLeft)).setTextColor(Color.BLACK);
        ((Button) findViewById(R.id.bottomLeft)).setEnabled(true);

        ((Button) findViewById(R.id.bottomMiddle)).setText("");
        ((Button) findViewById(R.id.bottomMiddle)).setTextColor(Color.BLACK);
        ((Button) findViewById(R.id.bottomMiddle)).setEnabled(true);

        ((Button) findViewById(R.id.bottomRight)).setText("");
        ((Button) findViewById(R.id.bottomRight)).setTextColor(Color.BLACK);
        ((Button) findViewById(R.id.bottomRight)).setEnabled(true);

        // Who moves first
        int upperBound = 100;
        int lowerBound = 0;
        int randomInt = (int) (Math.random() * (upperBound - lowerBound + 1) + lowerBound);

        if (randomInt < 50) {
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.xTurn));
        } else {
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.oTurn));
        }
    }

    /**
     * Checks for a game winner
     */
    public void gameStatus() {
        // If top row is X, end game
        if ((((Button) findViewById(R.id.topLeft)).getText().toString() == getString(R.string.xSpot)) && (((Button) findViewById(R.id.topMiddle)).getText().toString() == getString(R.string.xSpot)) &&
                (((Button) findViewById(R.id.topRight)).getText().toString() == getString(R.string.xSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.xWins));

        // If middle row is X
        } else if ((((Button) findViewById(R.id.middleLeft)).getText().toString() == getString(R.string.xSpot)) && (((Button) findViewById(R.id.middleMiddle)).getText().toString() == getString(R.string.xSpot)) &&
                (((Button) findViewById(R.id.middleRight)).getText().toString() == getString(R.string.xSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.xWins));

        // If bottom row is X
        } else if ((((Button) findViewById(R.id.bottomLeft)).getText().toString() == getString(R.string.xSpot)) && (((Button) findViewById(R.id.bottomMiddle)).getText().toString() == getString(R.string.xSpot)) &&
                (((Button) findViewById(R.id.bottomRight)).getText().toString() == getString(R.string.xSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.xWins));

        // If left column is X
        } else if ((((Button) findViewById(R.id.topLeft)).getText().toString() == getString(R.string.xSpot)) && (((Button) findViewById(R.id.middleLeft)).getText().toString() == getString(R.string.xSpot)) &&
                (((Button) findViewById(R.id.bottomLeft)).getText().toString() == getString(R.string.xSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.xWins));

        // If middle column is X
        } else if ((((Button) findViewById(R.id.topMiddle)).getText().toString() == getString(R.string.xSpot)) && (((Button) findViewById(R.id.middleMiddle)).getText().toString() == getString(R.string.xSpot)) &&
                (((Button) findViewById(R.id.bottomMiddle)).getText().toString() == getString(R.string.xSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.xWins));

        // If right column is X
        } else if ((((Button) findViewById(R.id.topRight)).getText().toString() == getString(R.string.xSpot)) && (((Button) findViewById(R.id.middleRight)).getText().toString() == getString(R.string.xSpot)) &&
                (((Button) findViewById(R.id.bottomRight)).getText().toString() == getString(R.string.xSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.xWins));

        // If leftToRight diagonal is X
        } else if ((((Button) findViewById(R.id.topLeft)).getText().toString() == getString(R.string.xSpot)) && (((Button) findViewById(R.id.middleMiddle)).getText().toString() == getString(R.string.xSpot)) &&
                (((Button) findViewById(R.id.bottomRight)).getText().toString() == getString(R.string.xSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.xWins));

        // If rightToLeft diagonal is X
        } else if ((((Button) findViewById(R.id.topRight)).getText().toString() == getString(R.string.xSpot)) && (((Button) findViewById(R.id.middleMiddle)).getText().toString() == getString(R.string.xSpot)) &&
                (((Button) findViewById(R.id.bottomLeft)).getText().toString() == getString(R.string.xSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.xWins));



        // Now check for O's

        // If top row is O
        } else if ((((Button) findViewById(R.id.topLeft)).getText().toString() == getString(R.string.oSpot)) && (((Button) findViewById(R.id.topMiddle)).getText().toString() == getString(R.string.oSpot)) &&
                (((Button) findViewById(R.id.topRight)).getText().toString() == getString(R.string.oSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.oWins));

        // If middle row is O
        } else if ((((Button) findViewById(R.id.middleLeft)).getText().toString() == getString(R.string.oSpot)) && (((Button) findViewById(R.id.middleMiddle)).getText().toString() == getString(R.string.oSpot)) &&
                (((Button) findViewById(R.id.middleRight)).getText().toString() == getString(R.string.oSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.oWins));

        // If bottom row is O
        } else if ((((Button) findViewById(R.id.bottomLeft)).getText().toString() == getString(R.string.oSpot)) && (((Button) findViewById(R.id.bottomMiddle)).getText().toString() == getString(R.string.oSpot)) &&
                (((Button) findViewById(R.id.bottomRight)).getText().toString() == getString(R.string.oSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.oWins));

        // If left column is O
        } else if ((((Button) findViewById(R.id.topLeft)).getText().toString() == getString(R.string.oSpot)) && (((Button) findViewById(R.id.middleLeft)).getText().toString() == getString(R.string.oSpot)) &&
                (((Button) findViewById(R.id.bottomLeft)).getText().toString() == getString(R.string.oSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.oWins));

        // If middle column is O
        } else if ((((Button) findViewById(R.id.topMiddle)).getText().toString() == getString(R.string.oSpot)) && (((Button) findViewById(R.id.middleMiddle)).getText().toString() == getString(R.string.oSpot)) &&
                (((Button) findViewById(R.id.bottomMiddle)).getText().toString() == getString(R.string.oSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.oWins));

        // If right column is O
        } else if ((((Button) findViewById(R.id.topRight)).getText().toString() == getString(R.string.oSpot)) && (((Button) findViewById(R.id.middleRight)).getText().toString() == getString(R.string.oSpot)) &&
                (((Button) findViewById(R.id.bottomRight)).getText().toString() == getString(R.string.oSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.oWins));

        // If leftToRight diagonal is O
        } else if ((((Button) findViewById(R.id.topLeft)).getText().toString() == getString(R.string.oSpot)) && (((Button) findViewById(R.id.middleMiddle)).getText().toString() == getString(R.string.oSpot)) &&
                (((Button) findViewById(R.id.bottomRight)).getText().toString() == getString(R.string.oSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.oWins));

        //If rightToLeft diagonal is O
        } else if ((((Button) findViewById(R.id.topRight)).getText().toString() == getString(R.string.oSpot)) && (((Button) findViewById(R.id.middleMiddle)).getText().toString() == getString(R.string.oSpot)) &&
                (((Button) findViewById(R.id.bottomLeft)).getText().toString() == getString(R.string.oSpot))) {
            disable();
            ((TextView) findViewById(R.id.turnText)).setText(getString(R.string.oWins));
        }
    }

    /**
     * Disables all playable buttons
     */
    public void disable() {
        // Disable all playable buttons
        findViewById(R.id.topLeft).setEnabled(false);
        findViewById(R.id.topMiddle).setEnabled(false);
        findViewById(R.id.topRight).setEnabled(false);
        findViewById(R.id.middleLeft).setEnabled(false);
        findViewById(R.id.middleMiddle).setEnabled(false);
        findViewById(R.id.middleRight).setEnabled(false);
        findViewById(R.id.bottomLeft).setEnabled(false);
        findViewById(R.id.bottomMiddle).setEnabled(false);
        findViewById(R.id.bottomRight).setEnabled(false);
    }
}