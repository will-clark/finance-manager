package org.willclark.finance.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.willclark.finance.models.Account;
import org.willclark.finance.models.Transaction;
import org.willclark.finance.models.Type;
import org.willclark.finance.models.User;


public class AccountService {

	private EntityManager entityManager;
	
	public AccountService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Account find(User user, long id) throws PersistenceException {
		Account account = null;
		try {
			account = (Account) entityManager.createQuery("from Account where id = :id")
							.setParameter("id", id)
							.getSingleResult();
			
			if (account != null && (user.getId() != account.getUser().getId())) throw new SecurityException("This user may not access another users records!");
		}
		catch (NoResultException e) {
			return null;
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to query database", e);
		}
		return account;
	}
	
	public List<Account> findAll(User user) throws PersistenceException {
		List<Account> list = new ArrayList<Account>(0);
		
		try {
			list = (ArrayList<Account>) entityManager.createQuery("from Account where user = :user")
					.setParameter("user", user)
					.getResultList();
		}
		catch (NoResultException e) {
			return null;
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to query database", e);
		}
		
		Collections.sort(list);
		return list;
	}
					
	public void create(User user, Account account) throws PersistenceException {
		account.setUser(user);
		account.setCreated(new Date());
		account.setModified(new Date());
		
		BigDecimal initialBalance = account.getBalance();
				
		try {
			entityManager.persist(account);
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to create entity", e);
		}
		
		Transaction transaction = new Transaction();
		transaction.setDate(new Date());
		transaction.setActive(true);
		transaction.setAccount(account);
		transaction.setType(Type.CREDIT);		
		transaction.setAmount(initialBalance);
		
		new TransactionService(entityManager).create(user, account, transaction);
	}
	
	public void recalculateBalance(User user, Account account) throws PersistenceException {
		if (user.getId() != account.getUser().getId()) throw new SecurityException("This user may not update another users records!");

		try {
			BigDecimal balance =  (BigDecimal) entityManager.createQuery("select sum(amount) from Transaction where account = :account")
							.setParameter("account", account)
							.getSingleResult();
			
			if (balance != null) {
				account.setBalance(balance);
				update(user, account);
			}
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to query database", e);
		}
	}
	
	public void update(User user, Account account) throws PersistenceException {
		if (user.getId() != account.getUser().getId()) throw new SecurityException("This user may not update another users records!");
		
		account.setModified(new Date());
		
		try {
			entityManager.merge(account);
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to update entity", e);
		}
		
	}
		
	public void delete(User user, Account account) throws PersistenceException {
		if (user.getId() != account.getUser().getId()) throw new SecurityException("This user may not delete another users records!");

		account.setActive(false);
		update(user, account);
	}
		
}