package com.codelads.scavengerhunt;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.codelads.scavengerhunt.Models.Game;
import com.codelads.scavengerhunt.Models.Player;
import com.codelads.scavengerhunt.Models.QRiddle;
import com.codelads.scavengerhunt.Services.MockServer;
import com.codelads.scavengerhunt.Services.OfflineHelper;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tomtom.online.sdk.location.Locations;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.model.MapTilesType;

import java.util.Queue;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.tomtom.online.sdk.map.MapConstants.DEFAULT_ZOOM_LEVEL;

public class maingame extends AppCompatActivity implements com.google.android.gms.maps.OnMapReadyCallback {

    //TomtomMap mmap;
    private GoogleMap mMap;

    static TextView score;

    //private GeofencingClient geofencingClient;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.maintheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maingame);
        //geofencingClient = LocationServices.getGeofencingClient(this);
        score = findViewById(R.id.score);
        Player p01 = new Player(MainActivity.mc, "Player You");

        ListView CList = this.findViewById(R.id.ClueList);
        Game.GetGame().AddQuestions(MockServer.GetQuestions());

        QueueAdapter customAdapter = new QueueAdapter(this, R.layout.queue_item, Game.GetGame().GetQuestions());

        CList.setAdapter(customAdapter);
        //////////////
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /////////////


        //MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.gamemap_fragment);
        //mapFragment.getAsyncMap(onMapReadyCallback);
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location)
        {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            //mMarker = mMap.addMarker(new MarkerOptions().position(loc));
            if (mMap != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }
        }
    };

    public void MainClickHandler(View v) {
        try {
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_popup_enter));
            switch (v.getTag().toString()) {
                case "search":
                    ListView lv = OfflineHelper.handlePopup(v, LayoutInflater.from(this).inflate(R.layout.searchpopup, null), 1, this).getContentView().findViewById(R.id.sboxlv);
                    WinnerAdapter customAdapter = new WinnerAdapter(this, R.layout.leaderboard_item, MockServer.GetLeaderBoard()); //change null to get list of active games from server
                    lv.setAdapter(customAdapter);
                    break;
            }
        } catch (Exception ignore) {
        }

    }

    /*private final OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback()
    {
        @Override public void onMapReady(TomtomMap map)
        {
            //Map is ready here
            mmap = map;
            mmap.setMyLocationEnabled(true);
            mmap.getUiSettings().setMapTilesType(MapTilesType.VECTOR);
            mmap.setGeopoliticalView("IN");
            mmap.centerOnMyLocationWithNorthUp();
            mmap.centerOn(41.872723, -87.649027,DEFAULT_ZOOM_LEVEL, MapConstants.ORIENTATION_NORTH);
        }
    };*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151); //41.873126, -87.647656
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);
    }

}
