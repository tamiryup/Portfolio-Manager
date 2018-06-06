package com.fyber.tamir.FyberChallenge;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import exceptions.InvalidClientException;
import exceptions.InvalidRecommendationStrategyException;

@RestController
@RequestMapping("/{id}/recommend")
public class RecommendatinoRestController {

	@Autowired
	PortfolioJpaRepo portfolioRepo;

	@Autowired
	ClientJpaRepo clientRepo;

	@Autowired
	StockPriceUpdateJpaRepo stockPriceUpdateRepo;

	@RequestMapping("/performance")
	@ResponseBody
	public String bestPerformance(@PathVariable long id) {
		if (!clientRepo.exists(id))
			throw new InvalidClientException();
		List<Portfolio> portfolios = portfolioRepo.getByClientId(id);
		String maxStock = null;
		float maxDiff = Float.NEGATIVE_INFINITY;
		for (Portfolio port : portfolios) {
			float diff = stockPriceUpdateRepo.getStockValueRaise(port.getStock());
			if (diff > maxDiff) {
				maxDiff = diff;
				maxStock = port.getStock();
			}
		}
		return maxStock;
	}

	@RequestMapping("/stable")
	@ResponseBody
	public String mostStable(@PathVariable long id) {
		if (!clientRepo.exists(id))
			throw new InvalidClientException();
		List<Portfolio> portfolios = portfolioRepo.getByClientId(id);
		String minFlactStock = null;
		float minFlact = Float.POSITIVE_INFINITY;
		for (Portfolio port : portfolios) {
			float flact = stockPriceUpdateRepo.getStockFlactuation(port.getStock());
			if (flact < minFlact) {
				minFlactStock = port.getStock();
				minFlact = flact;
			}
		}
		return minFlactStock;
	}
	
	@RequestMapping("/best")
	@ResponseBody
	public String highestValue(@PathVariable long id) {
		if (!clientRepo.exists(id))
			throw new InvalidClientException();
		List<Stock> stocks = portfolioRepo.stocksNotOwnedByClient(id);
		System.out.print(stocks.size());
		String maxStock = null;
		float max = Float.NEGATIVE_INFINITY;
		for (Stock stock : stocks) {
			float value = stockPriceUpdateRepo.getStockPrice(stock.getName());
			if (value > max) {
				max = value;
				maxStock = stock.getName();
			}
		}
		return maxStock;
	}

	@RequestMapping("/**")
	public void others() {
		throw new InvalidRecommendationStrategyException();
	}

}
