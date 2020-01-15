package com.attendance.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool
{

	private static ConnectionPool pool = null;
	private static DataSource dataSource = null;

	private ConnectionPool()
	{
		try
		{
			InitialContext ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/meetingAttendancePool");
		}
		catch (NamingException e)
		{
			System.out.println(e);
		}
	}

	public static synchronized ConnectionPool getInstance()
	{
		if (pool == null)
		{
			pool = new ConnectionPool();
		}

		return pool;
	}

	public Connection getConnection() throws SQLException
	{
		return dataSource.getConnection();
	}
}