package com.fyber.tamir.FyberChallenge;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(PortfolioId.class)
public class Portfolio {
	
	@Id
	private String stock;
	@Id
	private long clientId;
	
	private int amount;
	
	public Portfolio() {
		
	}
	
	public Portfolio(String stock, int amount, long clientId) {
		this.stock = stock;
		this.amount = amount;
		this.clientId = clientId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getStock() {
		return stock;
	}

	public long getClientId() {
		return clientId;
	}
	
}
