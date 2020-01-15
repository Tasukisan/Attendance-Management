package com.attendance.controllers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.attendance.database.DBUtil;

import struct.User;

public class UserController
{
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	public static User getByUsername(String username)
	{
		EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT u FROM User u WHERE u.username = :username";
		TypedQuery<User> q = entityManager.createQuery(query, User.class);
		q.setParameter("username", username);

		log.info("Searching for user {}", username);

		User retVal = null;
		try
		{
			retVal = q.getSingleResult();
		}
		catch (NoResultException e)
		{
			log.info("User not found: {}", username);
		}
		finally
		{
			entityManager.close();
		}
		return retVal;
	}

	public static User getByID(String id)
	{
		EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT u FROM User u WHERE u.id = :id";
		TypedQuery<User> q = entityManager.createQuery(query, User.class);
		q.setParameter("id", id);

		log.info("Searching for user {}", id);

		User retVal = null;
		try
		{
			retVal = q.getSingleResult();
		}
		catch (NoResultException e)
		{
			log.info("User not found: {}", id);
		}
		finally
		{
			entityManager.close();
		}
		return retVal;
	}

	public static List<User> getAllUsers()
	{
		EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
		String query = "Select u from User u";
		TypedQuery<User> q = entityManager.createQuery(query, User.class);
		List<User> list = q.getResultList();
		return list;
	}

	public static void save(User user)
	{
		EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
		entityManager.getTransaction().begin();
		if (user.getId() == null)
		{
			entityManager.persist(user);
		}
		else
		{
			entityManager.merge(user);
		}
		entityManager.getTransaction().commit();
	}

	public static void saveAll(Collection<User> users)
	{
		Iterator<User> it = users.iterator();
		while (it.hasNext())
		{
			UserController.save(it.next());
		}
	}

	public static void deleteAll(Collection<User> users)
	{
		Iterator<User> it = users.iterator();
		while (it.hasNext())
		{
			UserController.delete(it.next());
		}
	}

	public static void delete(User user)
	{
		EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
		entityManager.getTransaction().begin();
		if (entityManager.contains(user))
		{
			entityManager.remove(user);
		}
		else
		{
			entityManager.remove(entityManager.merge(user));
		}
		entityManager.getTransaction().commit();

	}
}
