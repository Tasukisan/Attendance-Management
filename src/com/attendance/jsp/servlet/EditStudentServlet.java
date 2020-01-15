package com.attendance.jsp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.attendance.controllers.UserController;

import struct.User;

/**
 * Servlet implementation class EditStudent
 */
@WebServlet("/EditStudent")
public class EditStudentServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getRequestDispatcher("editStudent.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String username = ((String) request.getSession().getAttribute("student_username"));
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String password = request.getParameter("password");
		String dupe = request.getParameter("dupe");
		
		User user = UserController.getByUsername(username);
		HttpSession session = request.getSession();
		
		if (null != firstname && !firstname.equals("") && null != lastname && !lastname.equals(""))
		{
			if (null != password && null != dupe && !password.equals("") && !dupe.equals(""))
			{
				if (password.equals(dupe))
				{
					user.setFirstName(firstname);
					user.setLastName(lastname);
					user.setPassword(password);
					UserController.save(user);
					
					session.setAttribute("student_firstname", firstname);
					session.setAttribute("student_lastname", lastname);
					
					response.sendRedirect("Student");
				}
				else
				{
					request.setAttribute("error", "error");
					doGet(request, response);
				}
				
			}
			else
			{
				user.setFirstName(firstname);
				user.setLastName(lastname);
				UserController.save(user);
				
				session.setAttribute("student_firstname", firstname);
				session.setAttribute("student_lastname", lastname);
				
				response.sendRedirect("Student");
			}
		}
		else
		{
			request.setAttribute("error", "error");
			doGet(request, response);			
		}
		

	}
}
