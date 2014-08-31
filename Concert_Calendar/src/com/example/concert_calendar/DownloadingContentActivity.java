package com.example.concert_calendar;

import java.util.Date;

import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.SimpleCMObject;
import com.cloudmine.api.rest.callbacks.ObjectModificationResponseCallback;
import com.cloudmine.api.rest.response.ObjectModificationResponse;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import ConcertCalendar.parseURL;

public class DownloadingContentActivity extends ActionBarActivity {
	public static boolean isComplete = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Get message from intent
		Intent intent = getIntent();
		//String message = intent.getStringExtra(MainActivity.DOWNLOAD_MESSAGE);
		
		//Create Text View
		//TextView textView = new TextView(this);
		//textView.setTextSize(40);
		//textView.setText(message);
		
		//Add text view to layout
		//LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_dlc);
		//linearLayout.addView(textView);

			try {
				System.out.println("Trying downloadContent");
				downloadContent();
				System.out.println("completed DownloadContent");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
		//setContentView(R.layout.activity_downloading_content);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.downloading_content, menu);
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
	
	public void downloadContent() throws Exception {
		String validation = "attempting...";
		
		//new DownloadContent(this).execute("test");
		
		//LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_dlc);
		TextView validate = new TextView(this);
		validate.setTextSize(30);
		validate.setText(validation); 
		
		//linearLayout.addView(validate);
	}
	public static void dlcComplete(){
		isComplete = true;
	}
	public void advance(View view) {
		if(isComplete){
			Intent intent = new Intent(this, DisplayArtistActivity.class);
			startActivity(intent);
		}
		else
		{
			TextView incomplete = new TextView(this);
			incomplete.setTextSize(30);
			incomplete.setText("Downloading in progress, try again later");
			//LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_dlc);
			//linearLayout.addView(incomplete);
		}
	}
	public void displayArtist1() {
		
		String artist1 = parseURL.artists.get(0).get(0);
		Context context = getBaseContext();
		TextView artist = new TextView(context);
		artist.setTextSize(30);
		artist.setText(artist1);
		//LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_dlc);
		//linearLayout.addView(artist);
	}
	
}
