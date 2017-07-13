package com.self_employed.stase.universityhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home_Screen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);

    }

    public void buttonClicked(View v){
        int id = v.getId();

        switch (id){
            case R.id.button1:
                Intent myIntent = new Intent(Home_Screen.this, Semester_Gpa.class);
                startActivity(myIntent);
        }
    }
}
