package com.codelads.scavengerhunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.codelads.scavengerhunt.Models.GameLite;
import com.codelads.scavengerhunt.Models.Player;
import com.codelads.scavengerhunt.Models.PlayerLite;

import java.util.List;

public class WinnerAdapter extends ArrayAdapter<PlayerLite>
{
    private int resourceLayout;
    private Context mContext;

    public WinnerAdapter(Context context, int resource, List<PlayerLite> items)
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

        PlayerLite p = getItem(position);

        if (p != null)
        {
            TextView tt1 =  v.findViewById(R.id.pname);
            tt1.setText(p.GetPlayerName());

            //TextView locIcon = v.findViewById(R.id.loc);
        }

        return v;
    }
}
