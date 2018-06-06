package com.fyber.tamir.FyberChallenge;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class InfoJpaRepo {

	@PersistenceContext
	EntityManager entityManager;
	
	 Info insert(Date date) {
		Info info = new Info(date);
		return entityManager.merge(info);
	}
	
	public void updateDate(Date date) {
		Info info = entityManager.find(Info.class, 1);
		if(info==null) {
			insert(date);
		}else {
			info.setCurrentDate(date);
		}
	}
	
	public Date getDate() {
		Info info = entityManager.find(Info.class, 1);
		return info.getCurrentDate();
	}
	
	public void updateDate(StockPriceUpdate update) {
		updateDate(update.getDate());
	}
}
