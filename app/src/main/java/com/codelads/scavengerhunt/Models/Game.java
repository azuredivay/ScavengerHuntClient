package com.codelads.scavengerhunt.Models;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class Game
{
    public String Name;
    public int ID;
    public int GM;
    public String Desc;
    public boolean started;
    public int TL;
    public DateTime startTime;
    public static Game MainGame = null; //create game and add it here?

    public List<QRiddle> Questions = new ArrayList<>(); //pseudo fill it?


}
