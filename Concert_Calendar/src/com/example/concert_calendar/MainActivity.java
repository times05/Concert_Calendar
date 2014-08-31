package com.example.concert_calendar;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.SimpleCMObject;
import com.cloudmine.api.db.LocallySavableCMObject;
import com.cloudmine.api.rest.CMStore;
import com.cloudmine.api.rest.callbacks.CMObjectResponseCallback;
import com.cloudmine.api.rest.callbacks.ObjectModificationResponseCallback;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.ObjectModificationResponse;

import ConcertCalendar.parseURL;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements CMO{
	
	
	public static Map<Integer, String> dows;
	public static Map<Integer, String> dates;
	public static Map<Integer, List<String>> artists;
	public static Map<Integer, String> venues;
	public static Map<Integer, String> prices;
	public static Map<Integer, String> onSales;
	public static Map<Integer, String> ticketLinks;
	public static Map<Integer, String> ages;
	public static Map<Integer, String> regions;
	public static boolean canContinue = false;
	public final static String EXTRA_MESSAGE = "com.example.concert_calendar.MESSAGE";
	public final static String DOWNLOAD_MESSAGE = "com.example.concert_calendar.DOWNLOADING";
	public static String  dateModified;
	public int counter = 0;
	public int datecounter = 0;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final String APP_ID = "84a0bc25b4bd4d4bb10dc7c16fa9ab63";
		final String API_KEY = "a626e6a575de4651a46de4ae4bb8bf01";
	
		CMApiCredentials.initialize(APP_ID, API_KEY, getApplicationContext());
		
		setContentView(R.layout.activity_main);
		
		System.out.println("Downloading Content");
		String searchQuery = "[key = \"CC\", value = \"Data\"]";
		
		CMStore store = CMStore.getStore();
		store.loadApplicationObjectsSearch(searchQuery, new CMObjectResponseCallback(){
			public void onCompletion(CMObjectResponse response){
				MapCMO datas = new MapCMO();

				for(CMObject object : response.getObjects()){
					MapCMO datacmo = (MapCMO) object;
					datas = datacmo;
				}
				
				dows = datas.getDows();
				dates = datas.getDates();
				artists = datas.getArtists();
				venues = datas.getVenues();
				prices = datas.getPrices();
				onSales = datas.getOnSales();
				ticketLinks = datas.getTicketLinks();
				ages = datas.getAges();
				regions = datas.getRegions();
				
				System.out.println("Maps Set");
				System.out.println(artists);
				
				canContinue = true;
			}
		});

		
		checkDate();
		

	}//main

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	/** Called when user clicks Search button */
	public void search(View view){
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}
	/** Called when user clicks saved searches button */
	public void savedSearches(View view){
		Intent intent = new Intent(this, SavedSearchesActivity.class);
		startActivity(intent);
	}
	/** Called when user clicks Concert Calendar button */
	public void concertCalendar(View view){
		if(canContinue){
			Intent intent = new Intent(this, ConcertCalendarActivity.class);
			startActivity(intent);
		}else{
			if(counter == 0){
				TextView dlcIncomplete = new TextView(this);
				dlcIncomplete.setText("Downloading concert information, please try again in a few moments");
				TableLayout table = (TableLayout) findViewById(R.id.TableLayout1);
				table.addView(dlcIncomplete);
				counter++;
			}
		}

	}
	
	@SuppressWarnings("deprecation")
	public void checkDate(){
		CMStore store = CMStore.getStore();
		String searchQuery = "[Date = \"LastUpdated\"]";
		store.loadApplicationObjectsSearch(searchQuery , new CMObjectResponseCallback(){
			public void onCompletion(CMObjectResponse response){
				for(CMObject object : response.getObjects()){
					if(datecounter == 0){
						SimpleCMObject dateObject = (SimpleCMObject) object;
						System.out.println("TEST RETRIEVE DATE OBJECT: " + dateObject.getDate("DATEOBJECT"));
						compareDates(dateObject.getDate("DATEOBJECT"));
						datecounter += 1;
					}
				}
			}
		
		});
	}
	
	public void compareDates(Date date){
		Date currentDate = new Date();
		long currentDateMilliseconds = currentDate.getTime();
		long lastUpdatedMilliseconds = date.getTime();
		long oneDay = 86400000;
		if(currentDateMilliseconds - lastUpdatedMilliseconds > oneDay ){
			System.out.println("Content is more than one day old.");

			new DownloadContent(this).execute("Download Content");
			
			final Date date2 = new Date();
			String datetime = date2.toString();
			dateModified = datetime;
		SimpleCMObject lastUpdated = new SimpleCMObject();
			lastUpdated.add("Date", "LastUpdated");
			lastUpdated.add("DATEOBJECT", date2);
			lastUpdated.save(new ObjectModificationResponseCallback(){
				public void onCompletion(ObjectModificationResponse response){
					System.out.println("Date was saved:" + date2);
				}
			});
			
		}else{
			System.out.println("It has been " +(currentDateMilliseconds - lastUpdatedMilliseconds)+ " since last update");
			SearchActivity.isComplete();
		}
		
	}
	
	@SuppressWarnings({ })
	public void updateCMContent() throws IOException{
		System.out.println("public void updateCMContent()");
		
		//Delete old content first:
		deletePreviousMaps();

	}
	
	@SuppressWarnings("deprecation")
	public void deletePreviousMaps(){
		System.out.println("Deleting previous maps...");
		String searchQuery = "[key = \"CC\", value = \"Data\"]";
		
		CMStore store = CMStore.getStore();
		store.loadApplicationObjectsSearch(searchQuery, new CMObjectResponseCallback(){
			public void onCompletion(CMObjectResponse response){
				for(CMObject object : response.getObjects()){
					MapCMO datacmo = (MapCMO) object;
					System.out.println("Deleting past CMO ");
					datacmo.delete();
				}
				addUpdatedMaps();
			}
		});
	}
	public void addUpdatedMaps(){
		System.out.println("Adding updated maps");
		
		Map<Integer, String> dows = parseURL.dows;
		Map<Integer, String> dates = parseURL.dates;
		Map<Integer, List<String>> artists = parseURL.artists;
		Map<Integer, String> venues = parseURL.venues;
		Map<Integer, String> prices = parseURL.prices;
		Map<Integer, String> onSales = parseURL.onSales;
		Map<Integer, String> ticketLinks = parseURL.ticketLinks;
		Map<Integer, String> ages = parseURL.ages;
		Map<Integer, String> regions = parseURL.regions;
		
		System.out.println("TESTING DOWS: " + dows.get(0) + dows.get(1));
		
		String key = "CC";
		String value = "Data";
		
		MapCMO newCMO = new MapCMO(key, value, dows, dates, artists, venues, prices, onSales, ticketLinks, ages, regions);
		
		LocallySavableCMObject.saveObjects(MainActivity.this, Arrays.asList(newCMO), new Response.Listener<ObjectModificationResponse>() {
				@Override
				public void onResponse(ObjectModificationResponse objectModificationResponse){
					System.out.println("Saved newCMO...");
				}
		});
	}
}
