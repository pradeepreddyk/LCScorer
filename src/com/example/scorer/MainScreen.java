package com.example.scorer;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.HashMap;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainScreen extends Activity {
	
	private static int scoreLimit;
	private static PlayerListAdapter playerListAdaptar;
	private static ArrayList<PlayerDetails> playerList;
	Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.activity_main_screen);
        toast = Toast.makeText(this, "Invalid Score Limit", Toast.LENGTH_LONG);
        
        playerList = new ArrayList<PlayerDetails>();
        
        playerListAdaptar = new PlayerListAdapter((Activity)this, playerList);
        
        ListView playerList = (ListView) findViewById(R.id.list_of_players_view);
    	playerList.setAdapter(playerListAdaptar);
    	
//    	// Click event for single list row
//    	playerList.setOnClickListener(new OnClickListener() {
//    		@Override
//    		public void onClick(View v) {
//    			// TODO Auto-generated method stub
//    			
//    		}
//		});
    	
    }
    
    /** Called when the user touches the start button */
    public void startGame(View view) {
    	//verify non null score
    	EditText scoreLimitEditor = (EditText) findViewById(R.id.score_limit_editor);
    	try{
    		scoreLimit = Integer.parseInt(scoreLimitEditor.getText().toString());
    	}
    	catch (NumberFormatException e)
    	{
    		toast = Toast.makeText(this, "Invalid Score Limit", Toast.LENGTH_LONG);
    		toast.show();
    		return;
    	}
    	Toast toast = Toast.makeText(this, "Score Limit "+scoreLimit, Toast.LENGTH_LONG);
		toast.show();
		
		if(playerListAdaptar.getCount() < 2)
		{
			toast.cancel();
			toast = Toast.makeText(this, "Add Atleast 2 players", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		else
		{
			toast.cancel();
			toast = Toast.makeText(this, playerListAdaptar.getCount() + " players present", Toast.LENGTH_LONG);
			toast.show();
		}
    }
    
    /** Called when the user touches the start button */
    public void addPlayer(View view) {
    	//verify non null score
    	EditText playerNameEditor = (EditText) findViewById(R.id.user_name_editor);
    	
    	String playerName = playerNameEditor.getText().toString();
    	playerNameEditor.setText("");
    	if(playerName == null || playerName.trim().isEmpty())
    	{
    		toast.cancel();
    		toast = Toast.makeText(this, "Enter Valid Player Name", Toast.LENGTH_LONG);
    		toast.show();
    		return;
    	}
 
    	playerListAdaptar.add(playerName);
    	
    	toast.cancel();
    	toast = Toast.makeText(this, "Player "+playerName+ " added", Toast.LENGTH_LONG);
		toast.show();
    }

}
