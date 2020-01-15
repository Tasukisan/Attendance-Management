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

import com.attendance.controllers.CourseController;
import com.attendance.controllers.UserController;
import com.attendance.email.EmailUtility;

import struct.Course;
import struct.Refresh;
import struct.User;
import struct.UserType;

/**
 * Servlet implementation class AddStudentServlet
 */
@WebServlet("/AddStudent")

public class AddStudentServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		/* Creates list of all student users */
		Refresh.refreshAll(request);
		HttpSession session = request.getSession();
		String code = (String) session.getAttribute("code");
		request.setAttribute("code", code);
		List<User> users = UserController.getAllUsers();
		Iterator<User> it = users.iterator();
		List<User> students = new LinkedList<User>();
		while (it.hasNext())
		{
			User user = it.next();
			if (user.getType() == UserType.STUDENT)
			{
				students.add(user);
			}
		}
		/* Removes all student users that are already enrolled in the course */
		List<User> nonEnrolled = new LinkedList<User>();
		it = students.iterator();
		while (it.hasNext())
		{
			User user = it.next();
			List<Course> enrolled = user.getCoursesEnrolled();
			boolean check = false;
			for (int i = 0; i < enrolled.size(); i++)
			{
				if (enrolled.get(i).getCourseCode().equals(code))
				{
					check = true;
				}
			}
			if (!check)
			{
				nonEnrolled.add(user);
			}
		}
		Collections.sort(nonEnrolled);
		request.setAttribute("all_students", nonEnrolled);
		request.getRequestDispatcher("addStudent.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String stud = request.getParameter("pass_stud");
		if (null != stud)
		{
			User user = UserController.getByUsername(stud);
			Course course = CourseController.getByCode((String) request.getSession().getAttribute("code"));
			List<Course> courses = user.getCoursesEnrolled();
			if (!courses.contains(course))
			{
				courses.add(course);
				user.setCoursesEnrolled(courses);
				UserController.save(user);
				response.sendRedirect("Course");
			}
		}
		else
		{
			String username = request.getParameter("username").toLowerCase();
			String email = request.getParameter("email").toLowerCase();
			String firstName = request.getParameter("firstname");
			String lastName = request.getParameter("lastname");
			String password = request.getParameter("password");
			String duplicate_password = request.getParameter("dupe");
			String errMsg = "The following values are invalid: ";
			User user = UserController.getByUsername(username);
			boolean flag = false;
			boolean pass_compare = true;

			if (null != user || null == username || username.equals(""))
			{
				request.setAttribute("stud_name", "is-invalid");
				errMsg = errMsg + "Student User Name, ";
				flag = true;
			}
			else
			{
				request.setAttribute("name", username);
			}
			String e = "";
			if (null != email)
			{
				if (!email.equals(""))
				{
					List<User> users = UserController.getAllUsers();
					Iterator<User> it = users.iterator();
					while (it.hasNext())
					{
						User current_user = it.next();
						if (current_user.getEmail().equals(email))
						{
							e = "invalid";
						}
					}
				}
				else
				{
					e = "invalid";
				}
			}
			else
			{
				e = "invalid";
			}
			if (e.equals("invalid"))
			{
				request.setAttribute("stud_email", "is-invalid");
				errMsg = errMsg + "Email, ";
			}
			else
			{
				request.setAttribute("ema", email);
			}
			if (null == firstName || "".equals(firstName))
			{
				request.setAttribute("stud_first", "is-invalid");
				errMsg = errMsg + "Student First Name, ";
				flag = true;
			}
			else
			{
				request.setAttribute("first", firstName);
			}
			if (null == lastName || "".equals(lastName))
			{
				request.setAttribute("stud_last", "is-invalid");
				errMsg = errMsg + "Student Last Name, ";
				flag = true;
			}
			else
			{
				request.setAttribute("last", lastName);
			}
			if (null == password || password.equals("") || null == duplicate_password || duplicate_password.equals(""))
			{
				request.setAttribute("stud_pass", "is-invalid");
				errMsg = errMsg + "Assigned Password, ";
				flag = true;
				pass_compare = false;
			}
			if (pass_compare)
			{
				if (!password.equals(duplicate_password))
				{
					request.setAttribute("auth_pass", "is-invalid");
					flag = true;
				}
			}
			if (flag)
			{
				String errMsgAp = errMsg.substring(0, errMsg.length() - 2) + ".";
				request.setAttribute("errorMessage", errMsgAp);
				doGet(request, response);
				/*
				 * RequestDispatcher rd =
				 * getServletContext().getRequestDispatcher("/addStudent.jsp");
				 * rd.include(request, response);
				 */
			}
			else
			{

				User newUser = new User();
				HttpSession session = request.getSession();
				String code = (String) session.getAttribute("code");
				List<Course> list = new LinkedList<Course>();
				list.add(CourseController.getByCode(code));
				User instructor = (User) session.getAttribute("user");
				boolean emailSent = EmailUtility.SMTPEmail(username, password, email, instructor.getFirstName(),
						instructor.getLastName(), firstName, lastName, code);
				if (emailSent)
				{
					newUser.setUsername(username);
					newUser.setEmail(email);
					newUser.setFirstName(firstName);
					newUser.setLastName(lastName);
					newUser.setPassword(password);
					newUser.setType(UserType.STUDENT);
					newUser.setCoursesEnrolled(list);
					UserController.save(newUser);
					Refresh.refreshAll(request);
					response.sendRedirect("Course");
				}
				else
				{
					request.setAttribute("errorMessage", "Email is invalid");
					doGet(request, response);
					/*
					 * RequestDispatcher rd =
					 * getServletContext().getRequestDispatcher("/addStudent.jsp");
					 * rd.include(request, response);
					 */
				}
			}
		}
	}
}
