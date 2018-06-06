package com.fyber.tamir.FyberChallenge;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ClientJpaRepo {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public Client insert(Client client) {
		return entityManager.merge(client);
	}
	
	public boolean exists(long clientId) {
		String queryString = "select c from Client c where c.id=:id";
		TypedQuery<Client> query = entityManager.createQuery(queryString, Client.class);
		query.setParameter("id", clientId);
		if(query.getResultList().size()==0) {
			return false;
		}
		return true;
	}
	
}
