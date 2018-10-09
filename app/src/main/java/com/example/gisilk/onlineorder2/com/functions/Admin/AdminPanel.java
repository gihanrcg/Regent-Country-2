package com.example.gisilk.onlineorder2.com.functions.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gisilk.onlineorder2.R;

public class AdminPanel extends AppCompatActivity {

    Button btnLiquorManagemrnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);


        btnLiquorManagemrnt = findViewById(R.id.btn_liquor_management);

        btnLiquorManagemrnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AdminPanel.this,AddLiquor.class);
                startActivity(i);

            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
