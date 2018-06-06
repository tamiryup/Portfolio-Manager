package com.fyber.tamir.FyberChallenge;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Client {

	@Id
	@GeneratedValue
	private long id;
	
	public Client() {
		
	}
	
	public Client(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
}
