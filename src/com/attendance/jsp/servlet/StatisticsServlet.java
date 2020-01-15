package com.attendance.jsp.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
import struct.UserType;

/**
 * Servlet implementation class StatisticsServlet
 */
@WebServlet("/Statistics")
public class StatisticsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		List<User> users;
		List<Course> courses;

		if (user.getType().equals(UserType.INSTRUCTOR))
		{
			users = UserController.getAllUsers();
			courses = CourseController.getAllCourses();

		}
		else
		{
			users = new LinkedList<User>();
			users.add(user);

			courses = user.getCoursesEnrolled();
		}
		Collections.sort(users);
		Collections.sort(courses);
		session.setAttribute("users", users);
		session.setAttribute("courses", courses);
		request.getRequestDispatcher("statistics.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
