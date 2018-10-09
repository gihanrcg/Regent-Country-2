package com.example.gisilk.onlineorder2.com.functions.Booking;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gisilk.onlineorder2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class Booking extends AppCompatActivity {

    private DatabaseReference dbRefForSpinner, dbRefForPush;
    private Spinner dropdown;
    private boolean availability;
    private List<String> roomList;
    private ArrayAdapter<String> adapter;
    private Button buttonAvailability;
    private Activity context;
    private ValueEventListener mSendEventListner;
    private EditText noOfRooms, noofNights;
    private String spinnerValue, currentdate;
    private Integer roomCount;
    private Integer nightCount;
    private Long availableRoomCount;
    private CalendarView calendarView ;
    private ValueEventListener valEvLisForSpinner, valEvLisForPush;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        context = this;
        buttonAvailability = (Button) findViewById(R.id.btn_checkAvailability);
        noOfRooms = (EditText)findViewById(R.id.noOfRooms);
        noofNights = (EditText)findViewById(R.id.noofNights);
        roomList = new ArrayList<>();
        dropdown = findViewById(R.id.spinner1);
        dbRefForSpinner =  FirebaseDatabase.getInstance().getReference("Hotel/Rooms");

        valEvLisForSpinner = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("booking", "dataSnapshot : " + dataSnapshot);
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    roomList.add(ds.getKey());
                }
                setValuesToSpinner();
                dbRefForSpinner.removeEventListener(valEvLisForSpinner);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("booking", "error : " + databaseError);
            }
        };
        dbRefForSpinner.addValueEventListener(valEvLisForSpinner);

        buttonAvailability.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomCount = Integer.valueOf(noOfRooms.getText().toString());
                nightCount = Integer.valueOf(noofNights.getText().toString());
                spinnerValue = dropdown.getSelectedItem().toString();

                dbRefForSpinner =  FirebaseDatabase.getInstance().getReference("Hotel/Rooms/"+spinnerValue);
                valEvLisForPush = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.i("booking", "ds : " + dataSnapshot.child("availableRooms").getValue());
                        availableRoomCount = (Long) dataSnapshot.child("availableRooms").getValue();
                        if(availableRoomCount >= roomCount){
                            Log.i("booking", "ds : " + dataSnapshot.child("availableRooms").getValue());
                            firebaseAuth = FirebaseAuth.getInstance();
                            firebaseUser = firebaseAuth.getCurrentUser();
                            String current_uid = "u2MnKhvoF2TzdScRubamMsfXKBE3";
//                            String current_uid = firebaseUser.getUid();
                            Book bbb = new Book(spinnerValue, roomCount, nightCount, currentdate);

                            dbRefForPush = FirebaseDatabase.getInstance().getReference("Users/"+current_uid+"/Booking");
                            String pathID = dbRefForPush.push().getKey();
                            dbRefForPush.child(pathID).setValue(bbb);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "No Rooms Available", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.i("booking", "error : " + databaseError);
                    }
                };
                dbRefForSpinner.addValueEventListener(valEvLisForPush);
            }
        });

    }

    public void setValuesToSpinner(){
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roomList);
        dropdown.setAdapter(adapter);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        dbRefForSpinner.removeEventListener(valEvLisForPush);
    }
}
