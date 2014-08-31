package com.example.concert_calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cloudmine.api.CMObject;
import com.cloudmine.api.SimpleCMObject;
import com.cloudmine.api.rest.CMStore;
import com.cloudmine.api.rest.callbacks.CMObjectResponseCallback;
import com.cloudmine.api.rest.callbacks.ObjectModificationResponseCallback;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.ObjectModificationResponse;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchActivity extends ActionBarActivity {
	public final static String ARTIST_NAME = "com.example.concert_calendar.ARTIST_NAME";
	public static boolean isComplete = false;
	public int counter = 0;
	public ArrayList<String> addedArtists = new ArrayList<String>();


	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	/** Called when user clicks add button */
	public void addArtist(View view){
		EditText artistName = (EditText) findViewById(R.id.artist_search);
		String artist = artistName.getText().toString();
		if(artist != null){
			addedArtists.add(artist);
			TextView added = new TextView(this);
			added.setText(artist);
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_artists_activity_search);
			linearLayout.addView(added);
		}
	}
	/** Called when user clicks Search button */
	public void searchArtist(View view){
		if(isComplete && MainActivity.canContinue){
			Intent intent = new Intent(this, SearchArtistActivity.class);
			EditText artistName = (EditText) findViewById(R.id.artist_search);
			//String artist = artistName.getText().toString();
			intent.putStringArrayListExtra(ARTIST_NAME, addedArtists);
			startActivity(intent);
		}else{
			if(counter == 0){
				TextView dlcIncomplete = new TextView(this);
				dlcIncomplete.setText("Downloading concert information, please try again in a few moments");
				LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_activity_search);
				linearLayout.addView(dlcIncomplete);
				counter++;
			}
		}
	}
	
	public static void isComplete(){
		isComplete = true;
	}
	/** Called when user clicks save button */
	public void saveSearch(View view){
		SimpleCMObject savedSearch = new SimpleCMObject();
		ArrayList<String> search = addedArtists;
		savedSearch.add("key", "savedsearch");
		String savedsearch = "";
		for(int i = 0; i<search.size(); i++){
			if(i == 0){
				savedsearch = search.get(i).toString();
			}else{
			savedsearch = savedsearch + ", " + search.get(i).toString();
			}
		}
		savedSearch.add("artists", savedsearch);
		savedSearch.save(new ObjectModificationResponseCallback(){
			public void onCompletion(ObjectModificationResponse response){
				System.out.println("Search was saved");
			}
		});
		TextView saved = new TextView(this);
		saved.setText("Search Saved");
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_activity_search);
		linearLayout.addView(saved);
	}
}
