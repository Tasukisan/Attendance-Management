package com.attendance.controllers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.attendance.database.DBUtil;

import struct.Course;

public class CourseController
{
	public static Course getByTitle(String title)
	{
		EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT c FROM Course c WHERE c.title = :title";
		TypedQuery<Course> q = entityManager.createQuery(query, Course.class);
		q.setParameter("title", title);

		Course retVal = null;
		try
		{
			retVal = q.getSingleResult();
		}
		catch (NoResultException e)
		{
			System.out.println(e);
		}
		finally
		{
			entityManager.close();
		}
		return retVal;
	}

	public static List<Course> getAllCourses()
	{
		EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
		String query = "Select c from Course c";
		TypedQuery<Course> q = entityManager.createQuery(query, Course.class);
		List<Course> list = q.getResultList();
		return list;
	}

	public static void save(Course course)
	{
		EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
		entityManager.getTransaction().begin();
		if (course.getId() == null)
		{
			entityManager.persist(course);
		}
		else
		{
			entityManager.merge(course);
		}
		entityManager.getTransaction().commit();
	}

	public static void saveAll(Collection<Course> courses)
	{
		Iterator<Course> it = courses.iterator();
		while (it.hasNext())
		{
			CourseController.save(it.next());
		}
	}

	public static void delete(Course course)
	{
		EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
		entityManager.getTransaction().begin();
		if (entityManager.contains(course))
		{
			entityManager.remove(course);
		}
		else
		{
			entityManager.remove(entityManager.merge(course));
		}
		entityManager.getTransaction().commit();

	}

	public static void deleteAll(Collection<Course> courses)
	{
		Iterator<Course> it = courses.iterator();
		while (it.hasNext())
		{
			CourseController.delete(it.next());
		}
	}

	public static Course getByCode(String course_code)
	{
		EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT c FROM Course c WHERE c.courseCode = :code";
		TypedQuery<Course> q = entityManager.createQuery(query, Course.class);
		q.setParameter("code", course_code);

		Course retVal = null;
		try
		{
			retVal = q.getSingleResult();
		}
		catch (NoResultException e)
		{
			System.out.println(e);
		}
		finally
		{
			entityManager.close();
		}
		return retVal;
	}

	public static List<Course> getCoursesByID(String id)
	{
		EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT c FROM Course c INNER JOIN User u ON u.id = :id";
		TypedQuery<Course> q = entityManager.createQuery(query, Course.class);
		q.setParameter("id", id);
		List<Course> list = null;
		try
		{
			list = q.getResultList();
		}
		catch (NoResultException e)
		{
			System.out.println(e);
		}
		finally
		{
			entityManager.close();
		}
		return list;
	}
}
