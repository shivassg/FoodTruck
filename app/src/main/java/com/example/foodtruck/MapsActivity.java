package com.example.foodtruck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 *  Google Map Activity.
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<FoodTruck> markersArray;
    LatLngBounds.Builder builder;

    //List View
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tolist,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        builder = new LatLngBounds.Builder();
        markersArray = (ArrayList<FoodTruck>) getIntent().getSerializableExtra("foodtruck");
        Toolbar toolbar = findViewById(R.id.tt);
        setSupportActionBar(toolbar);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (int i = 0; i < markersArray.size(); i++) {
            double lati = Double.parseDouble(markersArray.get(i).getLatitude());
            double longLat = Double.parseDouble(markersArray.get(i).getLongitude());

            mMap.addMarker(new MarkerOptions().position(new LatLng(lati, longLat)).title(markersArray.get(i).getFoodTruckName()).snippet(markersArray.get(i).getFoodTruckAddress()))
                    .setTag(markersArray.get(i));
            builder.include(new LatLng(lati, longLat));

        }


        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds bounds = builder.build();
                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.20); // offset from edges of the map 10% of screen

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

                mMap.animateCamera(cu);

                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30));
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {

                FoodTruck currentLocDetails = (FoodTruck) arg0.getTag();

                Dialog d = new Dialog(MapsActivity.this);
                d.setContentView(R.layout.dialog);
                Window window = d.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.BOTTOM;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);

                ((TextView)d.findViewById(R.id.resname)).setText(""+arg0.getTitle());
                ((TextView)d.findViewById(R.id.adres)).setText(""+arg0.getSnippet());
                ((TextView)d.findViewById(R.id.foodinfo)).setText(""+currentLocDetails.getFoodTruckAdditionalText());
                ((TextView)d.findViewById(R.id.timimg)).setText(""+currentLocDetails.getStartTime()+"-"+currentLocDetails.getEndTime());
                d.show();
            }
        });
    }

    public void gotolist(MenuItem item) {
        finish();
    }
}