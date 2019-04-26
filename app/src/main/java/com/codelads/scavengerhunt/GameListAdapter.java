package com.codelads.scavengerhunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.codelads.scavengerhunt.Models.GameLite;
import com.codelads.scavengerhunt.Models.Player;
import com.codelads.scavengerhunt.Services.OfflineHelper;

import java.util.List;

public class GameListAdapter extends ArrayAdapter<GameLite>
{
    private int resourceLayout;
    private Context mContext;

    public GameListAdapter(Context context, int resource, List<GameLite> items)
    {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent)
    {

        View v = convertView;

        if (v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        GameLite p = getItem(position);

        if (p != null)
        {
            TextView tt1 =  v.findViewById(R.id.gname);
            tt1.setText(p.GetGameName());
            Button join = v.findViewById(R.id.Join);
            join.setOnClickListener(v1 ->
            {

                Player p01 = new Player(MainActivity.mc,"Player You");

            });

            //TextView locIcon = v.findViewById(R.id.loc);
        }

        return v;
    }
}
