package com.danieldogeanu.android.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * Score Variables:
     * questionsAnswered - (int) Keeps track of the number of questions answered. (0/7)
     * totalPoints - (int) Keeps track of the points scored for answered questions. Some questions may have more than one point. (0/10)
     */
    private int questionsAnswered = 0;
    private int totalPoints = 0;

    /**
     * ID Arrays for all Radio Groups and Checkboxes.
     * allRadioGroups - (int[]) ID array for all radio groups.
     * correctRadioAnswers - (int[]) ID array with all the correct answers for the radio groups.
     * allCheckBoxes - (int[]) ID array with all the checkboxes.
     * correctCheckAnswers - (int[]) ID array with all the correct answers for checkboxes.
     */
    private int[] allRadioGroups = {
            R.id.radio_group_one,
            R.id.radio_group_two,
            R.id.radio_group_three,
            R.id.radio_group_five,
            R.id.radio_group_six
    };
    private int[] correctRadioAnswers = {
            R.id.radio_one_c,
            R.id.radio_two_c,
            R.id.radio_three_a,
            R.id.radio_five_b,
            R.id.radio_six_c
    };
    private int[] allCheckBoxes = {
            R.id.check_four_a,
            R.id.check_four_b,
            R.id.check_four_c,
            R.id.check_four_d,
            R.id.check_seven_a,
            R.id.check_seven_b,
            R.id.check_seven_c,
            R.id.check_seven_d,
            R.id.check_seven_e
    };
    private int[] correctCheckAnswers = {
            R.id.check_four_a,
            R.id.check_four_c,
            R.id.check_seven_b,
            R.id.check_seven_d,
            R.id.check_seven_e
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Temporary Display Score Keeping Variables
        displayProgress(questionsAnswered);
        displayScore(totalPoints);
    }

    /**
     * This method displays the number of answered questions (progress), regardless if the question's answer is right or wrong.
     * @param progress - Number of questions answered.
     */
    private void displayProgress(int progress) {
        TextView progressTextView = (TextView) findViewById(R.id.display_progress);
        String progressText = getString(R.string.progress_text, Integer.toString(progress));
        progressTextView.setText(progressText);
    }

    /**
     * This method displays how many points the user has scored during the quiz. Wrong answers are not added to the score.
     * @param score - Points scored by the user.
     */
    private void displayScore(int score) {
        TextView scoreTextView = (TextView) findViewById(R.id.display_score);
        String scoreText = getString(R.string.score_text, Integer.toString(score));
        scoreTextView.setText(scoreText);
    }

    /**
     * Display a short toast message in case of error or action that needs to show feedback to the user.
     * @param message - The message to display (needs to be short and concise).
     */
    private void showToast(CharSequence message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Increment the number of answered questions.
     * @return - Incremented questionsAnswered.
     */
    private int incrementQuestions() {
        if (questionsAnswered < 7) {
            questionsAnswered++;
        }
        showToast(getString(R.string.questions_toast));
        return questionsAnswered;
    }

    /**
     * Decrement the number of answered questions.
     * @return - Decremented questionsAnswered.
     */
    private int decrementQuestions() {
        if (questionsAnswered != 0) {
            questionsAnswered--;
        }
        showToast(getString(R.string.questions_toast));
        return questionsAnswered;
    }

    /**
     * Reset the number of answered questions.
     * @return - Reset questionsAnswered.
     */
    private int resetQuestions() {
        questionsAnswered = 0;
        return questionsAnswered;
    }

    /**
     * Add points to the total score.
     * @param points - Number of points to add.
     * @return - Incremented totalPoints variable.
     */
    private int addPoints(int points) {
        if (totalPoints < 10) {
            totalPoints += points;
        }
        return totalPoints;
    }

    /**
     * Subtract points from the total score.
     * @param points - Number of points to subtract.
     * @return - Decremented totalPoints variable.
     */
    private int subtractPoints(int points) {
        if ((totalPoints != 0) && (totalPoints >= points)) {
            totalPoints -= points;
        }
        return totalPoints;
    }

    /**
     * Reset the number of points.
     * @return - Reset totalPoints.
     */
    private int resetPoints() {
        totalPoints = 0;
        return totalPoints;
    }

    /**
     * Get all the answers from all the RadioGroups and check to see if they're correct.
     * If they are correct, add one point for each correct answer.
     */
    private void getRadioAnswers() {
        for (int groupID : allRadioGroups) {
            RadioGroup thisRadioGroup = (RadioGroup) findViewById(groupID);
            int selectedAnswer = thisRadioGroup.getCheckedRadioButtonId();

            for (int correctAnswer : correctRadioAnswers) {
                if (selectedAnswer == correctAnswer) {
                    addPoints(1);
                }
            }
        }
    }

    /**
     * Show the results of the quiz, based on the answers provided.
     * This method is called when the Show Results button is clicked.
     */
    public void showResults(View view) {
        getRadioAnswers();
        displayScore(totalPoints);
    }

    /**
     * Reset the results and progress of the entire quiz.
     * This method is called when the Reset button is clicked.
     */
    public void resetResults(View view) {
        resetQuestions();
        resetPoints();
        displayProgress(questionsAnswered);
        displayScore(totalPoints);
    }
}
