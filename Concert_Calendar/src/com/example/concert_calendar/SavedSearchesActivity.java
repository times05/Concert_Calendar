package com.example.concert_calendar;

import java.util.ArrayList;
import java.util.List;

import com.cloudmine.api.CMObject;
import com.cloudmine.api.SimpleCMObject;
import com.cloudmine.api.rest.CMStore;
import com.cloudmine.api.rest.callbacks.CMObjectResponseCallback;
import com.cloudmine.api.rest.response.CMObjectResponse;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SavedSearchesActivity extends ActionBarActivity {
	public final static String ARTIST_NAME = "com.example.concert_calendar.ARTIST_NAME";
	public ArrayList<String> artistToSearch = new ArrayList<String>();
	public int counter = 0;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_searches);
		
		String searchQuery = "[key = \"savedsearch\"]";
		
		CMStore store = CMStore.getStore();
		store.loadApplicationObjectsSearch(searchQuery, new CMObjectResponseCallback(){
			public void onCompletion(CMObjectResponse response){
				for(CMObject object : response.getObjects()){
					SimpleCMObject search = (SimpleCMObject) object;
					String savedsearch = search.getString("artists");
					System.out.println("Adding saved search: " +savedsearch);
					List<Object> listO = search.getList("list");
					List listQ = listO;
					@SuppressWarnings("unchecked")
					List<String> listS = (List<String>) listQ;
					addSearch(savedsearch, listS);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.saved_searches, menu);
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
	public void addSearch(String savedSearch, List<String> listS){
		final ArrayList<String> artists = (ArrayList<String>) listS;
		TextView searchview = new TextView(this);
		searchview.setText(savedSearch);
		searchview.setTextSize(25);
		searchview.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				artistToSearch = artists;
				searchFromSavedSearch(v);
			}	
		});
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout_saved_searches_activity);
		layout.addView(searchview);
	}
	public void searchFromSavedSearch(View view){
		if(MainActivity.canContinue){
			Intent intent = new Intent(this, SearchArtistActivity.class);
			intent.putStringArrayListExtra(ARTIST_NAME, artistToSearch);
			startActivity(intent);
		}else{
			if(counter == 0){
				TextView dlcIncomplete = new TextView(this);
				dlcIncomplete.setText("Downloading concert information, please try again in a few moments");
				LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_saved_searches_activity);
				linearLayout.addView(dlcIncomplete);
				counter++;
			}
		}
	}
}
