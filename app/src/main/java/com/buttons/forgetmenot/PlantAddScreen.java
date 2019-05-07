package com.buttons.forgetmenot;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PlantAddScreen extends AppCompatActivity {

    Button add,cancel;
    ImageButton feed,water,imageButton;
    TextView plantName,plantDescription,plantDate,lastWatered,lastFed;
    String imageLoc = "Default";
    Spinner waterIntervalSpin, foodIntervalSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_add_screen);

        //spinners
        waterIntervalSpin = (Spinner) findViewById(R.id.waterIntervalSpin);
        foodIntervalSpin = (Spinner) findViewById(R.id.feedIntervalSpin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.intervals,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        waterIntervalSpin.setAdapter(adapter);
        foodIntervalSpin.setAdapter(adapter);

        plantName = (TextView) findViewById(R.id.inputPlantName);
        plantDescription = (TextView) findViewById(R.id.inputPlantDescription);
        plantDate = (TextView) findViewById(R.id.plantingDate);
        lastWatered = (TextView) findViewById(R.id.lastWatered);
        lastFed = (TextView) findViewById(R.id.lastFed);

        //TODO: dates calendar



        //image button here
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });


        final DatabaseHelper db = new DatabaseHelper(this);

        //add button here
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name,date,lastWater,lastFood,desc,foodSpin,waterSpin;
                name = "" + plantName.getText();
                date = "" + plantDate.getText();
                lastWater = "" + lastWatered.getText();
                lastFood = "" + lastFed.getText();
                desc = "" + plantDescription.getText();
                foodSpin = "" + foodIntervalSpin.getSelectedItem();
                waterSpin = "" + waterIntervalSpin.getSelectedItem();

                Plant plantAdd = new Plant(name,date,lastWater,lastFood,desc,imageLoc,foodSpin,waterSpin);
                db.save(plantAdd);

                Context context = getApplicationContext();
                CharSequence text = "Added!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                db.close();
                finish();
            }
        });

        //cancel button here
        cancel = (Button) findViewById(R.id.Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //feed button here
        feed = (ImageButton) findViewById(R.id.foodButton);
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: update feed


                Context context = getApplicationContext();
                CharSequence text = "Fertilized!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        //water button here
        water = (ImageButton) findViewById(R.id.waterButton);
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: update water



                Context context = getApplicationContext();
                CharSequence text = "Watered!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });
    }
    public static final int PICK_IMAGE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode) {
            case PICK_IMAGE:{
                //TODO: update image location
                    Uri selectedImage = data.getData();
                    imageButton.setImageURI(selectedImage);
                    imageLoc = selectedImage.getLastPathSegment();
                    imageLoc = imageLoc.replace("raw:","");
                    break;
                //recreate();
            }
            case RESULT_CANCELED : break;
        }
    }
}
