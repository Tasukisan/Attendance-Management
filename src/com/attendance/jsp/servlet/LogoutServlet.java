package com.attendance.jsp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Matthew Schwartz This class invalidates the session and returns the
 *         user to the login page.
 */
@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(false);
		if (null != session)
		{
			session.invalidate();
		}
		response.sendRedirect("Login");
	}

}
