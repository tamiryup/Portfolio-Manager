package com.fyber.tamir.FyberChallenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class FyberChallengeApplication implements CommandLineRunner {

	@Autowired
	StockStreamCreator streamCreator;
	
	@Autowired
	StockStreamReader streamReader;
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FyberChallengeApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception{
		streamCreator.createStockStream();
		streamReader.readStockStream();
	}
}
