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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Booking extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private Spinner dropdown;
    private boolean availability;
    private List<String> roomList;
    private ArrayAdapter<String> adapter;
    private Button buttonAvailability;
    private Activity context;
    private ValueEventListener mSendEventListner;
    private EditText noOfRooms, noofNights;
    private String spinnerValue;
    private Integer roomCount;
    private Integer nightCount;
    private Long availableRoomCount;
    private CalendarView calendarView ;

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
        databaseReference =  FirebaseDatabase.getInstance().getReference("Hotel/Rooms");

        buttonAvailability.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomCount = Integer.valueOf(noOfRooms.getText().toString());
                nightCount = Integer.valueOf(noofNights.getText().toString());
                spinnerValue = dropdown.getSelectedItem().toString();
                CalendarView cv = (CalendarView)findViewById(R.id.calendarView);
                Calendar c = Calendar.getInstance();
                SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date();
                final String currentdate= ss.format(cv);
                Log.i("booking", "currentDate : " +currentdate);

                databaseReference =  FirebaseDatabase.getInstance().getReference("Hotel/Rooms/"+spinnerValue);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.i("booking", "ds : " + dataSnapshot.child("availableRooms").getValue());
                        availableRoomCount = (Long) dataSnapshot.child("availableRooms").getValue();
                        if(availableRoomCount >= roomCount){
                            Log.i("booking", "ds : " + dataSnapshot.child("availableRooms").getValue());

                            Book bbb = new Book(spinnerValue, roomCount, nightCount, currentdate, currentdate);
                            databaseReference =  FirebaseDatabase.getInstance().getReference();
                            String pathID = databaseReference.child("Users").child("qweqweqweqwe").push().getKey();
                            databaseReference.child("Users").child("qweqweqweqwe").child(pathID).setValue(bbb);



                        }
                        else{
                            Toast.makeText(getApplicationContext(), "No Rooms Available", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.i("booking", "error : " + error);
                    }
                });
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    roomList.add(ds.getKey());
                }
                setValuesToSpinner();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("booking", "error : " + error);
            }
        });

    }

    public void setValuesToSpinner(){
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roomList);
        dropdown.setAdapter(adapter);
    }
}
