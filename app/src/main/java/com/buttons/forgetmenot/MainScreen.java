package com.buttons.forgetmenot;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainScreen extends AppCompatActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        ConstraintLayout cardSpace = (ConstraintLayout) findViewById(R.id.CLayout);

        db = new DatabaseHelper(getApplicationContext());
        List<Plant> plantList = db.getAll();
        Integer counter = 0;

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View myView = inflater.inflate(R.layout.Card, null,false);
        cardSpace.addView(myView);


        Context context = getApplicationContext();
        CharSequence text = "Should have rendered!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        /*
        for (Plant plant:plantList) {
            createCard(plant,cardSpace,counter);
            counter++;
        }
        */
    }

    private void createCard(Plant currentPlant, ConstraintLayout space, Integer ct)
    {

    }
}
