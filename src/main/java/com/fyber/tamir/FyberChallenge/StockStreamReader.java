package com.fyber.tamir.FyberChallenge;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockStreamReader {

	@Autowired
	StockPriceUpdateJpaRepo repo;

	public void readStockStream() {
		FileInputStream fileIn = null;
		ObjectInputStream objectIn = null;
		StockPriceUpdate update;
		
		//added this because we are reading the same file over and over again
		//would not be needed in the real world
		repo.dropTable();
		
		try {
			fileIn = new FileInputStream("stocks.ser");
			objectIn = new ObjectInputStream(fileIn);
			while (true) {
				update = (com.fyber.tamir.FyberChallenge.StockPriceUpdate) objectIn.readObject();
				repo.insert(update);
			}
		} catch (EOFException e) {
			try {
				fileIn.close();
				objectIn.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 

	}
}
