package com.example.personalvehicledelivery;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BusinessInfoActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    EditText editName, editRestaurantName, editStreetAddress, editBusinessPhone, editEmployeeID;
    Spinner spinnerState;
    Button  buttonAddBusinessInfo;

    private static final String USERS = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_info);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USERS);

        editName = (EditText) findViewById(R.id.editName);
        editRestaurantName = (EditText) findViewById(R.id.editRestaurantName);
        editStreetAddress = (EditText) findViewById(R.id.editStreetAddress);
        editBusinessPhone = (EditText) findViewById(R.id.editBusinessPhone);
        editEmployeeID = (EditText) findViewById(R.id.editEmployeeID);
        spinnerState = (Spinner) findViewById(R.id.spinnerState);

        buttonAddBusinessInfo = (Button) findViewById(R.id.buttonAddBusinessInfo);

        buttonAddBusinessInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBusinessOwner();
            }
        });

    }

    public void addBusinessOwner(){
        //Get Business Owner Info
        String Name          = editName.getText().toString().trim();
        String restaurantName= editRestaurantName.getText().toString().trim();
        String streetAddress = editStreetAddress.getText().toString().trim();
        String businessPhone = editBusinessPhone.getText().toString().trim();
        String employeeID    = editEmployeeID.getText().toString().trim();
        String State         = spinnerState.getSelectedItem().toString();


        //Phone Number Validation
        if (!Patterns.PHONE.matcher(businessPhone).matches()){
            editBusinessPhone.setError("Please enter a valid phone number");
            editBusinessPhone.requestFocus();
            return;
        }

        String uid = mAuth.getCurrentUser().getUid();
        mDatabase.child(uid).child("Name").setValue(Name);
        mDatabase.child(uid).child("restaurantName").setValue(restaurantName);
        mDatabase.child(uid).child("streetAddress").setValue(streetAddress);
        mDatabase.child(uid).child("businessPhone").setValue(businessPhone);
        mDatabase.child(uid).child("employeeID").setValue(employeeID);
        mDatabase.child(uid).child("State").setValue(State);

        finish();
        startActivity(new Intent(BusinessInfoActivity.this, BusinessMainActivity.class));

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