package com.fyber.tamir.FyberChallenge;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class StockPriceUpdateJpaRepo {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	StockJpaRepo stockRepo;

	@Autowired
	InfoJpaRepo infoRepo;

	public StockPriceUpdate insert(StockPriceUpdate update) {
		stockRepo.insert(update);
		infoRepo.updateDate(update);
		return entityManager.merge(update);
	}

	public float getStockPrice(String name) {
		String queryString = "select p from StockPriceUpdate p where p.stockName=:name"
				+ " and p.date=(select max(p.date) from StockPriceUpdate p where p.stockName=:name)";
		TypedQuery<StockPriceUpdate> query = entityManager.createQuery(queryString, StockPriceUpdate.class);
		query.setParameter("name", name);
		return query.getSingleResult().getPrice();
	}

	public float getStockValueRaise(String name) {
		Date now = infoRepo.getDate();
		Date before = Helper.addDaysToDate(now, -7);
		StockPriceUpdate relevant;
		String queryString = "select p from StockPriceUpdate p where p.stockName=:name"
				+ " and p.date=(select max(p.date) from StockPriceUpdate p where p.stockName=:name and p.date<=:before)";
		TypedQuery<StockPriceUpdate> query = entityManager.createQuery(queryString, StockPriceUpdate.class);
		query.setParameter("before", before);
		query.setParameter("name", name);
		List<StockPriceUpdate> priceList = query.getResultList();
		if (priceList.size() != 0) {
			relevant = priceList.get(0);
		} else {
			queryString = "select p from StockPriceUpdate p where p.stockName=:name and"
					+ " p.date=(select min(p.date) from StockPriceUpdate p where p.stockName=:name)";
			query = entityManager.createQuery(queryString, StockPriceUpdate.class);
			query.setParameter("name", name);
			relevant = query.getSingleResult();
		}
		return getStockPrice(name) - relevant.getPrice();
	}

	public float getStockFlactuation(String name) {
		Date now = infoRepo.getDate();
		Date before = Helper.addDaysToDate(now, -7);
		String queryString = "select p.price from StockPriceUpdate p where p.stockName=:name and p.date>=:before";
		TypedQuery<Float> query = entityManager.createQuery(queryString, Float.class);
		query.setParameter("before", before);
		query.setParameter("name", name);
		List<Float> values = query.getResultList();
		if(values.size()==0) {
			return 0;
		}
		return Collections.max(values) - Collections.min(values);
	}

	/**
	 * truncates the stock_prices table and resets its id sequence.
	 */
	public void dropTable() {
		String sql = "TRUNCATE TABLE stock_prices;";
		entityManager.createNativeQuery(sql).executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE stock_prices ALTER COLUMN id RESTART WITH 1").executeUpdate();
	}

}
