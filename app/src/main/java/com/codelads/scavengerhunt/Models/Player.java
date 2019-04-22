package com.codelads.scavengerhunt.Models;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

public class Player
{
    private String PID;
    private int Score = 0;
    public static Player CurrentPlayer = null;

    public Player(){}

    public Player(Context context)
    {
        if(CurrentPlayer==null)
        {
            CurrentPlayer = new Player();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            {
                return;
            }
            String imei = telephonyManager.getDeviceId();
            PID = imei;
        }
    }

    public void AddScore()
    {
        Score++;
    }

    public String GetScore()
    {
        return Score+"";
    }
}
