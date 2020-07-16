package com.example.personalvehicledelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerInfoActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    EditText editTextCustomerName, editTextCustomerPhone,editTextCustomerAddress, editTextCustomerCity, editTextCustomerZip;
    Spinner spinnerState;
    Button buttonAddCustomer;

    private static final String USERS = "users";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USERS);

        editTextCustomerName = findViewById(R.id.editTextCustomerName);
        editTextCustomerPhone = findViewById(R.id.editTextCustomerPhone);
        editTextCustomerAddress = findViewById(R.id.editTextCustomerAddress);
        editTextCustomerCity = findViewById(R.id.editTextCustomerCity);
        editTextCustomerZip= findViewById(R.id.editTextCustomerCity);
        spinnerState = findViewById(R.id.spinnerState);

        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerName = editTextCustomerName.getText().toString();
                String customerPhone = editTextCustomerPhone.getText().toString();
                String customerAddress = editTextCustomerAddress.getText().toString();
                String customerState = spinnerState.getSelectedItem().toString();
                String customerCity = editTextCustomerCity.getText().toString();
                String customerZip = editTextCustomerZip.getText().toString();


                String uid = mAuth.getCurrentUser().getUid();
                mDatabase.child(uid).child("customer_name").setValue(customerName);
                mDatabase.child(uid).child("customer_phone").setValue(customerPhone);
                mDatabase.child(uid).child("customer_address").setValue(customerAddress);
                mDatabase.child(uid).child("customer_state").setValue(customerState);
                mDatabase.child(uid).child("customer_city").setValue(customerCity);
                mDatabase.child(uid).child("customer_zip").setValue(customerZip);

                finish();
                startActivity(new Intent(CustomerInfoActivity.this, CustomerMainActivity.class));

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