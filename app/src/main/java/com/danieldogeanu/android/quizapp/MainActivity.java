package com.danieldogeanu.android.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /** Keep track of the number of questions answered. (max 7 questions) */
    private int questionsAnswered;

    /**
     * Keep track of the points scored for answered questions.
     * Some questions may have more than one point. (max 10 points)
     */
    private int totalPoints;

    /** Show if the result (score) was already displayed, in order to prevent reading points. */
    private boolean wasScoreDisplayed = false;


    /** ID Array for all RadioGroups. */
    private int[] allRadioGroups = {
            R.id.radio_group_one,
            R.id.radio_group_three,
            R.id.radio_group_five,
            R.id.radio_group_six
    };

    /** ID Array with all the correct answers for the RadioGroups. */
    private int[] correctRadioAnswers = {
            R.id.radio_one_c,
            R.id.radio_three_a,
            R.id.radio_five_b,
            R.id.radio_six_c
    };

    /** ID Array with all the CheckBoxes Groups (parent views) */
    private int[] checkGroupQuestions = {
            R.id.check_group_four,
            R.id.check_group_seven
    };

    /** ID Array with all the CheckBoxes. */
    private int[][] allCheckBoxes = {
            { R.id.check_four_a, R.id.check_four_b, R.id.check_four_c, R.id.check_four_d },
            { R.id.check_seven_a, R.id.check_seven_b, R.id.check_seven_c, R.id.check_seven_d, R.id.check_seven_e }
    };

    /** ID Array with all the correct answers for CheckBoxes. */
    private int[][] correctCheckAnswers = {
            { R.id.check_four_a, R.id.check_four_c },
            { R.id.check_seven_b, R.id.check_seven_d, R.id.check_seven_e }
    };

    /** ArrayList for keeping track of answered questions in setCheckListeners() method. */
    private ArrayList<Integer> checkGroupAnswered = new ArrayList<>();

    /** ID Array with all EditText questions. */
    private int[] allEditTextQuestions = { R.id.edit_text_two };

    /** Array with string resources for EditText correct answers. */
    private int[] correctEditTextAnswers = { R.string.question_two_answer };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Display the initial values for the Score Keeping Variables
        displayProgress(questionsAnswered);
        displayScore(totalPoints);

        // Attach Click Listeners for all RadioGroups and Checkboxes
        setRadioListeners();
        setCheckListeners();
        setEditTextListeners();
    }

    /**
     * This method displays the number of answered questions (progress), regardless if the question's answer is right or wrong.
     * @param progress Number of questions answered.
     */
    private void displayProgress(int progress) {
        TextView progressTextView = (TextView) findViewById(R.id.display_progress);
        String progressText = getString(R.string.progress_text, Integer.toString(progress));
        progressTextView.setText(progressText);
    }

    /**
     * This method displays how many points the user has scored during the quiz. Wrong answers are not added to the score.
     * @param score Points scored by the user.
     */
    private void displayScore(int score) {
        TextView scoreTextView = (TextView) findViewById(R.id.display_score);
        String scoreText = getString(R.string.score_text, Integer.toString(score));
        scoreTextView.setText(scoreText);
    }

    /**
     * Change the color of the Score text based on totalScore variable.
     * @param score Total points scored by the user.
     */
    private void changeScoreColor(int score) {
        TextView scoreTextView = (TextView) findViewById(R.id.display_score);

        if (score == 10) {
            scoreTextView.setTextColor(getResources().getColor(R.color.colorGreen));
        } else if ((score >= 7) && (score <= 9)) {
            scoreTextView.setTextColor(getResources().getColor(R.color.colorLightGreen));
        } else if ((score >= 3) && (score <= 6)) {
            scoreTextView.setTextColor(getResources().getColor(R.color.colorOrange));
        } else {
            scoreTextView.setTextColor(getResources().getColor(R.color.colorRed));
        }
    }

    /** Reset the color of the Score text to the default one. */
    private void resetScoreColor() {
        TextView scoreTextView = (TextView) findViewById(R.id.display_score);
        scoreTextView.setTextColor(getResources().getColor(R.color.cardBackground));
    }

    /**
     * Display a short toast message in case of error or action that needs to show feedback to the user.
     * @param message The message to display (needs to be short and concise).
     */
    private void showToast(CharSequence message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /** Increment the number of answered questions. */
    private void incrementQuestions() {
        if (questionsAnswered < 7) {
            questionsAnswered++;
        }
        String questions = Integer.toString(questionsAnswered);
        showToast(getString(R.string.questions_toast, questions));
    }

    /** Reset the number of answered questions. */
    private void resetQuestions() {
        questionsAnswered = 0;
    }

    /** Add points to the total score. */
    private void addPoint() {
        if (totalPoints < 10) {
            totalPoints++;
        }
    }

    /** Reset the number of points. */
    private void resetPoints() {
        totalPoints = 0;
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
                    addPoint();
                }
            }
        }
    }

    /**
     * Get all the answers from all the questions with checkboxes and check to see if they're correct.
     * If they are correct, add one point for each question.
     */
    private void getCheckAnswers() {
        for (int i = 0; i < allCheckBoxes.length; i++) {
            // Get the arrays with the checkboxes IDs for each question.
            int[] checkQuestion = allCheckBoxes[i];
            int[] correctAnswers = correctCheckAnswers[i];

            // Convert correctAnswers int array into ArrayList
            // so we can use contains() method later on it.
            ArrayList<Integer> correctAnswersList = new ArrayList<>();
            for (int correctAnswer : correctAnswers) {
                correctAnswersList.add(correctAnswer);
            }

            // Create 3 ArrayLists for all selected answers, correct answers and wrong answers.
            ArrayList<Integer> currentQuestionAnswers = new ArrayList<>();
            ArrayList<Integer> currentCorrectAnswers = new ArrayList<>();
            ArrayList<Integer> currentWrongAnswers = new ArrayList<>();

            // Get the IDs of checked checkboxes and add them to currentQuestionAnswers.
            for (int checkID : checkQuestion) {
                CheckBox thisCheckBox = (CheckBox) findViewById(checkID);
                if (thisCheckBox.isChecked()) {
                    int currentID = thisCheckBox.getId();
                    currentQuestionAnswers.add(currentID);
                }
            }

            // See if the selected answers are correct or not and distribute them appropriately.
            for (int currentAnswer : currentQuestionAnswers) {
                if (correctAnswersList.contains(currentAnswer)) {
                    currentCorrectAnswers.add(currentAnswer);
                } else {
                    currentWrongAnswers.add(currentAnswer);
                }
            }

            // Check to see if we have any wrong answers, if we do, we bail, if we don't,
            // we check to see if we have all the correct answers. Only then we add a point.
            if ((currentWrongAnswers.size() == 0) && (currentCorrectAnswers.size() == correctAnswers.length)) {
                addPoint();
            }
        }
    }

    /**
     * Get all the answers from all the EditText questions and see if they're correct.
     * If they are, add a point for each question.
     */
    private void getEditTextAnswers() {
        for (int i = 0; i < allEditTextQuestions.length; i++) {
            int editTextID = allEditTextQuestions[i];
            int correctAnswerID = correctEditTextAnswers[i];
            String correctAnswer = getString(correctAnswerID);

            EditText thisEditText = (EditText) findViewById(editTextID);
            String thisAnswer = thisEditText.getText().toString();

            if (thisAnswer.equals(correctAnswer)) {
                addPoint();
            }
        }
    }

    /**
     * Set on checked changed listeners for all the RadioGroups,
     * so that when a question is answered, the progress number is incremented.
     */
    protected void setRadioListeners() {
        for (int groupID : allRadioGroups) {
            RadioGroup thisRadioGroup = (RadioGroup) findViewById(groupID);

            thisRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                int wasAnswered = 0;

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if ((checkedId != -1) && (wasAnswered < 1)) {
                        incrementQuestions();
                        displayProgress(questionsAnswered);
                        wasAnswered++;
                    }
                }
            });
        }
    }

    /**
     * Set on click listeners for all the CheckBoxes,
     * so that when a question is answered, the progress number is incremented.
     */
    protected void setCheckListeners() {
        for (int[] currentQuestion : allCheckBoxes) {
            for (int checkID : currentQuestion) {
                CheckBox thisCheckBox = (CheckBox) findViewById(checkID);

                thisCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View thisParent = (View) v.getParent();
                        int thisParentID = thisParent.getId();

                        for (int checkGroup : checkGroupQuestions) {
                            if ((!checkGroupAnswered.contains(checkGroup)) && (checkGroup == thisParentID)) {
                                incrementQuestions();
                                displayProgress(questionsAnswered);
                                checkGroupAnswered.add(checkGroup);
                            }
                        }

                    }
                });
            }
        }
    }

    /**
     * Set text changed listeners for all EditText questions,
     * so that, when a question is answered, the progress number is incremented.
     */
    protected void setEditTextListeners() {
        for (int editTextID : allEditTextQuestions) {
            EditText thisEditText = (EditText) findViewById(editTextID);
            thisEditText.addTextChangedListener(new TextWatcher() {
                int wasAnswered = 0;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    String thisAnswer = s.toString();

                    if ((!thisAnswer.isEmpty()) && (wasAnswered < 1)) {
                        incrementQuestions();
                        displayProgress(questionsAnswered);
                        wasAnswered++;
                    }
                }
            });
        }
    }

    /** Reset the state variables for setCheckListeners() method. */
    private void resetCheckAnswers() {
        checkGroupAnswered.clear();
    }

    /** Clears all RadioButtons from all RadioGroups. */
    private void clearRadioAnswers() {
        for (int groupID : allRadioGroups) {
            RadioGroup thisRadioGroup = (RadioGroup) findViewById(groupID);
            thisRadioGroup.clearCheck();
        }
    }

    /** Clears all CheckBoxes for all questions. */
    private void clearCheckAnswers() {
        for (int[] currentQuestion : allCheckBoxes) {
            for (int checkID : currentQuestion) {
                CheckBox thisCheckBox = (CheckBox) findViewById(checkID);
                if (thisCheckBox.isChecked()) {
                    thisCheckBox.setChecked(false);
                }
            }
        }
    }

    /** Clears all EditText fields. */
    private void clearEditTextAnswers() {
        for (int editTextID: allEditTextQuestions) {
            EditText thisEditText = (EditText) findViewById(editTextID);
            String thisAnswer = thisEditText.getText().toString();
            if (!thisAnswer.isEmpty()) {
                thisEditText.setText("");
                thisEditText.clearFocus();
            }
        }
    }

    /**
     * Show the results of the quiz, based on the answers provided.
     * This method is called when the Show Results button is clicked.
     */
    public void showResults(View view) {
        if (!wasScoreDisplayed) {
            getRadioAnswers();
            getCheckAnswers();
            getEditTextAnswers();

            String score = Integer.toString(totalPoints);
            showToast(getString(R.string.score_toast, score));

            displayScore(totalPoints);
            changeScoreColor(totalPoints);
            wasScoreDisplayed = true;
        }
    }

    /**
     * Reset the results and progress of the entire quiz.
     * This method is called when the Reset button is clicked.
     */
    public void resetResults(View view) {
        resetQuestions();
        resetPoints();
        clearRadioAnswers();
        clearCheckAnswers();
        clearEditTextAnswers();
        displayProgress(questionsAnswered);
        displayScore(totalPoints);
        resetScoreColor();
        setRadioListeners();
        resetCheckAnswers();
        wasScoreDisplayed = false;
        showToast(getString(R.string.reset_toast));
    }
}
