package com.codelads.scavengerhunt;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.codelads.scavengerhunt.Models.Player;
import com.codelads.scavengerhunt.Models.QRiddle;
import com.codelads.scavengerhunt.Services.OfflineHelper;

import java.util.List;

public class QueueAdapter extends ArrayAdapter<QRiddle>
{

    private int resourceLayout;
    private Context mContext;

    public QueueAdapter(Context context, int resource, List<QRiddle> items)
    {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View v = convertView;

        if (v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        QRiddle p = getItem(position);

        if (p != null)
        {
            TextView tt1 =  v.findViewById(R.id.question);
            TextView locIcon = v.findViewById(R.id.loc);
            if(!p.IsLocBased())
            {
                locIcon.setText("");
            }
            //TextView tt2 =  v.findViewById(R.id.categoryId);
            //TextView tt3 =  v.findViewById(R.id.description);


            if (tt1 != null) {
                tt1.setText(p.GetQuestion());
            }
        }

        v.setOnClickListener(v12 ->
        {
           PopupWindow pw = OfflineHelper.handlePopup(v12, LayoutInflater.from(mContext).inflate(R.layout.answer_popup,null),0,mContext);
           View MV = pw.getContentView();
           TextView ques = MV.findViewById(R.id.q1);
           TextView hint = MV.findViewById(R.id.hint);
           EditText ans = MV.findViewById(R.id.answerIN);
           Button check = MV.findViewById(R.id.check);
           if(p.IsLocBased())
           {
               ans.setVisibility(View.INVISIBLE);
           }
           ques.setText(p.GetQuestion());
           hint.setText(p.GetHint());
           TextView cor = MV.findViewById(R.id.correct);
           check.setOnClickListener(v1 ->
           {

               if(p.IsLocBased()&&OfflineHelper.IsWithinGeoFence(0,0,0,0,0,0))
               {
                   if(p.CheckAnswer("C4"))
                   {
                       cor.setVisibility(View.VISIBLE);
                       Player.CurrentPlayer.AddScore();
                       maingame.score.setText(Player.CurrentPlayer.GetScore());
                       p.MarkAnswered();
                       remove(p);
                   }
                   else
                   {
                       cor.setVisibility(View.VISIBLE);
                       cor.setTextColor(Color.parseColor("#CD5C5C"));
                       cor.setText("That... is incorrect.");
                   }

               }

               else if(p.CheckAnswer(ans.getText().toString()))
               {
                   cor.setVisibility(View.VISIBLE);
                   Player.CurrentPlayer.AddScore();
                   maingame.score.setText(Player.CurrentPlayer.GetScore());
                   p.MarkAnswered();
                   remove(p);
               }
               else
               {
                   cor.setVisibility(View.VISIBLE);
                   cor.setTextColor(Color.parseColor("#CD5C5C"));
                   cor.setText("That... is incorrect.");
               }
           });
        });

        return v;
    }

}
