package com.attendance.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBUtil
{
	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpaData");

	public static EntityManagerFactory getEmFactory()
	{
		return factory;
	}

	public static void closeResultSet(ResultSet rs)
	{
		try
		{
			if (rs != null)
			{
				rs.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void closePreparedStatement(PreparedStatement ps)
	{
		try
		{
			if (ps != null)
			{
				ps.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void freeConnection(Connection conn)
	{
		try
		{
			if (conn != null)
			{
				conn.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
