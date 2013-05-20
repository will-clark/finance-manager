package org.willclark.finance.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.willclark.finance.models.Account;
import org.willclark.finance.models.Transaction;
import org.willclark.finance.models.User;

public class TransactionService {

	private EntityManager entityManager;
	
	public TransactionService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Transaction find(User user, long id) throws PersistenceException {
		Transaction transaction = null;
		try {
			transaction = (Transaction) entityManager.createQuery("from Transaction where id = :id")
							.setParameter("id", id)
							.getSingleResult();
			
			if (transaction != null && (user != transaction.getUser())) throw new SecurityException("This user may not access another users records!");
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to query database", e);
		}
		return transaction;
	}

	public List<Transaction> findAll(User user, Account account) throws PersistenceException {
		if (user.getId() != account.getUser().getId()) throw new SecurityException("This user may not access another users records!");

		List<Transaction> list = new ArrayList<Transaction>(0);
		
		try {
			list = (ArrayList<Transaction>) entityManager.createQuery("from Transaction where account = :account")
					.setParameter("account", account)
					.getResultList();
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to query database", e);
		}
		
		Collections.sort(list);
		return list;
	}
	
	public void create(User user, Account account, Transaction transaction) throws PersistenceException {
		if (user.getId() != account.getUser().getId()) throw new SecurityException("This user may not update another users records!");

		transaction.setAccount(account);
		transaction.setUser(user);
		
		try {
			entityManager.persist(transaction);
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to create entity", e);
		}
		
		new AccountService(entityManager).recalculateBalance(user, account);
	}
	
	public void update(User user, Account account, Transaction transaction) throws PersistenceException {
		if (user.getId() != account.getUser().getId() || user.getId() != transaction.getUser().getId()) throw new SecurityException("This user may not update another users records!");

		try {
			entityManager.merge(transaction);
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to update entity", e);
		}
		
		new AccountService(entityManager).recalculateBalance(user, account);
	}
		
	public void delete(User user, Account account, Transaction transaction) throws PersistenceException {
		if (user.getId() != account.getUser().getId() || user.getId() != transaction.getUser().getId()) throw new SecurityException("This user may not delete another users records!");

		transaction.setActive(false);
		update(user, account, transaction);
	}
		
}
