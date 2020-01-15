package com.attendance.controllers;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.attendance.database.DBUtil;

import struct.Attendance;

public class AttendanceController {

	public static List<Attendance> getByClasstime(LocalDateTime classtime) {
		EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
		String query = "Select c from Attendance c WHERE c.classtime = :classtime";
		TypedQuery<Attendance> q = entityManager.createQuery(query, Attendance.class);
		q.setParameter("classtime", classtime);
		List<Attendance> list = q.getResultList();
		return list;
	}

	public static void delete(Attendance attendance) {
		EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
		entityManager.getTransaction().begin();
		if (entityManager.contains(attendance)) {
			entityManager.remove(attendance);
		} else {
			entityManager.remove(entityManager.merge(attendance));
		}
		entityManager.getTransaction().commit();

	}

	public static void deleteAll(Collection<Attendance> attendanceList) {
		Iterator<Attendance> it = attendanceList.iterator();
		while (it.hasNext()) {
			AttendanceController.delete(it.next());
		}
	}
}
