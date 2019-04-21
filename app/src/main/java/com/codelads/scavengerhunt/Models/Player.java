package com.codelads.scavengerhunt.Models;

public class Player
{
    private int PID;
    private int Score = 0;
    public static Player CurrentPlayer = null;

    public Player(int pid)
    {
        PID = pid;
        if(CurrentPlayer==null)
        {
            CurrentPlayer = this;
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
