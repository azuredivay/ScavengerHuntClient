package com.codelads.scavengerhunt.Models;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class Game
{
    private String Name;
    private int ID;
    private int GM;
    private String Desc;
    private boolean started;
    private int TL;
    private DateTime startTime;
    private static Game MainGame = null; //create game and add it here?
    private List<QRiddle> Questions = new ArrayList<>(); //pseudo fill it?

    private Game()
    {
        //MainGame = new Game();
    }

    public Game(String _name, int _id, int _gm, String _desc)
    {
        if(MainGame==null)
        {
            MainGame = new Game();
            MainGame.Name = _name;
            MainGame.ID = _id;
            MainGame.GM = _gm;
            MainGame.Desc = _desc;
        }
    }

    public List<QRiddle> GetQuestions()
    {
        return Questions;
    }

    public void AddQuestion(QRiddle riddle)
    {
        Questions.add(riddle);
    }

    public void AddQuestions(List<QRiddle> qrs)
    {
        Questions.addAll(qrs);
    }

    public static Game GetGame()
    {
        return MainGame;
    }


}
