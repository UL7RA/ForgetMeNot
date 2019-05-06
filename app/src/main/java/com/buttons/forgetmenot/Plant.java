package com.buttons.forgetmenot;

import android.database.sqlite.SQLiteDatabase;

public class Plant {
    private int ID;
    private String plantName;
    private String plantDate;
    private String lastWatered;
    private String lastFed;
    private String description;
    private String imageLocation;
    private String feedInterval;
    private String waterInterval;

    public Plant(){}

    public Plant(int ID,String plantName,String description,String plantDate,String lastWatered,String lastFed,String imageLocation, String feedInterval, String waterInterval)
    {
        this.ID=ID;
        this.plantDate=plantDate;
        this.plantName=plantName;
        this.lastFed=lastFed;
        this.lastWatered=lastWatered;
        this.description=description;
        this.imageLocation=imageLocation;
        this.feedInterval=feedInterval;
        this.waterInterval=waterInterval;
    }

    public Plant(String plantName,String plantDate,String lastWatered,String lastFed,String description,String imageLocation, String feedInterval, String waterInterval)
    {
        this.plantDate=plantDate;
        this.plantName=plantName;
        this.lastFed=lastFed;
        this.lastWatered=lastWatered;
        this.description=description;
        this.imageLocation=imageLocation;
        this.feedInterval=feedInterval;
        this.waterInterval=waterInterval;
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

    public String getImageLocation()
    {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation)
    {
        this.imageLocation=imageLocation;
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
}
