package com.attendance.jsp.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.attendance.controllers.UserController;

import struct.User;

@WebServlet("/RemoveUser")
public class RemoveUserServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(RemoveUserServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (!user.getUsername().equals("admin"))
		{
			log.info("Access denined: User {} is not Admin.", user.getUsername());
			response.sendRedirect("Home");
		}
		else
		{
			List<User> list = UserController.getAllUsers();
			Collections.sort(list);
			request.setAttribute("listOfAllUsers", list);
			request.getRequestDispatcher("/removeUser.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Set<String> keys = request.getParameterMap().keySet();
		if (keys.size() == 0)
		{
			request.setAttribute("error", "error");
			doGet(request, response);
		}
		else
		{
			Iterator<String> it = keys.iterator();
			while (it.hasNext())
			{
				String value = it.next();
				User user = UserController.getByUsername(value);
				log.info("Deleting user: {}", user.getUsername());
				UserController.delete(user);
			}
			response.sendRedirect("Home");
		}
	}

}
