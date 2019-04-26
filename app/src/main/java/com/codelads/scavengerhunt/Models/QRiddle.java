package com.codelads.scavengerhunt.Models;

public class QRiddle
{
    private String QID;
    private String Question;
    private String Hint;
    private String Answer;
    private boolean LocBased = false;
    private boolean IsAnswered = false;

    public QRiddle(String qid,String q, String h, String a, boolean loc)
    {
        QID = qid;
        Question = q;
        Hint = h;
        Answer = a;
        LocBased = loc;
    }

    public String GetQuestion()
    {
        return Question;
    }

    public boolean CheckAnswer(String ans)
    {
        return ans.equalsIgnoreCase(Answer);
    }

    public String GetHint()
    {
        return Hint;
    }

    public boolean IsLocBased()
    {
        return LocBased; //if true, change textview value in adapter
    }

    public void MarkAnswered()
    {
        IsAnswered = true;
    }

    public boolean IsAnswered()
    {
        return IsAnswered;
    }
}
