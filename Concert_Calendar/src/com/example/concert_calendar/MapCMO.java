package com.example.concert_calendar;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import ConcertCalendar.parseURL;

import com.cloudmine.api.db.LocallySavableCMObject;


public class MapCMO  extends LocallySavableCMObject {
	
	private String key;
	private String value;
	
	private Map<Integer, String> dowsCMO;
	private Map<Integer, String> datesCMO;
	private Map<Integer, List<String>> artistsCMO;
	private Map<Integer, String> venuesCMO;
	private Map<Integer, String> pricesCMO;
	private Map<Integer, String> onSalesCMO;
	private Map<Integer, String> ticketLinksCMO;
	private Map<Integer, String> agesCMO;
	private Map<Integer, String> regionsCMO;

	MapCMO(){}
	
	public MapCMO(String key, String value, Map<Integer, String> dows, 
			Map<Integer, String> dates, 
			Map<Integer, List<String>> artists, 
			Map<Integer, String> venues, 
			Map<Integer, String> price, 
			Map<Integer, String> onSales, 
			Map<Integer, String> ticketLinks, 
			Map<Integer, String> ages, 
			Map<Integer, String> regions){
		
		this.key = key;
		this.value = value;
		this.dowsCMO = dows;
		this.datesCMO = dates;
		this.artistsCMO = artists;
		this.venuesCMO = venues;
		this.pricesCMO = price;
		this.onSalesCMO = onSales;
		this.ticketLinksCMO = ticketLinks;
		this.agesCMO = ages;
		this.regionsCMO = regions;
		
	}
	
	public void setObjects() throws IOException{
		this.dowsCMO = parseURL.getDow();
		this.datesCMO = parseURL.getDate();
		this.artistsCMO = parseURL.getArtist();
		this.venuesCMO = parseURL.getVenue();
		this.pricesCMO = parseURL.getPrice();
		this.onSalesCMO = parseURL.getSaleInfo();
		this.ticketLinksCMO = parseURL.getLink();
		this.agesCMO = parseURL.getAge();
		this.regionsCMO = parseURL.getRegion();
	}
	
	//Accessors
	public String getKey(){
		return key;
	}
	public String getValue(){
		return value;
	}
	public Map<Integer, String> getDows(){
		return dowsCMO;
	}
	public Map<Integer, String> getDates(){
		return datesCMO;
	}
	public Map<Integer, List<String>> getArtists(){
		return artistsCMO;
	}
	public Map<Integer, String> getVenues(){
		return venuesCMO;
	}
	public Map<Integer, String> getPrices(){
		return pricesCMO;
	}
	public Map<Integer, String> getOnSales(){
		return onSalesCMO;
	}
	public Map<Integer, String> getTicketLinks(){
		return ticketLinksCMO;
	}
	public Map<Integer, String> getAges(){
		return agesCMO;
	}
	public Map<Integer, String> getRegions(){
		return regionsCMO;
	}
	
	//Mutators
	public void setKey(String key){
		this.key = key;
	}
	public void setValue(String value){
		this.value = value;
	}
	public void setDows(Map<Integer, String> dows){
		this.dowsCMO = dows;
	}
	public void setDates(Map<Integer, String> dates){
		this.datesCMO = dates;
	}
	public void setArtists(Map<Integer, List<String>> artists){
		this.artistsCMO = artists;
	}
	public void setVenues(Map<Integer, String> venues){
		this.venuesCMO = venues;
	}
	public void setPrices(Map<Integer, String> prices){
		this.pricesCMO = prices;
	}
	public void setOnSales(Map<Integer, String> onSales){
		this.onSalesCMO = onSales;
	}
	public void setTicketLinks(Map<Integer, String> ticketLinks){
		this.ticketLinksCMO = ticketLinks;
	}
	public void setAges(Map<Integer, String> ages){
		this.agesCMO = ages;
	}
	public void setRegions(Map<Integer, String> regions){
		this.regionsCMO = regions;
	}
	
}
