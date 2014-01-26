package com.example.scorer;

import com.example.scorer.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainScreen extends Activity {
	
	private static int scoreLimit;
	private static ArrayAdapter<String> playerListAdaptar;
	Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.activity_main_screen);
        toast = Toast.makeText(this, "Invalid Score Limit", Toast.LENGTH_LONG);
        
        playerListAdaptar = new ArrayAdapter<String>(this,
    	        android.R.layout.simple_list_item_1);
        
        ListView playerList = (ListView) findViewById(R.id.list_of_players_view);
    	playerList.setAdapter(playerListAdaptar);

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
