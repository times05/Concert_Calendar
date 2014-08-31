package com.example.concert_calendar;

import com.cloudmine.api.db.LocallySavableCMObject;
import java.util.Date;

public class DateCMO extends LocallySavableCMObject {

	private Date date;
	
	DateCMO(){}
	public DateCMO(Date currentDate){
		this.date = currentDate;
	}
	
	public Date getDate(){
		return date;
	}
	public void setDate(Date date){
		this.date = date;
	}
	
}
