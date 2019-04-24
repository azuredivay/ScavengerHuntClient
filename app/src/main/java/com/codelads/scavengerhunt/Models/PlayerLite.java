package com.codelads.scavengerhunt.Models;

public class PlayerLite
{
    private int Score;
    private String PlayerName;

    public PlayerLite(int _score, String pname)
    {
        Score = _score;
        PlayerName = pname;
    }

    public int GetScore()
    {
        return Score;
    }

    public  String GetPlayerName()
    {
        return PlayerName;
    }
}
