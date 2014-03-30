package com.example.scorer;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
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
			View scoreLimitText = (View) findViewById(R.id.score_limit_textview);
			scoreLimitText.setVisibility(View.GONE);
			View scoreLimitEditor = (View) findViewById(R.id.score_limit_editor);
			scoreLimitEditor.setVisibility(View.GONE);
			pauseGame();
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
			EditText scoreLimitEditor = (EditText) findViewById(R.id.score_limit_editor);
			try{
				scoreLimit = Integer.parseInt(scoreLimitEditor.getText().toString());
			}
			catch (NumberFormatException e)
			{
				myToast("Invalid Score Limit", false);
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

			this.setTitle("Score Limit is "+ scoreLimit);

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
