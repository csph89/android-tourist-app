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

public class LocationFour extends AppCompatActivity {

    double lat, lon;
    private TextView textView17;
    private TextView textView19;
    private TextView textView21;
    private MyTextToSpeech myTts;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myTitleRef = database.getReference("locations/location_four/title");
    DatabaseReference myTypeRef = database.getReference("locations/location_four/type");
    DatabaseReference myInfoRef = database.getReference("locations/location_four/info");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_four);

        Intent intent = getIntent();
        lat = intent.getDoubleExtra("latitude", 123);
        lon = intent.getDoubleExtra("longitude", 123);


        textView17 = findViewById(R.id.textView17);
        textView19 = findViewById(R.id.textView19);
        textView21 = findViewById(R.id.textView21);
        myTts = new MyTextToSpeech(this);

        myTitleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView17.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myTypeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView19.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        myInfoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView21.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void playInfo(View view) {
        myTts.mySpeak(textView21.getText().toString());
    }

    public void goToMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("latitude", lat);
        intent.putExtra("longitude", lon);
        startActivity(intent);
    }

}
