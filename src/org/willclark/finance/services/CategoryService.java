package org.willclark.finance.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.willclark.finance.models.Category;
import org.willclark.finance.models.User;


public class CategoryService {

	private EntityManager entityManager;
	
	public CategoryService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Category find(long id) throws PersistenceException {
		try {
			return (Category) entityManager.createQuery("from Category where id = :id")
							.setParameter("id", id)
							.getSingleResult();
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to query database", e);
		}
	}
	
	public List<Category> findAll(User user) throws PersistenceException {
		List<Category> list = new ArrayList<Category>(0);
		
		try {
			list = (ArrayList<Category>) entityManager.createQuery("from Category where user = :user")
					.setParameter("user", user)
					.getResultList();
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to query database", e);
		}
		
		Collections.sort(list);
		return list;
	}
					
	public void create(Category category) throws PersistenceException {
		category.setCreated(new Date());
		category.setModified(new Date());
		
		try {
			entityManager.persist(category);
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to create entity", e);
		}
		
	}
	
	public void update(Category category) throws PersistenceException {
		category.setModified(new Date());
		
		try {
			entityManager.merge(category);
		}
		catch (Exception e) {
			throw new PersistenceException("Unable to update entity", e);
		}
		
	}
		
	public void delete(Category category) throws PersistenceException {
		category.setActive(false);
		update(category);
	}
		
}
