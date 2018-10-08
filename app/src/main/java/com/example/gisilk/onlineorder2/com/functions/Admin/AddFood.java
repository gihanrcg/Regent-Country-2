package com.example.gisilk.onlineorder2.com.functions.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gisilk.onlineorder2.R;
import com.example.gisilk.onlineorder2.com.functions.OrderFood.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddFood extends AppCompatActivity {

    private int REQUEST_CODE = 1;
    private static final int Galley_Intent = 2;
    private ImageView liquorImage;
    private Button btnUpload;
    private StorageReference mStorageref;
    private Uri filepath;
    private String randomFileName, foodName, foodSize;
    private int foodPrice;
    private DatabaseReference databaseReference;
    private EditText fdName, fdSize, fdPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_management);

        btnUpload = findViewById(R.id.btn_submit);
        mStorageref = FirebaseStorage.getInstance().getReference();
        fdName = (EditText) findViewById(R.id.name);
        fdSize = (EditText) findViewById(R.id.size);
        fdPrice = (EditText) findViewById(R.id.price);
        liquorImage = (ImageView) findViewById(R.id.uploadImageView);

        liquorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent uploadimage = new Intent(Intent.ACTION_PICK);
//                uploadimage.setType("image/*");
//                startActivityForResult(uploadimage, Galley_Intent);

                Intent up = new Intent();
                up.setType("image/*");
                up.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(up,"Select image"),REQUEST_CODE);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(filepath !=  null){
                    final ProgressDialog progressDialog = new ProgressDialog(AddFood.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    randomFileName = UUID.randomUUID().toString();
                    StorageReference sref = mStorageref.child("Foods/" + randomFileName);
                    Log.i("regent","sref : " + sref);

                    sref.putFile(filepath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Log.i("regent","DONE");
                            foodName = fdName.getText().toString();
                            foodSize = fdSize.getText().toString();
                            foodPrice = Integer.parseInt(fdPrice.getText().toString());
                            databaseReference = FirebaseDatabase.getInstance().getReference("Hotel/Food");
                            Food fd = new Food(foodName, foodSize, foodPrice,true, randomFileName);
                            databaseReference.child(foodName).setValue(fd);
                            progressDialog.dismiss();
                            Toast.makeText(AddFood.this,"Done",Toast.LENGTH_SHORT);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(AddFood.this,"Failed",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = taskSnapshot.getBytesTransferred() /taskSnapshot.getTotalByteCount() * 100;
                            progressDialog.setMessage("Uploading "+(int)progress + "%" );
                        }
                    });
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null ) {
            try {
                filepath = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                liquorImage.setImageBitmap(bitmap);




                Toast.makeText(AddFood.this,"Selection Done",Toast.LENGTH_SHORT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(AddFood.this,"Selection Cancelled",Toast.LENGTH_SHORT);
        }

    }
}

