package com.fyber.tamir.FyberChallenge;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stocks")
public class Stock {
	
	@Id
	private String name;
	
	public Stock() {
		
	}
	
	public Stock(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
