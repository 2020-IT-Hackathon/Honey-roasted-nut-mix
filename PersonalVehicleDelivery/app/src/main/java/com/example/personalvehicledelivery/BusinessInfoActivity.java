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

    EditText editName, editRestaurantName, editStreetAddress, editBusinessPhone, editEmployeeID, editCity, editZip;
    Spinner spinnerState;
    Button  buttonAddBusinessInfo;
    private FirebaseAuth mAuth;

    // DB and DB reference
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    private static final String USER = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_info);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);

        editName = findViewById(R.id.editName);
        editRestaurantName = findViewById(R.id.editRestaurantName);
        editStreetAddress = findViewById(R.id.editStreetAddress);
        editCity = findViewById(R.id.editCity);
        editZip = findViewById(R.id.editZip);
        editBusinessPhone = findViewById(R.id.editBusinessPhone);
        spinnerState = findViewById(R.id.spinnerState);

        buttonAddBusinessInfo = findViewById(R. id.buttonAddBusinessInfo);

        buttonAddBusinessInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = editName.getText().toString().trim();
                String restaurantName = editRestaurantName.getText().toString().trim();
                String streetAddress = editStreetAddress.getText().toString().trim();
                String city = editCity.getText().toString().trim();
                String zip = editZip.getText().toString().trim();
                String businessPhone = editBusinessPhone.getText().toString().trim();
                String state = spinnerState.getSelectedItem().toString();

                String uid = mAuth.getCurrentUser().getUid();
                mDatabase.child(uid).child("full_name").setValue(fullName);
                mDatabase.child(uid).child("zip").setValue(zip);
                mDatabase.child(uid).child("business_name").setValue(restaurantName);
                mDatabase.child(uid).child("street_address").setValue(streetAddress);
                mDatabase.child(uid).child("city").setValue(city);
                mDatabase.child(uid).child("business_phone").setValue(businessPhone);
                mDatabase.child(uid).child("state").setValue(state);

                finish();
                startActivity(new Intent(BusinessInfoActivity.this, BusinessMainActivity.class));
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