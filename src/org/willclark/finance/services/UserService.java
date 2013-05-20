package org.willclark.finance.services;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.willclark.finance.models.User;

public class UserService {

	private EntityManager entityManager;
	
	public UserService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public User find(long id) throws PersistenceException {
		try {
			return (User) entityManager.createQuery("from User where id = :id")
							.setParameter("id", id)
							.getSingleResult();
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to query database", e);
		}
	}

	public User find(String username) throws PersistenceException {
		try {
			return (User) entityManager.createQuery("from User where username = :username")
							.setParameter("username", username)
							.getSingleResult();
		}
		catch (NoResultException nre) {
			return null;
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to query database", e);
		}
	}
	
	public User findByEmail(String email) throws PersistenceException {
		try {
			return (User) entityManager.createQuery("from User where email = :email")
							.setParameter("email", email)
							.getSingleResult();
		}
		catch (NoResultException nre) {
			return null;
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to query database", e);
		}
	}
	
	public User findByResetToken(String resetToken) throws PersistenceException {
		try {
			return (User) entityManager.createQuery("from User where resetToken = :resetToken")
							.setParameter("resetToken", resetToken)
							//.setParameter("now", new Date())
							.getSingleResult();
		}
		catch (NoResultException nre) {
			return null;
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to query database", e);
		}
	}
	
	public void create(User user) throws PersistenceException {
		user.setCreated(new Date());
		user.setModified(new Date());
		
		try {
			entityManager.persist(user);
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to create entity", e);
		}
		
	}
	
	public void update(User user) throws PersistenceException {
		user.setModified(new Date());
		
		try {
			entityManager.merge(user);
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to update entity", e);
		}
		
	}
		
	public void delete(User user) throws PersistenceException {
		user.setActive(false);
		update(user);
	}
		
}