package com.buttons.forgetmenot;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int database_version=1;
    private static final String table_name = "PlantDatabase";
    private static final String key_id = "ID";
    private static final String key_name = "PlantName";
    private static final String key_desc = "Description";
    private static final String key_planted = "Planted";
    private static final String key_watered = "LastWatered";
    private static final String key_fed = "LastFed";
    private static final String key_image = "ImageLoc";
    private static final String key_feedInterval = "FeedInterval";
    private static final String key_waterInterval = "WaterInterval";
    private static final String key_favorite = "IsFavorite";
    private static final String key_waterHistory = "WaterHistory";
    private static final String key_foodHistory = "FoodHistory";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + table_name + "(" + key_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +key_name +" TEXT,"+
                key_desc +" TEXT,"+key_planted +" TEXT,"+key_watered +" TEXT,"+key_fed +" TEXT,"+key_image +" BLOB,"
                +key_feedInterval +" TEXT,"+key_waterInterval +" TEXT,"+ key_favorite +" TEXT,"+ key_waterHistory +" TEXT," +key_foodHistory +" TEXT"+   ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }

    public DatabaseHelper(Context context)
    {
        super(context,table_name,null,database_version);
    }

    public void save(Plant plant)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(key_name,plant.getPlantName());
        content.put(key_desc,plant.getDescription());
        content.put(key_planted,plant.getPlantDate());
        content.put(key_watered,plant.getLastWatered());
        content.put(key_fed,plant.getLastFed());
        content.put(key_image,plant.getImage());
        content.put(key_feedInterval,plant.getFeedInterval());
        content.put(key_waterInterval,plant.getWaterInterval());
        content.put(key_favorite,plant.getFavorite());
        content.put(key_waterHistory,plant.getWaterHistory());
        content.put(key_foodHistory,plant.getFoodHistory());

        db.insert(table_name,null,content);
        db.close();
    }

    public Plant findOne(String plantName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.query(table_name,new String[]{key_name,key_desc,key_planted,key_watered,key_fed,key_imageLoc,key_feedInterval,key_waterInterval},key_id+"=?",new String[]{String.valueOf(id)}, null, null, null);
        Cursor cursor  = db.rawQuery("SELECT * FROM "+ table_name +" WHERE "+key_name +"=?",new String[]{plantName+""});
        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        cursor.close();
        db.close();
        return new Plant(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getBlob(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11));
    }

    public List<Plant> getAll()
    {
        List<Plant> plantList = new ArrayList<Plant>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + table_name,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                Plant currentPlant = new Plant(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getBlob(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11));
                plantList.add(currentPlant);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return plantList;
    }

    //WARNING
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table_name);
        db.close();
    }
}
