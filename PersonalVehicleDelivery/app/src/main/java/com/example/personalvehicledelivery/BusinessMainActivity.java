package com.example.personalvehicledelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.Date;

public class BusinessMainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    TextView textViewTitle;
    EditText editTextEmail;
    Spinner spinnerMenu;
    Button help_btn;
    TextView help_txt;
    ImageView comp_logo;

    private static final String USERS = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_main);
        help_btn = (Button)findViewById(R.id.help_button);
        help_txt = (TextView)findViewById(R.id.help_text);
        comp_logo = (ImageView)findViewById(R.id.comp_logo);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();

        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = mDatabase.child(USERS).child(uid);

        textViewTitle = findViewById(R.id.textViewTitle);
        spinnerMenu = findViewById(R.id.spinnerMenu);
        editTextEmail = findViewById(R.id.editTextEmail);

        help_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                help_txt.setVisibility((help_txt.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);
            }
        });


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String businessName = snapshot.child("business_name").getValue().toString();
                textViewTitle.setText("Welcome, " + businessName);
                if (businessName.equals("Chick-fil-A")) {
                    comp_logo.setImageResource(R.drawable.cfa);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(BusinessMainActivity.this,
                            R.array.cfa_menu, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerMenu.setAdapter(adapter);
                } else if (businessName.equals("Burger King")) {
                    comp_logo.setImageResource(R.drawable.burger_king_logo);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(BusinessMainActivity.this,
                            R.array.bk_menu, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerMenu.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);

        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = mAuth.getCurrentUser().getUid();
                String businessEmail = mAuth.getCurrentUser().getEmail();
                final String businessEmailKey = businessEmail.replace('.', ',');

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String businessName = snapshot.child("business owners")
                                .child(businessEmailKey)
                                .child("business_name").getValue().toString();
                        String businessAddress = snapshot.child("business owners")
                                .child(businessEmailKey)
                                .child("address").getValue().toString();
                        String customerEmail = editTextEmail.getText().toString();
                        String customerEmailKey = customerEmail.replace('.', ',');

                        String customerAddress = snapshot.child("customers")
                                .child(customerEmailKey)
                                .child("address").getValue().toString();
                        String item = spinnerMenu.getSelectedItem().toString();

                        Order order = new Order(customerEmail, customerAddress, item, businessAddress, businessName);

                        Date date = new Date();
                        long time = date.getTime();
                        String ts = new Timestamp(time).toString().substring(0, 19);

                        mDatabase.child("orders").child(ts).setValue(order);
                        Toast.makeText(getApplicationContext(), "Job added!", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                mDatabase.addListenerForSingleValueEvent(valueEventListener);
            }
        });


        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(BusinessMainActivity.this, MainActivity.class));
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