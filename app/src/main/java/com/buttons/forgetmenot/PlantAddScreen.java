package com.buttons.forgetmenot;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Debug;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlantAddScreen extends AppCompatActivity {

    Button add,cancel;
    ImageButton imageButton;
    TextView plantName,plantDescription,plantDate,lastWatered,lastFed;
    String favorite="false";
    byte[] image;
    Spinner waterIntervalSpin, foodIntervalSpin;
    CheckBox checkbox;
    int dateWriteSelector,editPlantID;
    Boolean isEdit;

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
        imageButton = findViewById(R.id.imageButton);
        checkbox = findViewById(R.id.favorite);
        add = (Button) findViewById(R.id.add);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.intervals, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        final DatabaseHelper db = new DatabaseHelper(this);
        waterIntervalSpin.setAdapter(adapter);
        foodIntervalSpin.setAdapter(adapter);

        final Plant plantToEdit;

        Intent lastIntent = getIntent();
        if(lastIntent.getExtras()!=null)
        {
            String name = lastIntent.getStringExtra("edit");
            plantToEdit=db.findOne(name);
            db.close();

            //TODO: make custom selection better, and actually custom
            int spinnerPosition1 = adapter.getPosition(plantToEdit.getFeedInterval());
            foodIntervalSpin.setSelection(spinnerPosition1);
            int spinnerPosition2 = adapter.getPosition(plantToEdit.getWaterInterval());
            waterIntervalSpin.setSelection(spinnerPosition2);

            plantName.setText(plantToEdit.getPlantName());
            plantDescription.setText(plantToEdit.getDescription());
            lastWatered.setText(plantToEdit.getLastWatered());
            lastFed.setText(plantToEdit.getLastFed());
            plantDate.setText(plantToEdit.getPlantDate());

            image = plantToEdit.getImage();
            Bitmap img = BitmapFactory.decodeByteArray(image,0,image.length);
            imageButton.setImageBitmap(Bitmap.createScaledBitmap(img,(int)dipToPixels(this,120),(int)dipToPixels(this,120),false));

            String isFav = plantToEdit.getFavorite();
            if(isFav.equals("true"))
                checkbox.setSelected(true);
            else
                checkbox.setSelected(false);

            add.setText(R.string.save);
            isEdit=true;
            editPlantID = plantToEdit.getID();
        }
        else
            isEdit=false;
        //Dates
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //TODO: make date format selectable from settings
                String format = "dd.MM.yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.ENGLISH);

                switch(dateWriteSelector)
                {
                    case PICK_PLANTING_DATE:{
                        plantDate.setText(sdf.format(myCalendar.getTime()));
                        break;
                    }
                    case PICK_FOOD_DATE:{
                        lastFed.setText(sdf.format(myCalendar.getTime()));
                        break;
                    }
                    case PICK_WATER_DATE:{
                        lastWatered.setText(sdf.format(myCalendar.getTime()));
                        break;
                    }
                }
            }
        };
        plantDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PlantAddScreen.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                dateWriteSelector = PICK_PLANTING_DATE;
            }
        });
        lastFed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PlantAddScreen.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                dateWriteSelector = PICK_FOOD_DATE;
            }
        });
        lastWatered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PlantAddScreen.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                dateWriteSelector = PICK_WATER_DATE;
            }
        });

        //image button here
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPickPhoto = new Intent();
                toPickPhoto.setType("image/*");
                toPickPhoto.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(toPickPhoto, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        //checkbox here
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();

                if(v.getId() == R.id.favorite)
                {
                    if(checked)
                        favorite="true";
                    else
                        favorite="false";
                }
                Log.d("URGENT","Checkbox clicked, favorite="+favorite);
            }
        });

        //add button here
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name,date,lastWater,lastFood,desc,foodSpin,waterSpin;

                List<Plant> plantList = db.getAll();
                name = "" + plantName.getText();
                date = "" + plantDate.getText();
                lastWater = "" + lastWatered.getText();
                lastFood = "" + lastFed.getText();
                desc = "" + plantDescription.getText();
                foodSpin = "" + foodIntervalSpin.getSelectedItem();
                waterSpin = "" + waterIntervalSpin.getSelectedItem();

                if(name.isEmpty())
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Name cannot be empty!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    if(!isEdit) {
                        boolean ok = true;
                        for (Plant thisPlant : plantList) {
                            if (thisPlant.getPlantName().equals(name)) {
                                Context context = getApplicationContext();
                                CharSequence text = "Name must be unique!";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                ok = false;
                                break;
                            }
                        }
                        if (ok) {
                            if (date.isEmpty())
                                date = "Unknown";
                            if (lastWater.isEmpty())
                                lastWater = "Unknown";
                            if (lastFood.isEmpty())
                                lastFood = "Unknown";
                            if (desc.isEmpty())
                                desc = "No notes";

                            Plant plantAdd = new Plant(name, desc, date, lastWater, lastFood, image, foodSpin, waterSpin, favorite, lastWater, lastFood);
                            db.save(plantAdd);

                            Context context = getApplicationContext();
                            CharSequence text = "Added!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            db.close();
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                    else
                    {
                        boolean ok=true;
                        for(Plant plant:plantList)
                        {
                            if(plant.getPlantName().equals(name) && plant.getID()!=editPlantID)
                            {
                                Context context = getApplicationContext();
                                CharSequence text = "There's another plant with the same name!";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                ok = false;
                                break;
                            }
                        }
                        if(ok)
                        {
                            Plant plantEdited = new Plant(editPlantID,name, desc, date, lastWater, lastFood, image, foodSpin, waterSpin, favorite, lastWater, lastFood);
                            db.update(plantEdited);
                            db.close();
                            Context context = getApplicationContext();
                            CharSequence text = "Saved!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                }
            }
        });

        //cancel button here
        cancel = (Button) findViewById(R.id.Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_PLANTING_DATE = 2;
    private static final int PICK_WATER_DATE = 3;
    private static final int PICK_FOOD_DATE = 4;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap,(int)dipToPixels(this,150),(int)dipToPixels(this,150),false);
                imageButton.setImageBitmap(scaled);
                image = getBitmapAsByteArray(scaled);
            }
            catch (Exception e)
            {
                error();
            }
        }
    }

    void error()
    {
        Context context = getApplicationContext();
        CharSequence text = "Something went wrong!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}
