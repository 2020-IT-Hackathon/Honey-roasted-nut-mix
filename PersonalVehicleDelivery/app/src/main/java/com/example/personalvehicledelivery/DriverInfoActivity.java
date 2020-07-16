package com.example.personalvehicledelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverInfoActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    EditText editTextFullName, editTextDate, editTextZip, editTextDLNumber, editTextPlate, editTextInsuranceNumber, editTextVehicleMake, editTextVehicleModel;
    Spinner spinner;

    private static final String USERS = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_info);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USERS);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextDate = findViewById(R.id.editTextDate);
        editTextZip = findViewById(R.id.editZip);
        editTextDLNumber = findViewById(R.id.editTextDLNumber);
        editTextPlate = findViewById(R.id.editTextPlate);
        editTextInsuranceNumber = findViewById(R.id.editTextInsuranceNumber);
        editTextVehicleMake = findViewById(R.id.editTextVehicleMake);
        editTextVehicleModel = findViewById(R.id.editTextVehicleModel);
        spinner = findViewById(R.id.spinner);

        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = editTextFullName.getText().toString();
                String birthdate = editTextDate.getText().toString();
                String zip = editTextZip.getText().toString();
                String dlnumber = editTextDLNumber.getText().toString();
                String plate = editTextPlate.getText().toString();
                String insurance = editTextInsuranceNumber.getText().toString();
                String vmake = editTextVehicleMake.getText().toString();
                String vmodel = editTextVehicleModel.getText().toString();
                String vtype = spinner.getSelectedItem().toString();

                String uid = mAuth.getCurrentUser().getUid();
                mDatabase.child(uid).child("full_name").setValue(fullname);
                mDatabase.child(uid).child("birth_date").setValue(birthdate);
                mDatabase.child(uid).child("zip").setValue(zip);
                mDatabase.child(uid).child("driver_license_number").setValue(dlnumber);
                mDatabase.child(uid).child("plate_number").setValue(plate);
                mDatabase.child(uid).child("insurance_number").setValue(insurance);
                mDatabase.child(uid).child("vehicle_make").setValue(vmake);
                mDatabase.child(uid).child("vehicle_model").setValue(vmodel);
                mDatabase.child(uid).child("vehicle_type").setValue(vtype);

                finish();
                startActivity(new Intent(DriverInfoActivity.this, DriverMainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}