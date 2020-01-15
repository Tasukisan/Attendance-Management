package com.attendance.jsp.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.attendance.controllers.UserController;
import struct.Course;
import struct.User;

/**
 * Servlet implementation class RemoveStudentServlet
 */
@WebServlet("/RemoveStudent")
public class RemoveStudentServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getRequestDispatcher("/removeStudent.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String courseId = (String) request.getSession().getAttribute("courseId");
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
				List<Course> courses = user.getCoursesEnrolled();
				for (int i = 0; i < courses.size(); i++)
				{
					if (courses.get(i).getId().equals(courseId))
					{
						courses.remove(i);
						user.setCoursesEnrolled(courses);
						UserController.save(user);
						break;
					}
				}
			}
			response.sendRedirect("Course");
		}
	}
}
