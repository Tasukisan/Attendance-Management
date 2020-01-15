package com.attendance.jsp.servlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AccreditServlet
 */
@WebServlet("/Accredit")
public class AccreditServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		String user0 = request.getParameter("sch");
		String user1 = request.getParameter("bec");
		String user2 = request.getParameter("lew");
		String user3 = request.getParameter("rob");
		String user4 = request.getParameter("arc");

		if (null != user0)
		{
			request.setAttribute("work", this.sch());
			request.setAttribute("member", "Matthew Schwartz");
		}
		else if (null != user1)
		{
			request.setAttribute("work", this.bec());
			request.setAttribute("member", "Benjamin Becker");
		}
		else if (null != user2)
		{
			request.setAttribute("work", this.lew());
			request.setAttribute("member", "Robert Lewis");
		}
		else if (null != user3)
		{
			request.setAttribute("work", this.rob());
			request.setAttribute("member", "Ben Roberts");
		}
		else if (null != user4)
		{
			request.setAttribute("work", this.arc());
			request.setAttribute("member", "Patrick Archer");
		}
		else
		{
			request.getRequestDispatcher("home.jsp").forward(request, response);
		}
		request.getRequestDispatcher("accredit.jsp").forward(request, response);
	}

	/* May store all the information within the database. */
	private LinkedList<WeekAndWork> sch()
	{
		LinkedList<WeekAndWork> list = new LinkedList<WeekAndWork>();
		list.add(new WeekAndWork("1-2", "Prepare the project framework for the team."));
		list.add(new WeekAndWork("3", "Prepare backlog information for the team."));
		list.add(new WeekAndWork("4", "Create the credit and accredit pages and servlets."));
		list.add(new WeekAndWork("5", "Merged branches, refactored code, and assisted the team."));
		list.add(new WeekAndWork("6", "Merged branches, refactored code, and implemented JSTL and assisted the team."));
		list.add(new WeekAndWork("7", "Merged branches, refactored code, and updated the home page, connected pages together."));
		list.add(new WeekAndWork("8", "Merged branches, refactored code, and brought the team backup to speed with the loss of a member."));
		list.add(new WeekAndWork("9", "Merged branches, refactored code, and implemented new email & refresh features."));
		list.add(new WeekAndWork("10", "Merged branches, refactored code, formatted code, removed bugs, and implemented student attendance view."));
		return list;
	}

	private LinkedList<WeekAndWork> bec()
	{
		LinkedList<WeekAndWork> list = new LinkedList<WeekAndWork>();
		list.add(new WeekAndWork("3", "Setting up the development environment."));
		list.add(new WeekAndWork("4", "Redesign the database layout & implement JPA."));
		list.add(new WeekAndWork("5", "Implement the database controllers."));
		list.add(new WeekAndWork("6-7", "Create the takeAttendance page and TakeAttendanceServlet."));
		list.add(new WeekAndWork("8", "Wire the course page to the takeAttendance page. "));
		list.add(new WeekAndWork("9-10", "Upload the website for online testing and preparation."));
		list.add(new WeekAndWork("10", "Attendance Page confirmation functionality"));
		list.add(new WeekAndWork("10", "Statistics tracking, confirmation page, uploading."));
		return list;
	}

	private LinkedList<WeekAndWork> lew()
	{
		LinkedList<WeekAndWork> list = new LinkedList<WeekAndWork>();
		list.add(new WeekAndWork("3", "Setting up the development environment."));
		list.add(new WeekAndWork("4-5", "Create the addCourse page."));
		list.add(new WeekAndWork("6", "Create the removeCourse page and RemoveCourseServlet."));
		list.add(new WeekAndWork("7-8", "Implement the home page tables."));
		list.add(new WeekAndWork("8-9", "Create the removeCourse page and RemoveCourseServlet."));
		list.add(new WeekAndWork("9-10", "Create the studentInfo page and the StudentLoginServlet."));
		return list;
	}

	private LinkedList<WeekAndWork> rob()
	{
		LinkedList<WeekAndWork> list = new LinkedList<WeekAndWork>();
		list.add(new WeekAndWork("3", "Setting up the development environment."));
		list.add(new WeekAndWork("4", "Implement the AddCourseServlet."));
		list.add(new WeekAndWork("5", "Connect the addCourse page with the AddCourseServlet."));
		list.add(new WeekAndWork("6-7", "Create the AddStudentServlet."));
		list.add(new WeekAndWork("8", "Implement the student page."));
		list.add(new WeekAndWork("9-10", "Search for bugs within the webpages."));
		return list;
	}

	private LinkedList<WeekAndWork> arc()
	{
		LinkedList<WeekAndWork> list = new LinkedList<WeekAndWork>();
		list.add(new WeekAndWork("3", "Setting up the development environment."));
		list.add(new WeekAndWork("4-5", "Create the editStudent page."));
		list.add(new WeekAndWork("6", "Create the course page."));
		return list;
	}

	public class WeekAndWork
	{
		String numberWeek = "";
		String workWeek = "";

		public WeekAndWork(String number, String in)
		{
			this.numberWeek = number;
			this.workWeek = in;
		}

		public String getWeek()
		{
			return numberWeek;
		}

		public String getWork()
		{
			return workWeek;
		}
	}
}
