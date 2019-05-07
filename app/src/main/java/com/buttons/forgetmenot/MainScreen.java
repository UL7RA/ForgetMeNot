package com.buttons.forgetmenot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainScreen extends AppCompatActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            //TODO: Send to permissions screen
            Intent sendToPermissions = new Intent(this,PermissionsScreen.class);
            startActivityForResult(sendToPermissions,0);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        FloatingActionButton addButton = findViewById(R.id.addButton);
        FloatingActionButton settingsButton = findViewById(R.id.settingsButton);

        //add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start add activity
                Intent intent = new Intent(getApplicationContext(),PlantAddScreen.class);
                //intent.putExtra("Type","Add");
                startActivityForResult(intent,0);
            }
        });
        //settings button
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete all, debug
                db.deleteAll();
                recreate();
            }
        });


        //render screen
        db = new DatabaseHelper(getApplicationContext());
        List<Plant> plantList = db.getAll();

        LinearLayout cardSpace = (LinearLayout) findViewById(R.id.mainConstraint);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        if(plantList.isEmpty())
        {
            //empty database
            TextView databaseEmptyText = new TextView(getApplicationContext());
            databaseEmptyText.setText(getString(R.string.nothing));
            databaseEmptyText.setTextSize(32);
            databaseEmptyText.setGravity(Gravity.CENTER);
            ConstraintLayout.LayoutParams emptyTextParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.MATCH_PARENT);
            emptyTextParams.setMargins(32,128,32,128);
            databaseEmptyText.setLayoutParams(emptyTextParams);
            databaseEmptyText.setTextColor(Color.WHITE);
            cardSpace.addView(databaseEmptyText);
        }
        else {
            //not empty
            for (Plant plant : plantList) {
                View currentCard = inflater.inflate(R.layout.card, null, false);

                File imageFile = new File(plant.getImageLocation());
                if (imageFile.exists()) {
                    Bitmap img = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    ImageView plantImg = currentCard.findViewById(R.id.plantPic);
                    //TODO: make image smaller, crashes app ?
                    plantImg.setImageBitmap(img);
                } else {
                    Log.d("HELP", "Can't find image!");
                    Log.d("LOCATION", plant.getImageLocation());
                }

                TextView plantNameShow = (TextView) currentCard.findViewById(R.id.plantName);
                plantNameShow.setText(plant.getPlantName());
                TextView plantWaterShow = (TextView) currentCard.findViewById(R.id.lastWatered);
                plantWaterShow.setText(plant.getLastWatered());
                TextView plantFoodShow = (TextView) currentCard.findViewById(R.id.lastFed);
                plantFoodShow.setText(plant.getLastFed());

                cardSpace.addView(currentCard);

                currentCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //start detailed activity
                    }
                });
            }
        }
        //end render
    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data){
        recreate();
    }

}
