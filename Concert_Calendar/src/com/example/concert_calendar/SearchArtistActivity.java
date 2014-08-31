package com.example.concert_calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cloudmine.api.CMObject;
import com.cloudmine.api.rest.CMStore;
import com.cloudmine.api.rest.callbacks.CMObjectResponseCallback;
import com.cloudmine.api.rest.response.CMObjectResponse;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import ConcertCalendar.retrieveData;

public class SearchArtistActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_artist);
		
		//Get the intent
		Intent intent = getIntent();
		ArrayList<String> artists = intent.getStringArrayListExtra(SearchActivity.ARTIST_NAME);
		System.out.println("Finding shows for: " +artists);
		
		findShows(artists);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_artist, menu);
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
	
	public void findShows(ArrayList<String> artist){
		
		try {
	
					List<String> artists;
					String venue;
					String date;
					String price;
					String age;
					List<Integer> artkeys = new ArrayList<Integer>();
					//GET ARTIST KEYS:
					for(int i = 0; i<artist.size(); i++){//For each artists searched...
						for(int j = 0; j<MainActivity.artists.size(); j++){//For each show in search activity...
							for(int k = 0; k<MainActivity.artists.get(j).size(); k++){//For each artists in a show...
								if(MainActivity.artists.get(j).get(k).contains(artist.get(i))){
								//if(artist.get(i).equals(MainActivity.artists.get(j).get(k))){
									System.out.println("Artist match: " +artist.get(i));
									artkeys.add(j);
								}
							}
						}
					}
					//Use Keys to retrieve data:
					if(artkeys.size() == 0){
						noShows();
					}
					for(int j = 0; j<artkeys.size(); j++){
						artists = MainActivity.artists.get(artkeys.get(j));
						venue = MainActivity.venues.get(artkeys.get(j));
						date = MainActivity.dates.get(artkeys.get(j));
						price = MainActivity.prices.get(artkeys.get(j));
						age = MainActivity.ages.get(artkeys.get(j));
								
						setShows(artists, venue, date, price, age);
					}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setShows(List<String> artists, String venue, String date, String price, String age){
		TextView thisShowArtists = new TextView(this);
		TextView thisShowVenue = new TextView(this);
		TextView thisShowDatePriceAge = new TextView(this);
		TextView thisShowSpacing = new TextView(this);
		thisShowArtists.setTextSize(25);
		thisShowVenue.setTextSize(20);
		thisShowDatePriceAge.setTextSize(20);
		thisShowSpacing.setTextSize(20);
		String formattedArtists = null;
		for(int i = 0 ; i<artists.size(); i++){
			if(i == 0){
				formattedArtists = artists.get(i);
			}else{
				formattedArtists = formattedArtists + ", " + artists.get(i);
			}
		}
		thisShowArtists.setText(formattedArtists);
		thisShowVenue.setText(venue);
		thisShowDatePriceAge.setText(date + " | " + price + " | " + age);
		thisShowSpacing.setText(" ");
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_scroll_search_artists_activity);
		linearLayout.addView(thisShowArtists);
		linearLayout.addView(thisShowVenue);
		linearLayout.addView(thisShowDatePriceAge);
		linearLayout.addView(thisShowSpacing);
		
	}
	public void noShows(){
		TextView noShow = new TextView(this);
		noShow.setTextSize(25);
		noShow.setText("No matches found");
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_activity_search_artists);
		linearLayout.addView(noShow);
	}
}
