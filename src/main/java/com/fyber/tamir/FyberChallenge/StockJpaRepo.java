package com.fyber.tamir.FyberChallenge;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class StockJpaRepo {

	@PersistenceContext
	EntityManager entityManager;
	
	public Stock insert(Stock stock) {
		return entityManager.merge(stock);
	}
	
	public Stock insert(StockPriceUpdate stockUpdate) {
		Stock stock = new Stock(stockUpdate.getStockName());
		return insert(stock);
	}
	
	public boolean exists(String stockName) {
		String queryString = "select s from Stock s where s.name=:name";
		TypedQuery<Stock> query = entityManager.createQuery(queryString, Stock.class);
		query.setParameter("name", stockName);
		if(query.getResultList().size()==0) {
			return false;
		}
		return true;
	}
}
