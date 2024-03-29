package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionOne extends AppCompatActivity {
    private ImageView imageView;
    private Button correct, incorrect, continueButton;
    private String firstName, lastName, id;
    private int score = 0;

    //array is used to store all possible pictures that could be asked for the user
    private final Integer[] viewImage = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            R.drawable.image9,
            R.drawable.image10,
            R.drawable.image11,
            R.drawable.image12,
            R.drawable.image13,
            R.drawable.image14,
            R.drawable.image15,
            R.drawable.image16,
            R.drawable.image17,
            R.drawable.image18,
            R.drawable.image19,
            R.drawable.image20,
            R.drawable.image21,
            R.drawable.image22,
            R.drawable.image23,
            R.drawable.image24,
            R.drawable.image25,
            R.drawable.image26,
            R.drawable.image27,
            R.drawable.image28,
            R.drawable.image29,
            R.drawable.image30
    };

    private final List<Integer> imageList = Arrays.asList(viewImage);
    private final List<Integer> usedImage = new ArrayList<>();
    private final int[] pos = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_one);

        retrieveBundleInformation();

        assignVariables();
        showImageList();

        imageView.setImageResource(usedImage.get(pos[0]));
        pos[0]++;

        correct.setOnClickListener(view -> {
            score++;
            moveToNextPicture();
        });

        incorrect.setOnClickListener(view -> moveToNextPicture());

        continueButton.setOnClickListener(view -> continueToQuestionTwo());

    }

    //after user clicks button, moves on to the next picture
    private void moveToNextPicture() {
        if(pos[0] == 5) {
            finishGame();
        }
        else {
            imageView.setImageResource(usedImage.get(pos[0]));
            pos[0]++;
        }
    }

    private void retrieveBundleInformation() {
        Bundle patientName = getIntent().getExtras();
        if(patientName != null) {
            firstName = patientName.getString("firstName");
            lastName = patientName.getString("lastName");
            id = patientName.getString("id");
        }

    }

    //moves on to the next question, while keeping the result of this question
    private void continueToQuestionTwo() {
        Intent intent = new Intent(this, QuestionTwo.class);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("id", id);
        intent.putExtra("questionOne", score);
        startActivity(intent);
    }

    //when the user finishes guess all 5 images it shows the continue button
    private void finishGame() {
        correct.setEnabled(false);
        incorrect.setEnabled(false);
        imageView.setVisibility(View.INVISIBLE);
        continueButton.setVisibility(View.VISIBLE);
    }

    //gets 5 random images from the image array and will be use to show the user
    private void showImageList() {
        int [] randomNumbers = new int [5];
        //loop is used to make sure none of the 5 random numbers that are picked are the same as the previous one
        shuffleList(this.imageList);
        for (int i = 0; i < 29; i++) {
            randomNumbers[0] = (int)(Math.random()*29)+1;

            while (randomNumbers[1] == randomNumbers[0])
            {
                randomNumbers[1] = (int)(Math.random()*39)+1;
            }
            while ((randomNumbers[2] == randomNumbers[1]) || (randomNumbers[2] == randomNumbers[0]) )
            {
                randomNumbers[2] = (int)(Math.random()*29)+1;
            }
            while ((randomNumbers[3] == randomNumbers[0]) || (randomNumbers[3] == randomNumbers[1]) || (randomNumbers[3] == randomNumbers[2]) )
            {
                randomNumbers[3] = (int)(Math.random()*29)+1;
            }
            while ((randomNumbers[4] == randomNumbers[0]) ||
                    (randomNumbers[4] == randomNumbers[1]) ||
                    (randomNumbers[4] == randomNumbers[2]) ||
                    (randomNumbers[4] == randomNumbers[3]) )
            {
                randomNumbers[4] = (int)(Math.random()*29)+1;
            }

        }

        //store the randomly picked images inside a list to be used to show the user the randomly picked images
        this.usedImage.add(this.imageList.get(randomNumbers[0]));
        this.usedImage.add(this.imageList.get(randomNumbers[1]));
        this.usedImage.add(this.imageList.get(randomNumbers[2]));
        this.usedImage.add(this.imageList.get(randomNumbers[3]));
        this.usedImage.add(this.imageList.get(randomNumbers[4]));
    }

    //shuffling list
    private void shuffleList(List<Integer> imageList) {
        Collections.shuffle(imageList);
        Collections.shuffle(imageList);
        Collections.shuffle(imageList);
    }

    //assigning variables from XML file
    private void assignVariables() {
        imageView = findViewById(R.id.imageView);
        correct = findViewById(R.id.correct);
        incorrect = findViewById(R.id.incorrect);
        continueButton = findViewById(R.id.continueToQTwo);
        continueButton.setVisibility(View.INVISIBLE);
    }
}