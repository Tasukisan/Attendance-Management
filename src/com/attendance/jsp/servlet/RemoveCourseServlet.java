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

import com.attendance.controllers.CourseController;
import com.attendance.controllers.UserController;

import struct.Course;
import struct.User;

/**
 * Servlet implementation class RemoveCourseServlet
 */
@WebServlet("/RemoveCourse")
public class RemoveCourseServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<Course> list = user.getCoursesTaught();
		Collections.sort(list);
		session.setAttribute("UCL", list);

		request.getRequestDispatcher("/removeCourse.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Set<String> keys = request.getParameterMap().keySet();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<Course> list = user.getCoursesTaught();
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
				Course course = CourseController.getByCode(value);
				list.remove(course);
				user.setCoursesTaught(list);
				UserController.save(user);
				CourseController.delete(course);
			}
			response.sendRedirect("Home");
		}
	}
}
