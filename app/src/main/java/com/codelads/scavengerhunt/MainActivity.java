package com.codelads.scavengerhunt;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.codelads.scavengerhunt.Models.Game;
import com.codelads.scavengerhunt.Models.GameLite;
import com.codelads.scavengerhunt.Models.Player;
import com.codelads.scavengerhunt.Models.PlayerLite;
import com.codelads.scavengerhunt.Services.MockServer;
import com.codelads.scavengerhunt.Services.OfflineHelper;
import com.tomtom.online.sdk.location.Locations;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.model.MapTilesType;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.tomtom.online.sdk.map.MapConstants.DEFAULT_ZOOM_LEVEL;

public class MainActivity extends AppCompatActivity
{
    TomtomMap mmap;
    static Context mc;
    @Override protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.maintheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mc = this;
    }

    void SetGameList(View v)
    {
        PopupWindow hrv = OfflineHelper.handlePopup(v, LayoutInflater.from(this).inflate(R.layout.joingamepopup,null),0,this);
        //hrv.setLayoutManager(new LinearLayoutManager(this));
        View mainView = hrv.getContentView();
        GameListAdapter customAdapter = new GameListAdapter(this, R.layout.gameitem, MockServer.GetCurrentlyRunningGames()); //change null to get list of active games from server
        ListView GList = mainView.findViewById(R.id.GameList);
        GList.setAdapter(customAdapter);
    }

    View pview;
    PopupWindow hrv2;
    public void MainClickHandler(View v)
    {
        try
        {
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_popup_enter));
            switch (v.getTag().toString())
            {
                case "joing":
                    SetGameList(v);
                    break;
                case "createg":
                    hrv2 = OfflineHelper.handlePopup(v, LayoutInflater.from(this).inflate(R.layout.creategamepopup,null),5,this);
                    pview = hrv2.getContentView();
                    MapFragment  mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
                    mapFragment.getAsyncMap(onMapReadyCallback);
                    break;
                case "search":

                    try{
                        ListView lv = OfflineHelper.handlePopup(v, LayoutInflater.from(this).inflate(R.layout.searchpopup,null),1,this).getContentView().findViewById(R.id.sboxlv);
                        WinnerAdapter customAdapter = new WinnerAdapter(this, R.layout.leaderboard_item, MockServer.GetLeaderBoard()); //change null to get list of active games from server
                        lv.setAdapter(customAdapter);
                    }
                    catch (Exception rt)
                    {
                        rt.printStackTrace();
                    }


                    break;

                case "creategame":
                    try
                    {
                        new Game(((EditText)(pview.findViewById(R.id.gameNameTB))).getText().toString(),12,1,((EditText)(pview.findViewById(R.id.gameDescTB))).getText().toString());
                    }
                    catch (Exception ee)
                    {
                        ee.printStackTrace(); //cant find edit texts
                    }
                    try
                    {
                        hrv2.dismiss();
                        PopupWindow hrv3 = OfflineHelper.handlePopup(v, LayoutInflater.from(this).inflate(R.layout.add_qr_popup,null),0,mc);
                    }
                    catch (Exception ee)
                    {
                        ee.printStackTrace(); //cant find edit texts
                    }
                    //startActivity(new Intent(this,maingame.class));
                    break;

                case "skip":
                    startActivity(new Intent(this,maingame.class));
                    break;
            }
        }
        catch (Exception ignore){}

    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mmap.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
            mmap.centerOn(41.872723, -87.649027,DEFAULT_ZOOM_LEVEL, MapConstants.ORIENTATION_NORTH);
        }
    };
}
