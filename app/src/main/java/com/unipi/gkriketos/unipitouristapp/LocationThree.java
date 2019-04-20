package com.unipi.gkriketos.unipitouristapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LocationThree extends AppCompatActivity {

    double lat,lon;
    private TextView textView12;
    private TextView textView14;
    private TextView textView16;
    private MyTextToSpeech myTts;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myTitleRef = database.getReference("locations/location_three/title");
    DatabaseReference myTypeRef = database.getReference("locations/location_three/type");
    DatabaseReference myInfoRef = database.getReference("locations/location_three/info");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_three);

        Intent intent = getIntent();
        lat = intent.getDoubleExtra("latitude", 123);
        lon = intent.getDoubleExtra("longitude", 123);


        textView12 = findViewById(R.id.textView12);
        textView14 = findViewById(R.id.textView14);
        textView16 = findViewById(R.id.textView16);
        myTts = new MyTextToSpeech(this);

        myTitleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView12.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myTypeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView14.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        myInfoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView16.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void playInfo(View view) {
        myTts.mySpeak(textView16.getText().toString());
    }

    public void goToMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("latitude", lat);
        intent.putExtra("longitude", lon);
        startActivity(intent);
    }

}
