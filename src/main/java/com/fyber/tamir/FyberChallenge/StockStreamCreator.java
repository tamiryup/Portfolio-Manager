package com.fyber.tamir.FyberChallenge;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.stereotype.Component;

@Component
public class StockStreamCreator {

	// TODO: add random stock updates. one per day for every stock.

	public StockPriceUpdate[] createUpdates() {
		StockPriceUpdate fb1 = new StockPriceUpdate("FB", 143f, new GregorianCalendar(2018, Calendar.MAY, 2).getTime());
		StockPriceUpdate aapl1 = new StockPriceUpdate("AAPL", 170f, new GregorianCalendar(2018, Calendar.MAY, 2).getTime());
		StockPriceUpdate goog1 = new StockPriceUpdate("GOOG", 500f, new GregorianCalendar(2018, Calendar.MAY, 2).getTime());
		StockPriceUpdate teva1 = new StockPriceUpdate("TEVA", 7740f, new GregorianCalendar(2018, Calendar.MAY, 2).getTime());
		StockPriceUpdate fb2 = new StockPriceUpdate("FB", 140.5f, new GregorianCalendar(2018, Calendar.MAY, 3).getTime());
		StockPriceUpdate fb3 = new StockPriceUpdate("FB", 150.5f, new GregorianCalendar(2018, Calendar.MAY, 10).getTime());
		
		StockPriceUpdate[] stockUpdates = new StockPriceUpdate[] {fb1, aapl1, goog1, teva1, fb2,fb3};
		
		return stockUpdates;
	}
	
	public void createStockStream() {
		
		StockPriceUpdate[] stockUpdates = createUpdates();
		
		try {
			FileOutputStream fileOut = new FileOutputStream("stocks.ser");
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			for(StockPriceUpdate update : stockUpdates) {
				objectOut.writeObject(update);
			}
			objectOut.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
