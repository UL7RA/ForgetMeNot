package com.buttons.forgetmenot;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainScreen extends AppCompatActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        FloatingActionButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start add activity
                Intent intent = new Intent(getApplicationContext(),PlantAddScreen.class);
                intent.putExtra("Type","Add");
                startActivity(intent);
                recreate();
            }
        });

        db = new DatabaseHelper(getApplicationContext());
        List<Plant> plantList = db.getAll();

        LinearLayout cardSpace = (LinearLayout) findViewById(R.id.mainConstraint);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        if(plantList.isEmpty())
        {
            TextView databaseEmptyText = new TextView(getApplicationContext());
            databaseEmptyText.setText(getString(R.string.nothing));
            databaseEmptyText.setTextSize(32);
            cardSpace.addView(databaseEmptyText);
        }
        else {
            for (Plant plant : plantList) {
                View currentCard = inflater.inflate(R.layout.card,null,false);



                cardSpace.addView(currentCard);
            }
        }
    }
}
