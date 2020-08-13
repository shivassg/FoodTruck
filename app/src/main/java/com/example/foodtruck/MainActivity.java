package com.example.foodtruck;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

/**
 *   Opening Screen.  Fetches the data from API when the App is opened.
 */
public class MainActivity extends AppCompatActivity {

    String jsonstr;
    ListView lv;
    ArrayList<FoodTruck> foodTruckList;
    FoodTruckListAdapter foodTruckadpater;
    ProgressDialog progressDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mapmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void gotomap(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra("foodtruck",foodTruckList);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.listView);
        progressDialog=new ProgressDialog(this);
        foodTruckList = new ArrayList<>();

        FetchFoodTruckData foodTruckData= new FetchFoodTruckData();
        foodTruckData.execute();

    }

    //Fetch the Food Truck Data in the background
    private class FetchFoodTruckData extends AsyncTask{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading data from API.");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            JsonParsing parsingObj = new JsonParsing();
            jsonstr = parsingObj.procesurl("https://data.sfgov.org/resource/jjew-r69b.json"); //API Link
            JSONArray jsonArray = null;

            try{
                jsonArray = new JSONArray(jsonstr);
                for( int i=0; i < jsonArray.length(); i++){
                    JSONObject jobj = jsonArray.optJSONObject(i);
                    if( jobj != null){
                        //TODO: Need to validate the string before using it. Invalid/empty data can be received?

                        String foodtruckName = jobj.getString("applicant");
                        String foodTruckAddress = jobj.getString("location");
                        String foodTruckAdditionalText = (jobj.has("optionaltext")) ? jobj.getString("optionaltext") : "Empty";


                        String startTime = jobj.getString("starttime");
                        String endTime = jobj.getString("endtime");

                        String dayOfWeek = jobj.getString("dayofweekstr");

                        String latitude = jobj.getString("latitude");
                        String longitude = jobj.getString("longitude");

                        String start24Hours = jobj.getString("start24");
                        String end24Hours = jobj.getString("end24");

                        FoodTruck foodtruckObj = new FoodTruck(foodtruckName, foodTruckAddress, foodTruckAdditionalText, startTime, endTime, dayOfWeek, latitude, longitude);
                        if (checkIfTruckIsOpen(start24Hours, end24Hours, dayOfWeek)) {
                            foodTruckList.add(foodtruckObj);
                        }
                    }
                }


            }catch (JSONException |ParseException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(Object o) {

            super.onPostExecute(o);

            Collections.sort(foodTruckList); //Sorting Alphabettical order
            foodTruckadpater = new FoodTruckListAdapter(MainActivity.this, R.layout.custom_list_view, foodTruckList);
            lv.setAdapter(foodTruckadpater);
            progressDialog.dismiss();
        }

        /*
           Getting PST time and checkin trucks open for the PST timezone
         */

        public boolean checkIfTruckIsOpen(String start24Hours, String end24Hours, String dayOfWeek) throws ParseException {
            boolean isTruckOpen = false;

            TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
            Date now = new Date();
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("EEEE");
            String currentDay = dateFormat.format(now);

            java.text.SimpleDateFormat hourFormat = new java.text.SimpleDateFormat("HH");

            int currentTime = Integer.parseInt(hourFormat.format(now));

            int startTimeInt = ((Date)hourFormat.parse(start24Hours)).getHours();
            int endTimeInt = ((Date)hourFormat.parse(end24Hours)).getHours();

            if(currentDay.equalsIgnoreCase(dayOfWeek) && (currentTime > startTimeInt && currentTime < endTimeInt)) {
                isTruckOpen= true;
                return isTruckOpen;
            }else{
                return isTruckOpen;
            }
        }
    }
}