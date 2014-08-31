import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;

import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class parseURL {
	/*
	 * Parses XPN URL to make maps for the day of week, date, artist, venue, price, on sale info, ticket link, age, and region
	 * The maps are formatted key - value where key is an integer and value is a string or list of strings in the case of artist
	 * Using the same key over multiple maps will return the information for the same show as the values.
	 */

	public static void main(String[] args) throws Exception {
		
		
		/*
		 * Printing out the information:
		 */
		parseURL function = new parseURL();
		Map<Integer, String> dow = function.getDow();
		Map<Integer, String> date = function.getDate();
		Map<Integer, List<String>> artists = function.getArtist();
		Map<Integer, String> venue = function.getVenue();
		Map<Integer, String> price = function.getPrice();
		Map<Integer, String> onSale = function.getSaleInfo();
		Map<Integer, String> ticketLink = function.getLink();
		Map<Integer, String> age = function.getAge();
		Map<Integer, String> region = function.getRegion();
		
		int len = dow.size();
		System.out.println(len);
		for(int i = 0; i<len; i++){
			for(int k = 0; k<artists.get(i).size(); k++){
				boolean printcomma = true;
				if( k == artists.get(i).size() - 1)
					printcomma = false;
				System.out.print(artists.get(i).get(k));
				if(printcomma)
					System.out.print(", ");
			}
			System.out.println(" @ " + venue.get(i) + " on " + dow.get(i) + " " + date.get(i) + " for " + price.get(i) + " Age requirement: " + age.get(i));
			
		}
	}
	
	public void CleanArtists(String[] mystring) {
		/*
		 * CleanArtists() takes an array of strings as an input and removes '- XPN WELCOMES', '<b>', and '</b>' from each string
		 */
		Pattern pattern;
		Matcher matcher;
		
		for (int i = 0; i<mystring.length; i++){
			//Remove trailing XPN WELCOMES
			String current = mystring[i];
			pattern = Pattern.compile("(.*?)-? <b>XPN WELCOMES</b>");
			matcher = pattern.matcher(current);
			if(matcher.find()){
				current = matcher.group(1);
			}
			//Remove </b> tags
			pattern = Pattern.compile("(.*)</b>(.*)");
			matcher = pattern.matcher(current);
			if(matcher.find()){
				current = matcher.group(1) + matcher.group(2);
			}
			//Remove <b> tags
			pattern = Pattern.compile("<b>(.*)");
			matcher = pattern.matcher(current);
			if(matcher.find()){
				current = matcher.group(1);
			}
			//Remove remaining XPN WELCOMES
			pattern = Pattern.compile("(.*) - XPN WELCOMES");
			matcher = pattern.matcher(current);
			if(matcher.find()){
				current = matcher.group(1);
			}
			
			mystring[i] = current;
		}
	}
	
	public void CleanTicketURL(String[] mystring){
		/*
		 * CleanTicketURL() takes an array of strings as an input and returns the URL in that string as long as the string is in the format:
		 * .*\"URL\".*
		 */
		for (int i = 0; i<mystring.length; i++){
			String tixLink = mystring[i];
			Pattern pattern = Pattern.compile("\"(.*?)\"");
			Matcher matcher = pattern.matcher(tixLink);
			if (matcher.find()){
				mystring[i] = matcher.group(1);
			}
		}
	}
	
	private Document doc() throws IOException{
		String xpn = "http://xpn.org/events/concert-calendar";

		Document document = Jsoup.connect(xpn).get();
		return document;
	}
	
	/*
	 * Constructors
	 */
	
	private Map<Integer, String> dow() throws IOException {
		// TODO Auto-generated method stub
		parseURL func = new parseURL();
		Document doc = func.doc();
		
		String[] dayofweek = doc.getElementsByClass("cell-1").html().split("\n");
		Map<Integer, String> dow = new HashMap<Integer, String>();
		
		for(int i = 0; i<dayofweek.length; i++){
			dow.put(i, dayofweek[i]);
		}
		return dow;
	}
	private Map<Integer, String> date() throws IOException {
		// TODO Auto-generated method stub
		parseURL func = new parseURL();
		Document doc = func.doc();
		
		String[] dateofshow = doc.getElementsByClass("cell0").html().split("\n");
		Map<Integer, String> date = new HashMap<Integer, String>();
		
		for(int i = 0; i<dateofshow.length; i++){
			date.put(i, dateofshow[i]);
		}
		return date;
	}
	private Map<Integer, List<String>> artists() throws IOException{
		// TODO Auto-generated method stub
		parseURL func = new parseURL();
		Document doc = func.doc();
		
		String[] artist = doc.getElementsByClass("cell1").html().split("\n");
		Map<Integer, List<String>> artists = new HashMap<Integer, List<String>>();
		
		parseURL function = new parseURL();
		function.CleanArtists(artist); //Removes excess characters from artist[]
		/*
		 * Assign Artists Separately
		 * Multiple artists look like: Headliner / Opening act 1 / Opening act 2 ...
		 * Singular Artists look like Headliner
		 */
		int len = artist.length;
		for(int i = 0; i<len; i++){
			String[] splitstring = artist[i].split("/");
			List <String> art = new ArrayList<String>();
			for (int j = 0; j<splitstring.length; j++){
				splitstring[j] = splitstring[j].trim();
				art.add(splitstring[j]);
			}
			artists.put(i, art);
		}
		
		return artists;
	}
	private Map<Integer, String> venue() throws IOException {
		// TODO Auto-generated method stub
		parseURL func = new parseURL();
		Document doc = func.doc();
		
		String[] venueofshow = doc.getElementsByClass("cell2").html().split("\n");
		Map<Integer, String> venue = new HashMap<Integer, String>();
		
		for(int i = 0; i<venueofshow.length; i++){
			venue.put(i, venueofshow[i]);
		}
		return venue;
	}
	private Map<Integer, String> price() throws IOException {
		// TODO Auto-generated method stub
		parseURL func = new parseURL();
		Document doc = func.doc();
		
		String[] priceofshow = doc.getElementsByClass("cell3").html().split("\n");
		Map<Integer, String> price = new HashMap<Integer, String>();
		
		for(int i = 0; i<priceofshow.length; i++){
			price.put(i, priceofshow[i]);
		}
		return price;
	}
	private Map<Integer, String> onSale() throws IOException {
		// TODO Auto-generated method stub
		parseURL func = new parseURL();
		Document doc = func.doc();
		
		String[] onSaleInfo = doc.getElementsByClass("cell4").html().split("\n");
		Map<Integer, String> onSale = new HashMap<Integer, String>();
		
		for(int i = 0; i<onSaleInfo.length; i++){
			onSale.put(i, onSaleInfo[i]);
		}
		
		return onSale;
	}
	private Map<Integer, String> ticketLink() throws IOException {
		// TODO Auto-generated method stub
		parseURL func = new parseURL();
		Document doc = func.doc();
		
		String[] tixlink = doc.getElementsByClass("cell5").html().split("\n");
		Map<Integer, String> ticketLink = new HashMap<Integer, String>();
		
		parseURL function = new parseURL();
		function.CleanTicketURL(tixlink); //Removes excess characters from tixLink[]
		
		for(int i = 0; i<tixlink.length; i++){
			ticketLink.put(i, tixlink[i]);
		}
		return ticketLink;
	}
	private Map<Integer, String> age() throws IOException {
		// TODO Auto-generated method stub
		parseURL func = new parseURL();
		Document doc = func.doc();
		
		String[] agetoenter = doc.getElementsByClass("cell6").html().split("\n");
		Map<Integer, String> age = new HashMap<Integer, String>();
		
		for(int i = 0; i<agetoenter.length; i++){
			age.put(i, agetoenter[i]);
		}
		return age;
	}
	private Map<Integer, String> region() throws IOException {
		// TODO Auto-generated method stub
		parseURL func = new parseURL();
		Document doc = func.doc();
		
		String[] regionofshow = doc.getElementsByClass("cell7").html().split("\n");
		Map<Integer, String> region = new HashMap<Integer, String>();
		
		for(int i = 0; i<regionofshow.length; i++){
			region.put(i, regionofshow[i]);
		}
		return region;
	}
	
	/*
	 * Accessors
	 */
	public Map<Integer, String> getDow() throws IOException{
		return dow();
	}
	public Map<Integer, String> getDate() throws IOException{
		return date();
	}
	public Map<Integer, List<String>> getArtist() throws IOException{
		return artists();
	}
	public Map<Integer, String> getVenue() throws IOException{
		return venue();
	}
	public Map<Integer, String> getPrice() throws IOException{
		return price();
	}
	public Map<Integer, String> getSaleInfo() throws IOException{
		return onSale();
	}
	public Map<Integer, String> getLink() throws IOException{
		return ticketLink();
	}
	public Map<Integer, String> getAge() throws IOException{
		return age();
	}
	public Map<Integer, String> getRegion() throws IOException{
		return region();
	}
}
