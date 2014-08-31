package com.example.concert_calendar;

import ConcertCalendar.parseURL;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayArtistActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_artist);
		
		displayArtist1();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_artist, menu);
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

	public void displayArtist1() {
		
		String artist1 = parseURL.artists.get(0).get(0);
		TextView artist = new TextView(this);
		artist.setTextSize(30);
		artist.setText(artist1);
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_displayArt);
		linearLayout.addView(artist);
	}
}
