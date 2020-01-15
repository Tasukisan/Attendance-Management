package struct;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.attendance.controllers.UserController;

public class Refresh
{
	public static void refreshAll(HttpServletRequest request)
	{
		refreshUser(request);
		refreshCourseList(request);
		refreshStudentList(request);
	}

	public static void refreshUser(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		User reUser = UserController.getByUsername(user.getUsername());
		session.setAttribute("user", reUser);
	}

	public static void refreshCourseList(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		List<Course> courses = ((User) session.getAttribute("user")).getCoursesTaught();
		Collections.sort(courses);
		session.setAttribute("listOfCourses", courses);
	}

	public static void refreshStudentList(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String id = (String) session.getAttribute("courseId");
		Course selected = new Course();
		Iterator<Course> it = user.getCoursesTaught().iterator();
		while (it.hasNext())
		{
			selected = it.next();
			if (selected.getId().equals(id))
			{
				break;
			}
		}
		List<User> students = selected.getStudents();
		Collections.sort(students);
		session.setAttribute("listOfStudents", selected.getStudents());
	}
}
