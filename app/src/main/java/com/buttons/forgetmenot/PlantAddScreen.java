package com.buttons.forgetmenot;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class PlantAddScreen extends AppCompatActivity {

    Button add,cancel;
    ImageButton feed,water,imageButton;
    TextView plantName,plantDescription,plantDate,lastWatered,lastFed;
    String imageLoc;
    Spinner waterIntervalSpin, foodIntervalSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_add_screen);

        waterIntervalSpin = (Spinner) findViewById(R.id.waterIntervalSpin);
        foodIntervalSpin = (Spinner) findViewById(R.id.feedIntervalSpin);
        plantName = (TextView) findViewById(R.id.inputPlantName);
        plantDescription = (TextView) findViewById(R.id.inputPlantDescription);
        plantDate = (TextView) findViewById(R.id.plantingDate);
        lastWatered = (TextView) findViewById(R.id.lastWatered);
        lastFed = (TextView) findViewById(R.id.lastFed);

        //imagebutton here
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update imageLoc here
            }
        });

        //add button here
        add = (Button) findViewById(R.id.Add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Plant plantAdd = new Plant((String) plantName.getText(),(String) plantDate.getText(),(String) lastWatered.getText(),(String) lastFed.getText(),(String) plantDescription.getText(),
                        imageLoc,(String) foodIntervalSpin.getSelectedItem(),(String) waterIntervalSpin.getSelectedItem());
                DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                db.save(plantAdd);
                Context context = getApplicationContext();
                CharSequence text = "Added!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
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
                //update feed


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
                //update water



                Context context = getApplicationContext();
                CharSequence text = "Watered!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });
    }
}
