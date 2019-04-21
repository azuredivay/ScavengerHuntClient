package com.codelads.scavengerhunt;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.codelads.scavengerhunt.Models.Game;
import com.codelads.scavengerhunt.Models.QRiddle;
import com.codelads.scavengerhunt.Services.OfflineHelper;
import com.tomtom.online.sdk.location.Locations;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.model.MapTilesType;

import java.util.Queue;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.tomtom.online.sdk.map.MapConstants.DEFAULT_ZOOM_LEVEL;

public class maingame extends AppCompatActivity
{

    TomtomMap mmap;
    @Override protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.maintheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maingame);
        ListView CList = this.findViewById(R.id.ClueList);

        Game.MainGame.Questions.add(new QRiddle("1","Name a Functional Language that starts with the letter 'F'?","hint here","F#",false));
        Game.MainGame.Questions.add(new QRiddle("2","What snake can you code with?","hint here","Python",false));
        Game.MainGame.Questions.add(new QRiddle("3","Go to the building that defies gravity?","hint here","",true)); //overload with location data later
        Game.MainGame.Questions.add(new QRiddle("4","Name a Language that no one should like?","hint here","JavaScript",false));
        Game.MainGame.Questions.add(new QRiddle("5","Go to the building that defies gravity?","hint here","",true)); //overload with location data later

        QueueAdapter customAdapter = new QueueAdapter(this, R.layout.queue_item, Game.MainGame.Questions);

        CList .setAdapter(customAdapter);

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.gamemap_fragment);
        mapFragment.getAsyncMap(onMapReadyCallback);
    }

    public void MainClickHandler(View v)
    {
        try
        {
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_popup_enter));
            switch (v.getTag().toString())
            {
                case "search":
                    OfflineHelper.handlePopup(v, LayoutInflater.from(this).inflate(R.layout.searchpopup,null),1,this).getContentView().findViewById(R.id.sboxrv);
                    break;

            }
        }
        catch (Exception ignore){}

    }

    private final OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback()
    {
        @Override public void onMapReady(TomtomMap map)
        {
            //Map is ready here
            mmap = map;
            mmap.setMyLocationEnabled(true);
            mmap.getUiSettings().setMapTilesType(MapTilesType.VECTOR);
            mmap.setGeopoliticalView("IN");
            mmap.centerOnMyLocationWithNorthUp();
            mmap.centerOn(Locations.AMSTERDAM.getLatitude(), Locations.AMSTERDAM.getLongitude(),DEFAULT_ZOOM_LEVEL, MapConstants.ORIENTATION_NORTH);
        }
    };

}
