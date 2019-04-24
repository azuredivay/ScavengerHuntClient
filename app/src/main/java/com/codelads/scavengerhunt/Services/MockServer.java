package com.codelads.scavengerhunt.Services;

import com.codelads.scavengerhunt.Models.GameLite;
import com.codelads.scavengerhunt.Models.PlayerLite;
import com.codelads.scavengerhunt.Models.QRiddle;

import java.util.ArrayList;
import java.util.List;

public class MockServer
{
    public static List<GameLite> GetCurrentlyRunningGames()
    {
        List<GameLite> games = new ArrayList<>();
        games.add(new GameLite(1,"UIC Game"));
        games.add(new GameLite(2,"CTA Holiday Game"));
        games.add(new GameLite(3,"Faculty Games"));
        //games.add(new GameLite(1,"Private Game"));
        return  games;
    }

    public static List<PlayerLite> GetLeaderBoard()
    {
        List<PlayerLite> players = new ArrayList<>();
        players.add(new PlayerLite(12,"Adam"));
        players.add(new PlayerLite(12,"Sullivan"));
        players.add(new PlayerLite(12,"Phantom"));
        players.add(new PlayerLite(12,"No One"));
        return players;
    }

    public static void SetupGame()
    {

    }

    public static List<QRiddle> GetQuestions()
    {
        List<QRiddle> riddles = new ArrayList<>();
        riddles.add(new QRiddle("1","The building with metal cubes?","They are silver, and in clusters","ERF",true));
        riddles.add(new QRiddle("1","This coding language can Seem sharp","See shh?","C#",false));
        riddles.add(new QRiddle("1","The BAT cave","nananananananana- BATMAN!","TBH",true));
        riddles.add(new QRiddle("1","One half is simple, the other complex, but connected by a bridge","Look both ways while crossing!","SES",true));
        return riddles;
    }
}
