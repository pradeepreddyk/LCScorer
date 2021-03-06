package com.appsforfunnutility.scorer;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
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
	private static PlayerListAdapter playerListAdaptar;
	private static ArrayList<PlayerDetails> playerList;
	Toast toast;
	GameState state;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		toast = Toast.makeText(this, "Invalid Score Limit", Toast.LENGTH_LONG);
		state = GameState.PAUSED;

		playerList = new ArrayList<PlayerDetails>();
		playerListAdaptar = new PlayerListAdapter(this, R.layout.player_list_item, playerList);
		ListView playerListView = (ListView) findViewById(R.id.list_of_players_view);
		playerListView.setAdapter(playerListAdaptar);

		// Click event for single list row
		playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long arg3) {
				View vi=view;
				LayoutInflater inflater = getLayoutInflater();
				if(view==null)
					vi = inflater.inflate(R.layout.player_list_item, null);

				myToast("clicked " + position + "val " + arg3);

				EditText newScore = (EditText)vi.findViewById(R.id.l_score_input); // new score
				//ImageButton add=(ImageButton)vi.findViewById(R.id.l_add_button); // add button
				newScore.setVisibility(View.VISIBLE);

				Button startButton = (Button) findViewById(R.id.start_button);
				startButton.setVisibility(View.VISIBLE);
			}
		}); 
		showScoreLimitPopup();
	}
	
	private void showScoreLimitPopup(){
		Log.e("PRADEEP","Before popup");
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		input.setText(scoreLimit+"");
		AlertDialog.Builder alert = new AlertDialog.Builder(this)
	    .setTitle("Set Score Limit")
	    .setMessage("")
	    .setCancelable(true)
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	Log.e("PRADEEP", "input is " + input.getText());
				try{
					scoreLimit = Integer.parseInt(input.getText().toString());
					MainScreen.this.setTitle("Score Limit is "+ scoreLimit);
				}
				catch (NumberFormatException e)
				{
					myToast("Invalid Score Limit", false);
					return;
				}
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert);
		// Set an EditText view to get user input 
		
		alert.setView(input);
	     alert.show();
	     Log.e("PRADEEP","After	popup");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_layout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_add_player:
			View playerInput = (View) findViewById(R.id.score_input_layout);
			playerInput.setVisibility(View.VISIBLE);
			pauseGame();
			return true;
		case R.id.menu_set_score:
			showScoreLimitPopup();
			return true;
		case R.id.menu_help:
			myToast("Help pressed.. Please Help");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void pauseGame()
	{
		Button startButton = (Button) findViewById(R.id.start_button);
		startButton.setVisibility(View.VISIBLE);
		startButton.setText("Start");
		state = GameState.PAUSED;
	}

	public void myToast(String text)
	{
		myToast(text,true);
	}
	
	public void myToast(String text, Boolean flag)
	{
		Log.d("TOAST_LOG",text);
		if(toast!=null && flag)
		{
			toast.cancel();
		}
		toast = Toast.makeText(this, text , Toast.LENGTH_LONG);
		toast.show();
	}
	
	/** Called when the user touches the start button */
	public void startGame(View view) {
		//verify non null score
		if(state == GameState.PAUSED)
		{
			if (scoreLimit < -1)
			{
				myToast("Invalid Score Limit", false);
				showScoreLimitPopup();
				return;
			}
				
			myToast("Score Limit "+scoreLimit, false);

			if(playerListAdaptar.getCount() < 2)
			{
				myToast("Add Atleast 2 players", true);
				return;
			}
			else
			{
				myToast(playerListAdaptar.getCount() + " players present", true);
			}
			View scoreInput = (View) findViewById(R.id.score_input_layout);
			scoreInput.setVisibility(View.GONE);

			

			Button startButton = (Button) findViewById(R.id.start_button);
			startButton.setText("Add");
			startButton.setVisibility(View.INVISIBLE);
			state = GameState.STARTED;
			ListView playerListView = (ListView) findViewById(R.id.list_of_players_view);
			for (int i = 0; i < playerListView.getChildCount(); i++)
			{
				View v = playerListView.getChildAt(i);
				playerListAdaptar.getView(i, v, null);
			}
		}
		else if(state == GameState.STARTED)
		{
			ListView playerListView = (ListView) findViewById(R.id.list_of_players_view);
			int j = 0;
			for (int i = 0; i < playerListView.getChildCount(); i++)
			{
			    View v = playerListView.getChildAt(i);
			    int value = 0;
			    EditText tx = (EditText) v.findViewById(R.id.l_score_input);
			    try{
			    value = Integer.parseInt(tx.getText().toString());
			    }
			    catch (Exception e)
			    {
			    	myToast("Please enter valid integers for score");
			    	return;
			    }
			    playerList.get(j).addScore(value);
			    tx.setText("");
			    if(playerList.get(j).getScore() <= scoreLimit)
			    {
			    	playerListAdaptar.getView(j, v, null);
			    }
			    else
			    {
			    	myToast("Player " + playerList.get(j).getName() + " is out of Game");
			    	playerListAdaptar.remove(playerList.get(j));
			    	j--;
			    }
			    j++;
			}
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
			myToast( "Enter Valid Player Name", true);
			return;
		}
		playerListAdaptar.add(new PlayerDetails(playerName));
		myToast("Player "+playerName+ " added", true);
	}
}
