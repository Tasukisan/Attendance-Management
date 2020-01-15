package com.attendance.jsp.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import struct.Course;
import struct.Refresh;
import struct.User;

/**
 * @author Matthew Schwartz This class allows users to access the home.jsp page
 *         through the doGet() method as well as handles any form submits
 *         through the doPost() method.
 */
@WebServlet("/Home")
public class HomeServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(HomeServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		Refresh.refreshUser(request);
		Refresh.refreshCourseList(request);
		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String id = (String) request.getParameter("pass");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<Course> course = user.getCoursesTaught();
		Course selected = new Course();
		Iterator<Course> it = course.iterator();
		while (it.hasNext())
		{
			selected = it.next();
			if (selected.getId().equals(id))
			{
				break;
			}
		}
		List<User> students = selected.getStudents();
		Collections.sort(students);
		session.setAttribute("code", selected.getCourseCode());
		session.setAttribute("title", selected.getTitle());
		session.setAttribute("listOfStudents", students);
		session.setAttribute("courseId", selected.getId());

		log.info("HomeServlet doPost()");
	}

}
