package com.buttons.forgetmenot;

import android.Manifest;
import android.content.ClipData;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.util.List;

public class MainScreen extends AppCompatActivity {

    DatabaseHelper db;
    static CardClick listener;
    private static List<Plant> plantList;
    CardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent sendToPermissions = new Intent(this,PermissionsScreen.class);
            startActivity(sendToPermissions);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //render screen
        db = new DatabaseHelper(getApplicationContext());
        plantList = db.getAll();

        if(plantList.isEmpty())
        {
            //empty database
            ConstraintLayout mainLayout = findViewById(R.id.mainLayout);
            TextView databaseEmptyText = new TextView(getApplicationContext());
            databaseEmptyText.setText(getString(R.string.nothing));
            databaseEmptyText.setTextSize(32);
            databaseEmptyText.setGravity(Gravity.CENTER);
            ConstraintLayout.LayoutParams emptyTextParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.MATCH_PARENT);
            emptyTextParams.setMargins(32,128,32,128);
            databaseEmptyText.setLayoutParams(emptyTextParams);
            databaseEmptyText.setTextColor(Color.BLACK);
            mainLayout.addView(databaseEmptyText);
        }
        else {
            //not empty
            RecyclerView recycler = findViewById(R.id.recycler);
            recycler.setHasFixedSize(false);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recycler.setLayoutManager(layoutManager);
            listener = new CardClick();
            adapter = new CardAdapter(plantList);
            recycler.setAdapter(adapter);
        }
        //end render
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.settings,menu);
        //getMenuInflater().inflate(R.);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.delete_all:
            {
                db.deleteAll();
                recreate();
                return true;
            }
            case R.id.action_add:
            {
                Intent intent = new Intent(getApplicationContext(),PlantAddScreen.class);
                startActivityForResult(intent,ADD_NEW);
                return true;
            }
            default:
            {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private static final int ADD_NEW = 0;
    private static final int EDIT = 1;

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data){
        if(resultCode == RESULT_OK)
            recreate();
    }

    private class CardClick implements View.OnClickListener{

        public void onClick(View v)
        {
            //TODO: add card stuff to do here
            Intent intent = new Intent(getApplicationContext(),PlantAddScreen.class);
            TextView tw = v.findViewById(R.id.plantName);
            intent.putExtra("edit",tw.getText());
            startActivityForResult(intent,EDIT);
        }
    }
}
