package com.attendance.jsp.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
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

import com.attendance.controllers.AttendanceController;
import struct.Attendance;

/**
 * Servlet implementation class AttendanceConfirmation
 */
@WebServlet("/AttendanceConfirmation")
public class AttendanceConfirmation extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(AttendanceConfirmation.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();

		LocalDateTime classtime = (LocalDateTime) session.getAttribute("classtime");
		List<Attendance> attendanceList = AttendanceController.getByClasstime(classtime);

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String status = request.getParameter("status");
		String classTimeString = request.getParameter("classTime");
		String courseCode = request.getParameter("courseCode");

		log.info("Values:\nStatus: {}\nClass Time: {}\nCourse Code: {}\n", status, classTimeString, courseCode);

		if (status.contentEquals("cancel"))
		{
			LocalDateTime classTime = Attendance.round(LocalDateTime.parse(classTimeString));

			List<Attendance> attendanceList = AttendanceController.getByClasstime(classTime);

			// Filter list down to just those attendance objects from this class.
			Iterator<Attendance> atIt = attendanceList.iterator();
			while (atIt.hasNext())
			{
				Attendance att = atIt.next();
				if (!att.getCourse().getCourseCode().contentEquals(courseCode))
				{
					atIt.remove();
				}
			}
			AttendanceController.deleteAll(attendanceList);
		}

		response.sendRedirect("Home");
	}

}
