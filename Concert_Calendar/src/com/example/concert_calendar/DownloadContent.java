package com.example.concert_calendar;
import java.io.IOException;


import android.os.AsyncTask;
import ConcertCalendar.parseURL;

class DownloadContent extends AsyncTask<String, Integer, String>{
	private CMO listener;
	
	public DownloadContent(CMO listener){
		this.listener = listener;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String validation = "failed in async";
		
		try {
			System.out.println("Async: dlc started");
			validation = parseURL.downloadContent();
			System.out.println("Async: dlc complete");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return validation;
	}
	
	@Override
	protected void onPostExecute(String validate){
		//isComplete = true;
		super.onPostExecute(validate);
		System.out.println("Async: post execute ");
		SearchActivity.isComplete();
		try {
			listener.updateCMContent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
