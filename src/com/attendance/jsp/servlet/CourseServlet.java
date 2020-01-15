package com.attendance.jsp.servlet;

import java.io.IOException;
import java.util.Iterator;
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

import struct.Attendance;
import struct.Course;
import struct.Refresh;
import struct.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class CourseServlet
 */
@WebServlet("/Course")
public class CourseServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(CourseServlet.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Refresh.refreshAll(request);
		request.getRequestDispatcher("course.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		log.info("CourseServlet posted.");
		String username = (String) request.getParameter("pass_stud");
		User user = UserController.getByUsername(username);
		HttpSession session = request.getSession();
		session.setAttribute("student_username", user.getUsername());
		session.setAttribute("student_email", user.getEmail());
		session.setAttribute("student_firstname", user.getFirstName());
		session.setAttribute("student_lastname", user.getLastName());

		String code = (String) session.getAttribute("code");
		Course current_course = CourseController.getByCode(code);
		List<Attendance> list = user.getAttendance();
		List<Attendance> reList = new LinkedList<Attendance>();
		Iterator<Attendance> it = list.iterator();
		while (it.hasNext())
		{
			Attendance att = it.next();
			Course course = att.getCourse();
			if (course.equals(current_course))
			{
				reList.add(att);
			}
		}
		session.setAttribute("listOfAttendance", reList);
		Refresh.refreshAll(request);
	}
}
