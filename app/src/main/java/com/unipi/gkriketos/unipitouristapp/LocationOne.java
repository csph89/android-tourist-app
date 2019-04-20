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

public class LocationOne extends AppCompatActivity {

    double lat, lon;
    private TextView textView2;
    private TextView textView4;
    private TextView textView7;
    private MyTextToSpeech myTts;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myTitleRef = database.getReference("locations/location_one/title");
    DatabaseReference myTypeRef = database.getReference("locations/location_one/type");
    DatabaseReference myInfoRef = database.getReference("locations/location_one/info");
//    DatabaseReference myLatRef = database.getReference("locations/location_one/point/lat");
//    DatabaseReference myLonRef = database.getReference("locations/location_one/point/long");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_one);

        Intent intent = getIntent();
        lat = intent.getDoubleExtra("latitude", 123);
        lon = intent.getDoubleExtra("longitude", 123);


        textView2 = findViewById(R.id.textView2);
        textView4 = findViewById(R.id.textView4);
        textView7 = findViewById(R.id.textView7);
        myTts = new MyTextToSpeech(this);

        myTitleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView2.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myTypeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView4.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        myInfoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView7.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void playInfo(View v) {
        myTts.mySpeak(textView7.getText().toString());
    }

    public void goToMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("latitude", lat);
        intent.putExtra("longitude", lon);
        startActivity(intent);
    }
}
