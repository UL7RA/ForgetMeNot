package com.buttons.forgetmenot;

import android.database.sqlite.SQLiteDatabase;

public class Plant {
    private int ID;
    private String plantName;
    private String plantDate;
    private String lastWatered;
    private String lastFed;
    private String description;
    private byte[] image;
    private String feedInterval;
    private String waterInterval;

    private String favorite;
    private String waterHistory;
    private String foodHistory;

    public Plant(int ID,String plantName,String description,String plantDate,String lastWatered,String lastFed,byte[] image, String feedInterval, String waterInterval,String favorite, String waterHistory, String foodHistory)
    {
        this.ID=ID;
        this.plantDate=plantDate;
        this.plantName=plantName;
        this.lastFed=lastFed;
        this.lastWatered=lastWatered;
        this.description=description;
        this.image=image;
        this.feedInterval=feedInterval;
        this.waterInterval=waterInterval;
        this.favorite=favorite;
        this.waterHistory=waterHistory;
        this.foodHistory=foodHistory;
    }
    public Plant(String plantName,String description,String plantDate,String lastWatered,String lastFed,byte[] image, String feedInterval, String waterInterval,String favorite, String waterHistory, String foodHistory)
    {
        this.plantDate=plantDate;
        this.plantName=plantName;
        this.lastFed=lastFed;
        this.lastWatered=lastWatered;
        this.description=description;
        this.image=image;
        this.feedInterval=feedInterval;
        this.waterInterval=waterInterval;
        this.favorite=favorite;
        this.waterHistory=waterHistory;
        this.foodHistory=foodHistory;
    }

    public String getPlantName()
    {
        return plantName;
    }

    public void setPlantName(String plantName)
    {
        this.plantName=plantName;
    }

    public String getPlantDate()
    {
        return plantDate;
    }

    public void setPlantDate(String plantDate)
    {
        this.plantDate=plantDate;
    }

    public String getLastWatered()
    {
        return lastWatered;
    }

    public void setLastWatered(String lastWatered)
    {
        this.lastWatered=lastWatered;
    }

    public String getLastFed()
    {
        return lastFed;
    }

    public void setLastFed(String lastFed)
    {
        this.lastFed=lastFed;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description=description;
    }

    public byte[] getImage()
    {
        return image;
    }

    public void setImage(String imagePath)
    {
        this.image=image;
    }

    public String getFeedInterval()
    {
        return feedInterval;
    }

    public void setFeedInterval(String feedInterval)
    {
        this.feedInterval=feedInterval;
    }

    public String getWaterInterval()
    {
        return waterInterval;
    }

    public void setWaterInterval(String waterInterval)
    {
        this.waterInterval=waterInterval;
    }

    public String getFavorite()
    {
        return favorite;
    }

    public void setFavorite(String favorite)
    {
        this.favorite=favorite;
    }

    public String getFoodHistory() {
        return foodHistory;
    }

    public void setFoodHistory(String foodToAdd)
    {
        foodHistory += ","+foodToAdd;
    }

    public String getWaterHistory() {
        return waterHistory;
    }

    public void setWaterHistory(String waterToAdd) {
        waterHistory += ","+waterToAdd;
    }
}
