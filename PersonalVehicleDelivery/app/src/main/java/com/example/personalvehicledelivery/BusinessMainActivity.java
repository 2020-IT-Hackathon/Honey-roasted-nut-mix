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
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BusinessMainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    TextView textViewTitle;
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
        mDatabase = database.getReference(USERS);

        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = mDatabase.child(uid);

        textViewTitle = findViewById(R.id.textViewTitle);
        spinnerMenu = findViewById(R.id.spinnerMenu);

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