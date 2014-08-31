package com.example.concert_calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ConcertCalendarActivity extends ActionBarActivity {
	
	public static String getTodayDate() {
		String today = "";
		today = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
		return today;
	}
	
	public static String[] getFiveDates(String baseDate) {
		String[] fiveDates = new String[5];
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(baseDate));
			
			for(int i=0; i<5; i++) {
				c.add(Calendar.DATE, 1);  // number of days to add
				fiveDates[i] = sdf.format(c.getTime());  // dt is now the new date
			}
		}
		catch(Exception e) {
			fiveDates[0] = "09-02-2014";
			fiveDates[1] = "09-03-2014";
			fiveDates[2] = "09-04-2014";
			fiveDates[3] = "09-05-2014";
			fiveDates[4] = "09-06-2014";
		}
		
		return fiveDates;
	}
	
	public Vector< Vector<Integer> > getSearchNum(String baseDate) {
		
		Map<Integer, String> date = MainActivity.dates;
		
		Vector< Vector<Integer> > searchNum = new Vector< Vector<Integer> >(5, 2);
		Vector<Integer> day1 = new Vector<Integer>(5, 2);
		Vector<Integer> day2 = new Vector<Integer>(5, 2);
		Vector<Integer> day3 = new Vector<Integer>(5, 2);
		Vector<Integer> day4 = new Vector<Integer>(5, 2);
		Vector<Integer> day5 = new Vector<Integer>(5, 2);
		
		String[] day = getFiveDates(baseDate);
		
		int len = date.size();
		for(int i = 0; i<len; i++){
			if( date.get(i).equals(day[0]) ){
				day1.add(i);
			}
			else if( date.get(i).equals(day[1]) ){
				day2.add(i);
			}
			else if( date.get(i).equals(day[2]) ){
				day3.add(i);
			}
			else if( date.get(i).equals(day[3]) ){
				day4.add(i);
			}
			else if( date.get(i).equals(day[4]) ){
				day5.add(i);
			}
		}
		
		searchNum.add(day1);
		searchNum.add(day2);
		searchNum.add(day3);
		searchNum.add(day4);
		searchNum.add(day5);
		
		return searchNum;
	}
	
	public Vector< Vector<List<String>> > getArtistNameBySearchNum(Vector< Vector<Integer> > searchNum) {
		
		Map<Integer, List<String>> artists = MainActivity.artists;
		
		Vector< Vector<List<String>> > artistNameBySearchNum = new Vector< Vector<List<String>> >(5, 2);
		
		for(int i = 0; i<searchNum.size(); i++){
			Vector<List<String>> artist = new Vector<List<String>>(5, 2);
			for(int j=0; j<searchNum.get(i).size(); j++) {
				artist.add( artists.get(j) );
			}
			artistNameBySearchNum.add(artist);
		}
		
		return artistNameBySearchNum;
	}

	public String getArtistNameInDay(Vector<List<String>> artistNameBySearchNumInDay) {
		Vector<String> artistNames = new Vector<String>(10, 10);
		String artistNameInDay = "";
		
		for(int i = 0; i<artistNameBySearchNumInDay.size(); i++){
			String artistName = "";
			for(int j = 0; j<artistNameBySearchNumInDay.get(i).size(); j++){
				boolean printcomma = true;
				if( j == artistNameBySearchNumInDay.get(i).size() - 1)
					printcomma = false;
				artistName = artistName + artistNameBySearchNumInDay.get(i).get(j);
				if(printcomma)
					artistName = artistName + ", ";
			}
			
			boolean checkSameName = false;
			for(int j=0; j<artistNames.size()-1; j++) {
				if( artistNames.get(j).equals(artistName) )
					checkSameName = true;
			}
			if( !checkSameName ) {
				artistNames.add(artistName);
				
				if( artistNameInDay.equals("") )
					artistNameInDay = artistName;
				else
					artistNameInDay = artistNameInDay + "\n\n" + artistName;
			}
		}
		
		return artistNameInDay;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_concert_calendar);
		
		String baseDate = getTodayDate(); //"08-31-2014";
		String[] day = getFiveDates(baseDate);

		TextView date1 = (TextView)findViewById(R.id.date1);
		date1.setText(day[0]);
		TextView date2 = (TextView)findViewById(R.id.date2);
		date2.setText(day[1]);
		TextView date3 = (TextView)findViewById(R.id.date3);
		date3.setText(day[2]);
		TextView date4 = (TextView)findViewById(R.id.date4);
		date4.setText(day[3]);
		TextView date5 = (TextView)findViewById(R.id.date5);
		date5.setText(day[4]);

		Vector< Vector<Integer> > searchNum = getSearchNum(baseDate);
		Vector< Vector<List<String>> > artistNameBySearchNum = getArtistNameBySearchNum(searchNum);

		String[] artistNameInDay = new String[artistNameBySearchNum.size()];
		for(int i=0; i<artistNameBySearchNum.size(); i++) {
			artistNameInDay[i] = getArtistNameInDay( artistNameBySearchNum.get(i) );
		}
		
		TextView list1 = (TextView)findViewById(R.id.list1);
		list1.setText(artistNameInDay[0]);
		TextView list2 = (TextView)findViewById(R.id.list2);
		list2.setText(artistNameInDay[1]);
		TextView list3 = (TextView)findViewById(R.id.list3);
		list3.setText(artistNameInDay[2]);
		TextView list4 = (TextView)findViewById(R.id.list4);
		list4.setText(artistNameInDay[3]);
		TextView list5 = (TextView)findViewById(R.id.list5);
		list5.setText(artistNameInDay[4]);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.concert_calendar, menu);
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
}
