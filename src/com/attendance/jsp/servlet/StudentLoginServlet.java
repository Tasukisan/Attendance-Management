package com.attendance.jsp.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import struct.Attendance;
import struct.Course;
import struct.User;

/**
 * Servlet implementation class StudentLogin
 */
@WebServlet("/StudentLogin")
public class StudentLoginServlet extends HttpServlet
{
    
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
		List<Course> studentsCourses = ((User) session.getAttribute("user")).getCoursesEnrolled();
		Collections.sort(studentsCourses);
		session.setAttribute("studentsListOfCourses", studentsCourses);
		request.getRequestDispatcher("/studentInfo.jsp").forward(request, response);
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		String course_code = (String) request.getParameter("pass");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<Attendance> att = user.getAttendance();
		List<Attendance> c_att = new LinkedList<Attendance>();
		Iterator<Attendance> it = att.iterator();
		while (it.hasNext())
		{
			Attendance current = it.next();
			if (current.getCourse().getCourseCode().equals(course_code))
			{
				c_att.add(current);
			}
		}
		session.setAttribute("StudentAttendanceList", c_att);
		session.setAttribute("StudentCode", course_code);
    }

}
