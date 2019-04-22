package com.codelads.scavengerhunt.Models;

public class GameLite
{
    private int GameID;
    private String GameName;

    public GameLite(int _id, String gname)
    {
        GameID = _id;
        GameName = gname;
    }

    public int GetGameID()
    {
        return GameID;
    }

    public  String GetGameName()
    {
        return GameName;
    }
}
