package com.fyber.tamir.FyberChallenge;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class PortfolioJpaRepo {

	@PersistenceContext
	EntityManager entityManager;

	public Portfolio insert(Portfolio portfolio) {
		return entityManager.merge(portfolio);
	}

	public void remove(Portfolio portfolio) {
		entityManager.remove(portfolio);
	}

	public void updateClientStocks(long clientId, List<PortfolioStock> portStocks) {
		Portfolio port;
		for (PortfolioStock portStock : portStocks) {
			// System.out.println(portStock.getStock() + ", " + portStock.getAmount());
			port = new Portfolio(portStock.getStock(), portStock.getAmount(), clientId);
			insert(port);
		}
	}

	public int removeClientStocks(long clientId) {
		List<Portfolio> clientPortfolios = getByClientId(clientId);
		for (Portfolio port : clientPortfolios) {
			remove(port);
		}
		return clientPortfolios.size();
	}

	public List<Portfolio> getByClientId(long clientId) {
		String queryString = "select p from Portfolio p where p.clientId=:id";
		TypedQuery<Portfolio> query = entityManager.createQuery(queryString, Portfolio.class);
		query.setParameter("id", clientId);
		return query.getResultList();
	}

	public List<Stock> stocksNotOwnedByClient(long clientId) {
		String queryString = "select s from Stock s where s.name not in"
				+ " (select p.stock from Portfolio p where p.clientId=:id)";
		TypedQuery<Stock> query = entityManager.createQuery(queryString, Stock.class);
		query.setParameter("id", clientId);
		return query.getResultList();
	}

	public Portfolio getStockByClientId(long clientId, String stock) {
		String queryString = "select p from Portfolio p where p.clientId=:id and p.stock=:stock";
		TypedQuery<Portfolio> query = entityManager.createQuery(queryString, Portfolio.class);
		query.setParameter("id", clientId);
		query.setParameter("stock", stock);
		Portfolio port;
		try {
			port = query.getSingleResult();
		} catch (NoResultException e) {
			port = null;
		}
		return port;
	}

	public void buySellStocks(long clientId, List<PortfolioStock> portStocks) {
		Portfolio port;
		for (PortfolioStock portStock : portStocks) {
			port = getStockByClientId(clientId, portStock.getStock());
			if (portStock.getBuy() == true) {
				port.setAmount(port.getAmount() + portStock.getAmount());
			} else {
				port.setAmount(port.getAmount() - portStock.getAmount());
			}
			insert(port);
			if(port.getAmount()==0)
				remove(port);
		}
	}

	public boolean isStockOwned(long clientId, String stock) {
		Portfolio port = getStockByClientId(clientId, stock);
		return port != null;
	}

	public boolean ownsAllStocks(long clientId, List<PortfolioStock> portStocks) {
		for (PortfolioStock portStock : portStocks) {
			if (isStockOwned(clientId, portStock.getStock()) == false)
				return false;
		}
		return true;
	}

}
