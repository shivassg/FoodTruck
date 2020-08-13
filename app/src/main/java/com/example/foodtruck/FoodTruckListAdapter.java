package com.example.foodtruck;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Custom List View Adapter
 */
public class FoodTruckListAdapter extends ArrayAdapter<FoodTruck> {

    private Activity mContext;

    public FoodTruckListAdapter(Activity context, int resource, ArrayList<FoodTruck> objects) {
        super(context, resource, objects);
        mContext = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String foodTruckName = getItem(position).getFoodTruckName();
        String foodTruckAddress =  getItem(position).getFoodTruckAddress();
        String foodTruckAdditionalText = getItem(position).getFoodTruckAdditionalText();
        String startTime = getItem(position).getStartTime();
        String endTime = getItem(position).getEndTime();
        String dayOfWeek = getItem(position).getDayOfWeek();

        FoodTruck foodTruck = new FoodTruck(foodTruckName, foodTruckAddress, foodTruckAdditionalText, startTime, endTime,dayOfWeek,"","");

        LayoutInflater inflater = mContext.getLayoutInflater();
        View rowview = inflater.inflate(R.layout.custom_list_view, null, true);

        TextView tvName = rowview.findViewById(R.id.resname);
        TextView tvAddress = rowview.findViewById(R.id.adres);
        TextView tvAdditionalText= rowview.findViewById(R.id.foodinfo);
        TextView tvStartTime = rowview.findViewById(R.id.timimg);
        TextView tvEndTime = rowview.findViewById(R.id.textView5);
        TextView tvDayOfWeek = rowview.findViewById(R.id.textView6);

        tvName.setText(foodTruckName);
        tvAddress.setText(foodTruckAddress);
        tvAdditionalText.setText(foodTruckAdditionalText);
        tvStartTime.setText(startTime+" - "+endTime);
        tvEndTime.setText(endTime);
        tvDayOfWeek.setText(dayOfWeek);

        return rowview;

    }
}
