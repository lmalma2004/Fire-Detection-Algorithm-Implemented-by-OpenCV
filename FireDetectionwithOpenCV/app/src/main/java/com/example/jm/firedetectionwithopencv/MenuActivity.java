package com.example.jm.firedetectionwithopencv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

/**
 * Created by JM on 2017-10-01.
 */

public class MenuActivity extends Activity{
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, Showapp.class));
        setContentView(R.layout.activity_menu);

        ImageButton imageButton1 = (ImageButton)findViewById(R.id.imageButton1);
        ImageButton imageButton2 = (ImageButton)findViewById(R.id.imageButton2);
        ImageButton imageButton3 = (ImageButton)findViewById(R.id.imageButton3);
        ImageButton imageButton4 = (ImageButton)findViewById(R.id.imageButton4);

    }
    public void CCTVbutton(View view){
        Intent intent = new Intent(MenuActivity.this,MainActivity.class);
        startActivity(intent);
    }
    public void Firebutton(View view){
        Intent intent = new Intent(MenuActivity.this,FireExtinguisher.class);
        startActivity(intent);
    }
    public void Emergencybutton(View view){
        Intent intent = new Intent(MenuActivity.this,EmergencyActivity.class);
        startActivity(intent);
    }
    public void CPRbutton(View view){
        Intent intent = new Intent(MenuActivity.this,CprActivity.class);
        startActivity(intent);
    }


}
