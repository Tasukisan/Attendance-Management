package com.attendance.jsp.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
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
import struct.UserType;

/**
 * @author Matthew Schwartz This class allows users to access the login.jsp page
 *         as well as processes login requests.
 */
@WebServlet("/Login")

public class LoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String username = request.getParameter("username").toLowerCase();
		String password = request.getParameter("password");
		boolean flag = false;

		if (null == username || "".equals(username) || null == password || "".equals(password))
		{
			flag = true;
		}
		else
		{
			log.info("Performing login action recieved for {}", username);

			User user = UserController.getByUsername(username);
			if (user != null && user.authUser(password))
			{
				UserType type = user.getType();
				if (type == UserType.STUDENT && !flag)
				{
					HttpSession session = request.getSession();
					session.setAttribute("user", user);
					session.setAttribute("username", username);
					session.setMaxInactiveInterval(30 * 60);
					response.sendRedirect("StudentLogin");
				}
				else
				{
					HttpSession session = request.getSession();
					session.setAttribute("user", user);
					session.setAttribute("username", username);
					session.setMaxInactiveInterval(30 * 60);
					response.sendRedirect("Home");
				}
			}
			else
			{
				flag = true;
			}
		}
		if (flag)
		{
			request.setAttribute("error", "is-invalid");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			rd.include(request, response);
		}

	}
}
