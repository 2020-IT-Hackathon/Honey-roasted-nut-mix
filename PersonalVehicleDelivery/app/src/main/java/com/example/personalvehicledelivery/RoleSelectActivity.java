package com.example.personalvehicledelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RoleSelectActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    private FirebaseAuth mAuth;
    // DB and DB reference
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    Button help_btn;
    TextView help_txt;

    private static final String USERS = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        help_btn = (Button)findViewById(R.id.help_button2);
        help_txt = (TextView)findViewById(R.id.help_text2);

        help_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                help_txt.setVisibility((help_txt.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);
            }
        });

        radioGroup = findViewById(R.id.radioGroup);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                // Adding user type into database
                String userType = radioButton.getText().toString().trim();
                String uid = mAuth.getCurrentUser().getUid();
                String userEmail = mAuth.getCurrentUser().getEmail();
                String userEmailKey = userEmail.replace('.', ',');
                mDatabase.child(USERS).child(uid).child("user_type").setValue(userType);
                mDatabase.child(userType.toLowerCase() + 's').child(userEmailKey).child("uid").setValue(uid);
                mDatabase.child(userType.toLowerCase() + 's').child(userEmailKey).child("email").setValue(userEmail);
                if (userType.equals("Customer")) {
                    finish();
                    startActivity(new Intent(RoleSelectActivity.this, CustomerInfoActivity.class));
                } else if (userType.equals("Driver")) {
                    finish();
                    startActivity(new Intent(RoleSelectActivity.this, DriverInfoActivity.class));
                } else if (userType.equals("Business Owner")) {
                    finish();
                    startActivity(new Intent(RoleSelectActivity.this, BusinessInfoActivity.class));
                }
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

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(getApplicationContext(), "User type: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }
}