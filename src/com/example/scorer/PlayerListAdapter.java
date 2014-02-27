package com.example.scorer;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
 
public class PlayerListAdapter extends ArrayAdapter<PlayerDetails> {
 
    private Activity activity;
    private ArrayList<PlayerDetails> data;
 
    Context context; 
    int layoutResourceId;    
    
    public PlayerListAdapter(Context context, int layoutResourceId, ArrayList<PlayerDetails>  data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
 
    public int getCount() {
        return data.size();
    }
  
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        if(convertView==null)
            vi = inflater.inflate(R.layout.player_list_item, null);
 
        TextView playerName = (TextView)vi.findViewById(R.id.l_player_name); // name
        TextView currentScore = (TextView)vi.findViewById(R.id.l_current_score); // current score
        EditText newScore = (EditText)vi.findViewById(R.id.l_score_input); // new score
        PlayerDetails player = data.get(position);
        
        Log.i("PRADEEP","getView called for position "+position);
 
        // Setting all values in listview
        playerName.setText(player.getName());
        //currentScore.setText(player.getScore());
        return vi;
    }
}
