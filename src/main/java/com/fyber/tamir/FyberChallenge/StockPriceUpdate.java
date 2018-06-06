package com.fyber.tamir.FyberChallenge;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stock_prices")
public class StockPriceUpdate implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String stockName;
	private float price;
	private Date date;
	
	public StockPriceUpdate() {
		
	}
	
	public StockPriceUpdate(String stockName, float price, Date date) {
		this.stockName = stockName;
		this.price = price;
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public String getStockName() {
		return stockName;
	}

	public float getPrice() {
		return price;
	}

	public Date getDate() {
		return date;
	}
	
	
}
