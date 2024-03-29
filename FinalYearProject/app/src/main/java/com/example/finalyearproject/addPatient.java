package com.example.finalyearproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddPatient extends AppCompatActivity {

    private EditText inputFirstName, inputLastName, inputDescription;
    private TextView inputDateOfBirth;
    private DatePickerDialog.OnDateSetListener setListener;
    private final String[] genders = {"Male", "Female", "Other", "Prefer Not to Say"};
    private AutoCompleteTextView inputGender;
    private String firstName, lastName, dateOfBirth, gender, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_patient);

        assignValues();
        createScrollCalender();
        genderDropBar();


        Button addPatientToDB = findViewById(R.id.updatePatientToDB);
        addPatientToDB.setOnClickListener(view -> {
            returnToListOfPatients();
            getPatientDetails();
            insertData();
        });

        Button cancelAddingPatient = findViewById(R.id.cancel);
        cancelAddingPatient.setOnClickListener(v -> returnToListOfPatients());
    }

    private void insertData() {
        //add all user data into map
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("dateOfBirth", dateOfBirth);
        map.put("gender", gender);
        map.put("description", description);

        //push all user date inside "patient" database in firebase
        FirebaseDatabase.getInstance().getReference().child("patient").push()
                .setValue(map)
                .addOnSuccessListener(unused -> Toast.makeText(AddPatient.this, "New Patient Added Successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(AddPatient.this, "Failed to Add Patient", Toast.LENGTH_SHORT).show());
    }

    //drop bar is made to allow user to pick gender
    private void genderDropBar() {
        ArrayAdapter<String> genderList = new ArrayAdapter<>(this, R.layout.gender_list, genders);
        inputGender.setAdapter(genderList);
    }

    //creates a scroll calender, for user to pick a date for birthday
    private void createScrollCalender() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        inputDateOfBirth.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddPatient.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    setListener, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();

        });

        setListener = (view, year1, month1, dayOfMonth) -> {
            month1 = month1 +1;
            String date = day + "/" + month1 + "/" + year1;
            inputDateOfBirth.setText(date);
        };
    }

    //assign values in xml file that correlates to this java class
    private void assignValues() {
        inputFirstName = findViewById(R.id.patientFirstName);
        inputLastName = findViewById(R.id.patientLastName);
        inputDescription = findViewById(R.id.patientDescription);
        inputDateOfBirth = findViewById(R.id.patient_date_of_birth);
        inputGender = findViewById(R.id.gender);
    }

    //retrieving patients details
    public void getPatientDetails() {
        firstName = inputFirstName.getText().toString();
        lastName = inputLastName.getText().toString();
        dateOfBirth = inputDateOfBirth.getText().toString();
        gender = inputGender.getText().toString();
        if (TextUtils.isEmpty(inputDescription.getText().toString()))
            description = "N/A";
        else description = inputDescription.getText().toString();
    }

    //moves current page to listOfPatients page
    public void returnToListOfPatients() {
        Intent intent = new Intent(this, ListOfPatients.class);
        startActivity(intent);
    }
}