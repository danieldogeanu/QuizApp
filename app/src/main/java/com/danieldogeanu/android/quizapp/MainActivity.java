package com.danieldogeanu.android.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * Score Keeping Variables
     *
     * questionsAnswered - Keeps track of the number of questions answered. (0/7)
     * totalPoints - Keeps track of the points scored for answered questions. Some questions may have more than one point. (0/10)
     */
    int questionsAnswered = 0;
    int totalPoints = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method displays the number of answered questions (progress), regardless if the question's answer is right or wrong.
     *
     * @param progress - Number of questions answered.
     */
    private void displayProgress(int progress) {
        TextView progressTextView = (TextView) findViewById(R.id.display_progress);
        progressTextView.setText("" + progress);
    }

    /**
     * This method displays how many points the user has scored during the quiz. Wrong answers are not added to the score.
     *
     * @param score - Points scored by the user.
     */
    private void displayScore(int score) {
        TextView scoreTextView = (TextView) findViewById(R.id.display_score);
        scoreTextView.setText("" + score);
    }
}
