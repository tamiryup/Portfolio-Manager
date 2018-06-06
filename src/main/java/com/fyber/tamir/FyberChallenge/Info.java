package com.fyber.tamir.FyberChallenge;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Info {
	
	@Id
	private int id = 1;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date currDate;
	
	public Info() {
		
	}
	
	public Info(Date date) {
		this.currDate = date;
	}

	public Date getCurrentDate() {
		return currDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currDate = currentDate;
	}

	public int getId() {
		return id;
	}
	
	
}
