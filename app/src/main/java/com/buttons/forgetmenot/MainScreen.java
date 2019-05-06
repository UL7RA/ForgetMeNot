package com.buttons.forgetmenot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainScreen extends AppCompatActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        db = new DatabaseHelper(getApplicationContext());
        List<Plant> plantList = db.getAll();
        for (Plant plant:plantList) {

        }
    }
}
