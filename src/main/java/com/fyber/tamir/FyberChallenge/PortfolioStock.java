package com.fyber.tamir.FyberChallenge;


public class PortfolioStock {
	
	private String stock;
	private int amount;
	private boolean buy = true;
	
	public PortfolioStock() {
		
	}
	
	public PortfolioStock(String stock, int amount) {
		this.stock = stock;
		this.amount = amount;
		this.buy = true;
	}
	
	public PortfolioStock(String stock, int amount, boolean buy) {
		this.stock = stock;
		this.amount = amount;
		this.buy = buy;
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
	
	public boolean getBuy() {
		return buy;
	}
}
