package com.attendance.jsp.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.attendance.controllers.CourseController;
import com.attendance.controllers.UserController;

import struct.Attendance;
import struct.AttendanceStatus;
import struct.Course;
import struct.User;

/**
 * Servlet implementation class ViewAttendanceServlet
 */
@WebServlet("/Attendance")
public class AttendanceServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(AttendanceServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AttendanceServlet()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String course_code = (String) session.getAttribute("code");
		if (null == course_code)
		{
			log.info("Course code is NULL, redirect to Home");
			response.sendRedirect("Home");
		}
		else
		{
//		String course_code = request.getParameter("course_code");
			Course course = CourseController.getByCode(course_code);
			LocalDateTime classtime = Attendance.round(LocalDateTime.now());
			int totalAbsent = 0, totalPresent = 0, totalUnassigned = 0, totalExcused = 0;
			log.info("Rendering attendance screen for {}", course_code);

			request.setAttribute("course", course);
			request.setAttribute("date", classtime.toString());

			Map<User, String> students = new TreeMap<User, String>();
			for (User student : course.getStudents())
			{
				AttendanceStatus thisStatus = student.getAttendanceStatus(course, classtime);
				if (thisStatus != null)
				{
					switch (thisStatus)
					{
					case PRESENT:
						totalPresent += 1;
						students.put(student, "Present");
						break;
					case ABSENT:
						totalAbsent += 1;
						students.put(student, "Absent");
						break;
					case EXCUSED:
						totalExcused += 1;
						students.put(student, "Excused");
						break;
					}
				}
				else
				{
					totalUnassigned += 1;
					students.put(student, "");
				}
			}

			request.setAttribute("totalPresent", totalPresent);
			request.setAttribute("totalAbsent", totalAbsent);
			request.setAttribute("totalExcused", totalExcused);
			request.setAttribute("totalUnassigned", totalUnassigned);

			log.info("{} has {} students.", course_code, students.size());

			request.setAttribute("students", students);
			request.getRequestDispatcher("/attendance.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		log.info("Post called.");

		String userID = request.getParameter("studentID");
		String status = request.getParameter("status");
		String classTimeString = request.getParameter("classTime");
		String courseCode = request.getParameter("courseCode");

		log.info("Values:\nUser ID: {}\nStatus: {}\nClass Time: {}\nCourse Code: {}\n", userID, status, classTimeString,
				courseCode);

		LocalDateTime classTime = Attendance.round(LocalDateTime.parse(classTimeString));
		Course course = CourseController.getByCode(courseCode);
		User student = UserController.getByID(userID);

		Attendance att = new Attendance();
		att.setStatus(Enum.valueOf(AttendanceStatus.class, status.toUpperCase()));
		att.setStudent(student);
		att.setClasstime(classTime);
		att.setCourse(course);

		student.removeAttendance(att);
		student.markAttendance(att);

		UserController.save(student);

		log.info("Saved changes to user {}", userID);
	}

}
