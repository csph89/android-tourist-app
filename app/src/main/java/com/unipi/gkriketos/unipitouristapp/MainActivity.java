package com.unipi.gkriketos.unipitouristapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements LocationListener {

    private boolean flag1 = true;
    private boolean flag2 = true;
    private boolean flag3 = true;
    private boolean flag4 = true;
    private Float userRadius;
    private EditText editText;
    private LocationManager locationManager;
    private Location location1;
    private Location location2;
    private Location location3;
    private Location location4;

    SQLiteDatabase myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.editText);

        myDB = openOrCreateDatabase("MyEventsDB", MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS `EVENTS` (" +
                "`timestamp` TEXT," +
                "`poi` TEXT," +
                "`latitude` TEXT," +
                "`longitude` TEXT," +
                "PRIMARY KEY(`timestamp`)" +
                "); ");

        location1 = new Location("location_one");
        setCoordinates(location1, 38.047138, 23.765546); //Madeira - Neo Hrakleio
        location2 = new Location("location_two");
        setCoordinates(location2, 38.042349, 23.751684); //Archontaraki - Nea Ionia
        location3 = new Location("location_three");
        setCoordinates(location3, 38.049977, 23.764060); //Tria Asteria - Nea Ionia
        location4 = new Location("location_four");
        setCoordinates(location4, 38.035467, 23.775124); //Meat O' Clock - Alsoupolh

        //To obtain an object of the LocationManager class, we call getSystemService()
        //method with the argument LOCATION_SERVICE in order to control location updates.
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
    }

    public void startGuide(View view) {
        //Kata th diadikasia enarkshs tou odhgou mas tha prepei na zhththei adeia apo ton xrhsth
        //wste na mporei h efarmogh na exei prosvash sthn topothesia ths suskeuhs.
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //H parakatw methodos tha ksekinhsei ena neo activity to opoio tha parotrunei ton xrhsth na dwsei thn katallhlh adeia
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            //Amesws meta tha ginei klhsh sthn onRequestPermissionsResult() sthn opoia tha staloun oi epiloges tou xrhsth
            //sxetika me to poies adeies paraxwrhse sthn efarmogh kai poies oxi.
        } else {
            //Tha xreiastei na ginei elegxos ths eisodou plhktrologhse o xrhsths.
            try {
                if(editText.getText().toString().equals(""))
                    Toast.makeText(this, "Please give a radius", Toast.LENGTH_SHORT).show();
                else {
                    userRadius = Float.parseFloat(editText.getText().toString());
                    Toast.makeText(this, "radius = " + userRadius, Toast.LENGTH_SHORT).show();
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                }
            } catch (Exception e) {
                editText.setText("");
                Toast.makeText(this, "Radius must be a number", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            startGuide(null);
        else
            Toast.makeText(this, "You have to press allow in order to start the Tourist Guide", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location1.distanceTo(location) <= userRadius) {
            if(flag1) {
                Toast.makeText(this, "You are close to Location1", Toast.LENGTH_SHORT).show();
                insertData(location1,"Madeira");
                Intent intent = new Intent(this, LocationOne.class);
                intent.putExtra("latitude", location1.getLatitude());
                intent.putExtra("longitude", location1.getLongitude());
                createNotification(intent, "You are close to Location1", 1, "Location1");
                flag1 = false;
            }
        } else {
            flag1 = true;
        }

        if (location2.distanceTo(location) <= userRadius) {
            if(flag2) {
                Toast.makeText(this, "You are close to Location2", Toast.LENGTH_SHORT).show();
                insertData(location2,"Archontaraki");
                Intent intent = new Intent(this, LocationTwo.class);
                intent.putExtra("latitude", location2.getLatitude());
                intent.putExtra("longitude", location2.getLongitude());
                createNotification(intent, "You are close to Location2", 2, "Location2");
                flag2 = false;
            }
        } else {
            flag2 = true;
        }

        if (location3.distanceTo(location) <= userRadius) {
            if(flag3) {
                Toast.makeText(this, "You are close to Location3", Toast.LENGTH_SHORT).show();
                insertData(location3,"Three Stars Cinema");
                Intent intent = new Intent(this, LocationThree.class);
                intent.putExtra("latitude", location3.getLatitude());
                intent.putExtra("longitude", location3.getLongitude());
                createNotification(intent, "You are close to Location3", 3, "Location3");
                flag3 = false;
            }
        } else {
            flag3 = true;
        }

        if (location4.distanceTo(location) <= userRadius) {
            if(flag4) {
                Toast.makeText(this, "You are close to Location4", Toast.LENGTH_SHORT).show();
                insertData(location4,"Meat O-Clock");
                Intent intent = new Intent(this, LocationFour.class);
                intent.putExtra("latitude", location4.getLatitude());
                intent.putExtra("longitude", location4.getLongitude());
                createNotification(intent, "You are close to Location4", 4, "Location4");
                flag4 = false;
            }
        } else {
            flag4 = true;
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void setCoordinates(Location location, double lat, double lon) {
        location.setLatitude(lat);
        location.setLongitude(lon);
    }

    public void insertData(Location location, String POI) {
        myDB.execSQL("INSERT OR IGNORE INTO 'EVENTS' VALUES (strftime('%Y-%m-%d %H:%M:%S','now','localtime')" +
                ",'" + POI +
                "','" + String.valueOf(location.getLatitude()) +
                "','" + String.valueOf(location.getLongitude()) +
                "');");
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }

    public void getData(View view) {
        StringBuffer buffer = new StringBuffer();
        Cursor cursor = myDB.rawQuery("SELECT * FROM EVENTS;", null);
        if(cursor.getCount() == 0) //An den exei katholou eggrafes h vash mas.
            Toast.makeText(this, "No records found!", Toast.LENGTH_LONG).show();
        else {
            while (cursor.moveToNext()) {
                buffer.append("Time: " + cursor.getString(0) + "\n");
                buffer.append("POI: " + cursor.getString(1) + "\n");
                buffer.append("Latitude: " + cursor.getString(2) + "\n");
                buffer.append("Longitude: " + cursor.getString(3) + "\n");
                buffer.append("================\n");
            }
            String s = buffer.toString();
            showMessage("Records", s);
        }
        cursor.close();
    }


    public void createNotification(Intent intent, String title, int code, String chanelId) {

        //NotificationManager: Class to notify the user of events that happen. This is how you tell the user that something has happened in the background.
        //NotificationManagerCompat: Compatibility library for NotificationManager with fallbacks for older platforms.
        NotificationManagerCompat myManager = NotificationManagerCompat.from(this); //This is how we get a NotificationManagerCompat instance for a provided context.

        //Notification.Builder: Builder class for notification objects. Provides a convenient way to set the various fields of a Notification.
        NotificationCompat.Builder myNotification = new NotificationCompat.Builder(this, chanelId);

        //Here we are designing a basic notification
        myNotification.setContentTitle("1 new POI");
        myNotification.setContentText(title);
        myNotification.setSmallIcon(R.drawable.ic_stat_add_location);

        //PendingIntent: A description of an Intent and target action to perform with it.
        //Instances of this class are created with getActivity method among others.
        //We have to put the intent inside the pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, code, intent, 0);

        //We give the pendingIntent to our notification
        myNotification.setContentIntent(pendingIntent);

        //One more thing we need to set in my notification is, every time we click on the notification, is to make sure
        //that our notification will go away once we interact with it.
        myNotification.setAutoCancel(true);

        //Then we have to notify our notification manager
        myManager.notify(code, myNotification.build());
    }

}
