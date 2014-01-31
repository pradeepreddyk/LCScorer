package com.example.scorer;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
 
public class PlayerListAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<PlayerDetails> data;
    private static LayoutInflater inflater=null;
 
    public PlayerListAdapter(Activity a, ArrayList<PlayerDetails> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.player_list_item, null);
 
        TextView playerName = (TextView)vi.findViewById(R.id.l_player_name); // name
        TextView currentScore = (TextView)vi.findViewById(R.id.l_current_score); // current score
        //EditText newScore = (EditText)vi.findViewById(R.id.l_score_input); // new score
        //ImageButton add=(ImageButton)vi.findViewById(R.id.l_add_button); // add button
 
        HashMap<String, String> player = new HashMap<String, String>();
        player = data.get(position);
        
        Log.i("PRADEEP","getView called for position "+position);
 
        // Setting all values in listview
        playerName.setText(player.get(PlayerDetails.KEY_NAME));
        currentScore.setText(player.get(PlayerDetails.KEY_SCORE));
        return vi;
    }
    
    public void add(String playerName)
    {
    	PlayerDetails tmp = new PlayerDetails(playerName);
    	data.add(tmp);
    	View v = inflater.inflate(R.layout.activity_main_screen, null);
    	ListView v1 = (ListView)v.findViewById(R.id.list_of_players_view);
    	v1.invalidate();
    	
    	
    }
    
}
