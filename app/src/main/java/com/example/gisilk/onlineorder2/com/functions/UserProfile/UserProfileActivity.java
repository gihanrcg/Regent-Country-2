package com.example.gisilk.onlineorder2.com.functions.UserProfile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gisilk.onlineorder2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserProfileActivity extends AppCompatActivity {

    private static final String TAG = UserProfileActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputName, inputEmail,inputMobile,inputNic,inputAddress;
    private Button btnSave;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        txtDetails = (TextView) findViewById(R.id.txt_user);
        inputName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputMobile = (EditText) findViewById(R.id.mobileNo);
        inputNic = (EditText) findViewById(R.id.nic);
        inputAddress = (EditText)findViewById(R.id.address);
        btnSave = (Button) findViewById(R.id.btn_save);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        // Save / update the user
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String mobile = inputMobile.getText().toString();
                String nic = inputNic.getText().toString();
                String address = inputAddress.getText().toString();

                // Check for already existed userId
                if (TextUtils.isEmpty(userID)) {
                    createUser(name, email,mobile,nic,address);
                } else {
                    updateUser(name, email,mobile,nic,address);
                }
            }
        });

        toggleButton();
    }

    // Changing button text
    private void toggleButton() {
        if (TextUtils.isEmpty(userID)) {
            btnSave.setText("Save");
        } else {
            btnSave.setText("Update");
        }
    }

    /**
     * Creating new user node under 'users'
     */
    private void createUser(String name, String email,String mobile,String nic,String address) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userID)) {
            userID = mFirebaseDatabase.push().getKey();
        }

        User user = new User(name, email,mobile,nic,address);

        mFirebaseDatabase.child(userID).setValue(user);

        addUserChangeListener();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + user.name + ", " + user.email);

                // Display newly updated name and email
                txtDetails.setText(user.name + ", " + user.email);

                // clear edit text
                inputEmail.setText("");
                inputName.setText("");
                inputMobile.setText("");
                inputAddress.setText("");
                inputNic.setText("");

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(String name, String email,String mobile,String nic,String address) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(userID).child("name").setValue(name);

        if (!TextUtils.isEmpty(email))
            mFirebaseDatabase.child(userID).child("email").setValue(email);

        if(!TextUtils.isEmpty(mobile))
            mFirebaseDatabase.child(userID).child("mobile").setValue(mobile);

        if(!TextUtils.isEmpty(nic))
            mFirebaseDatabase.child(userID).child("nic").setValue(nic);
        if(!TextUtils.isEmpty(address))
            mFirebaseDatabase.child(userID).child("address").setValue(address);
    }
}
