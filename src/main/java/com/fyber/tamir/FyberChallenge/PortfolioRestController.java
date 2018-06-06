package com.fyber.tamir.FyberChallenge;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import exceptions.InvalidClientException;
import exceptions.InvalidPortfolioException;

@RestController
public class PortfolioRestController {
	
	@Autowired
	ClientJpaRepo clientRepo;
	
	@Autowired
	PortfolioJpaRepo portfolioRepo;
	
	@Autowired
	StockJpaRepo stockRepo;
	
	@Autowired
	StockPriceUpdateJpaRepo stockPricesRepo;
	
	@RequestMapping(value="/create", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	@ResponseBody
	public Client createClient(@RequestBody PortfolioWrapper wrapper) {
		if (!isValidPortfolio(wrapper.getStocks()))
			throw new InvalidPortfolioException();
		Client client = clientRepo.insert(new Client());
		portfolioRepo.updateClientStocks(client.getId(), wrapper.getStocks());
		return client;
	}
	
	
	@RequestMapping(value="/{clientId}/portfolio-value", method=RequestMethod.GET)
	@ResponseBody
	public float getPortfolioValue(@PathVariable long clientId) {
		if(!clientRepo.exists(clientId))
			throw new InvalidClientException();
		List<Portfolio> portfolios = portfolioRepo.getByClientId(clientId);
		float sum = 0;
		for(Portfolio portfolio : portfolios) {
			int amount = portfolio.getAmount();
			float price = stockPricesRepo.getStockPrice(portfolio.getStock());
			sum += amount*price;
		}
		return sum;
	}
	
	@RequestMapping(value="/{clientId}/update", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public void updatePortfolio(@PathVariable long clientId, @RequestBody PortfolioWrapper wrapper) {
		if(!clientRepo.exists(clientId))
			throw new InvalidClientException();
		if (!isValidPortfolio(wrapper.getStocks()))
			throw new InvalidPortfolioException();
		if(!portfolioRepo.ownsAllStocks(clientId, wrapper.getStocks())) { //sent new portfolio
			portfolioRepo.removeClientStocks(clientId);
			portfolioRepo.updateClientStocks(clientId, wrapper.getStocks());
		}else {
			if(!isValidPortfolioUpdate(wrapper.getStocks(), clientId))
				throw new InvalidPortfolioException();
			portfolioRepo.buySellStocks(clientId, wrapper.getStocks());
		}
		
	}
	
	
	
	public boolean isValidPortfolio(List<PortfolioStock> portStocks) {
		for(PortfolioStock portStock : portStocks) {
			if(stockRepo.exists(portStock.getStock())==false || portStock.getAmount()<0) 
				return false;
		}
		return true;
	}
	
	public boolean isValidPortfolioUpdate(List<PortfolioStock> portStocks, long clientId) {
		if(!isValidPortfolio(portStocks)) 
			return false;
		for(PortfolioStock portStock : portStocks) {
			Portfolio port = portfolioRepo.getStockByClientId(clientId, portStock.getStock());
			if(port!=null && portStock.getBuy() == false && port.getAmount() < portStock.getAmount()) 
				return false;
		}
		return true;
	}

	
	
}
