package com.example.gisilk.onlineorder2.com.functions.Booking;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gisilk.onlineorder2.R;
import com.example.gisilk.onlineorder2.com.functions.OrderFood.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyBookings extends AppCompatActivity {

    private DatabaseReference dbRefForSpinner, dbFromSpinner;
    private Spinner dropdown;
    private boolean availability;
    private List<String> roomList;
    private ArrayAdapter<String> adapter;
    private Button updateButton, deleteButton;
    private Activity context;
    private ValueEventListener mSendEventListner;
    private EditText noOfRooms, noOfNights, roomType;
    private String spinnerValue, currentdate, current_uid;
    private Integer roomCount;
    private Integer nightCount;
    private Long availableRoomCount;
    private CalendarView calendarView ;
    private ValueEventListener valEvLisForSpinner, valEvLisFromSpinner;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        roomList = new ArrayList<>();
        dropdown = findViewById(R.id.spinner1);
        roomType = (EditText) findViewById(R.id.type);
        noOfRooms = (EditText) findViewById(R.id.noOfRooms);
        noOfNights = (EditText) findViewById(R.id.noofNights);
        updateButton = (Button) findViewById(R.id.btn_update);
        deleteButton = (Button) findViewById(R.id.btn_delete);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        current_uid = "u2MnKhvoF2TzdScRubamMsfXKBE3";
//        String current_uid = firebaseUser.getUid();

        dbRefForSpinner =  FirebaseDatabase.getInstance().getReference("Users/" + current_uid + "/Booking");

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

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                dbFromSpinner = FirebaseDatabase.getInstance().getReference("Users/" + current_uid + "/Booking/" + parentView.getSelectedItem());
                valEvLisFromSpinner = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Book bookDetails = dataSnapshot.getValue(Book.class);
                        roomType.setText(bookDetails.getRoomType(), TextView.BufferType.EDITABLE);
                        noOfRooms.setText(String.valueOf(bookDetails.getNoOfRooms()), TextView.BufferType.EDITABLE);
                        noOfNights.setText(String.valueOf(bookDetails.getNoOfNights()), TextView.BufferType.EDITABLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                };
                dbFromSpinner.addValueEventListener(valEvLisFromSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Log.i("booking", "parentView : " + parentView);
            }

        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        dbFromSpinner.addValueEventListener(valEvLisFromSpinner);
    }
}
