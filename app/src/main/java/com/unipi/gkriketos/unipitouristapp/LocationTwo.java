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

public class LocationTwo extends AppCompatActivity {

    double lat, lon;
    private TextView textView5;
    private TextView textView9;
    private TextView textView11;
    private MyTextToSpeech myTts;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myTitleRef = database.getReference("locations/location_two/title");
    DatabaseReference myTypeRef = database.getReference("locations/location_two/type");
    DatabaseReference myInfoRef = database.getReference("locations/location_two/info");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_two);

        Intent intent = getIntent();
        lat = intent.getDoubleExtra("latitude", 123);
        lon = intent.getDoubleExtra("longitude", 123);


        textView5 = findViewById(R.id.textView5);
        textView9 = findViewById(R.id.textView9);
        textView11 = findViewById(R.id.textView11);
        myTts = new MyTextToSpeech(this);

        myTitleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView5.setText(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myTypeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView9.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        myInfoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView11.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void playInfo(View v) {
        myTts.mySpeak(textView11.getText().toString());
    }

    public void goToMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("latitude", lat);
        intent.putExtra("longitude", lon);
        startActivity(intent);
    }

}
