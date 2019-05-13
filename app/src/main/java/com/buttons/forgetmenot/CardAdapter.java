package com.buttons.forgetmenot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<Plant> mDataSet;
    private Context context;

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        TextView plantName,lastWater,waterInterval,lastFeeding,feedingInterval;
        ImageView plantPicture;
        View view;
        public CardViewHolder(View v)
        {
            super(v);
            plantName = v.findViewById(R.id.plantName);
            lastWater = v.findViewById(R.id.lastWatered);
            waterInterval = v.findViewById(R.id.waterIntervalDate);
            lastFeeding = v.findViewById(R.id.lastFed);
            feedingInterval = v.findViewById(R.id.foodIntervalDate);
            plantPicture = v.findViewById(R.id.plantPic);
            view=v;
        }
    }

    public CardAdapter(List<Plant> mDataSet)
    {
        this.mDataSet=mDataSet;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        context=parent.getContext();
        view.setOnClickListener(MainScreen.listener);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder,final int listPosition){


        TextView plantName = holder.plantName;
        TextView lastWater = holder.lastWater;
        TextView waterInterval = holder.waterInterval;
        TextView lastFeeding = holder.lastFeeding;
        TextView feedingInterval = holder.feedingInterval;
        ImageView plantPicture = holder.plantPicture;

        plantName.setText(mDataSet.get(listPosition).getPlantName());
        lastWater.setText(mDataSet.get(listPosition).getLastWatered());
        waterInterval.setText(mDataSet.get(listPosition).getWaterInterval());
        lastFeeding.setText(mDataSet.get(listPosition).getLastFed());
        feedingInterval.setText(mDataSet.get(listPosition).getFeedInterval());
        if(mDataSet.get(listPosition).getImage()!=null)
            plantPicture.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(mDataSet.get(listPosition).getImage(),0,mDataSet.get(listPosition).getImage().length),
                (int)PlantAddScreen.dipToPixels(context,100),(int)PlantAddScreen.dipToPixels(context,100),false));
    }

    @Override
    public int getItemCount()
    {
        return mDataSet.size();
    }

}
