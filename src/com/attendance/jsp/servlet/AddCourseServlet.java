package com.attendance.jsp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.attendance.controllers.CourseController;

import struct.Course;
import struct.User;

/**
 * Servlet implementation class AddCourseServlet
 */
@WebServlet("/AddCourse")
public class AddCourseServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getRequestDispatcher("addCourse.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String courseID = request.getParameter("courseId");
		String courseName = request.getParameter("coursename");
		String courseDesc = request.getParameter("coursedesc");
		String errMsg = "The following values are invalid: ";
		boolean flag = false;
		
		Course check = CourseController.getByCode(courseID);

		if (null == courseID || "".equals(courseID) || null != check)
		{
			request.setAttribute("course_id", "is-invalid");
			errMsg = errMsg + "Course Code, ";
			flag = true;
		}
		else
		{
			request.setAttribute("id", courseID);
		}
		if (null == courseName || "".equals(courseName))
		{
			request.setAttribute("course_name", "is-invalid");
			errMsg = errMsg + "Course Name, ";
			flag = true;
		}
		else
		{
			request.setAttribute("cname", courseName);
		}

		if (flag)
		{
			String errMsgAp = errMsg.substring(0, errMsg.length() - 2) + ".";
			request.setAttribute("errorMessage", errMsgAp);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/addCourse.jsp");
			rd.include(request, response);
		}

		if (!flag)
		{
			User user = (User) request.getSession().getAttribute("user");
			Course course = new Course();
			course.setCourseCode(courseID);
			course.setTitle(courseName);
			if (null != courseDesc)
			{
				course.setDescription(courseDesc);
			}
			course.setInstructor(user);
			course.setDaysInSession(0);
			CourseController.save(course);
			response.sendRedirect("Home");
		}
	}
}
