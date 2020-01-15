package com.attendance.tests;

import java.util.LinkedList;
import java.util.List;

import com.attendance.controllers.CourseController;
import com.attendance.controllers.UserController;

import struct.Course;
import struct.User;

public class TestUtil
{
	User testUser, user1, user2, user3, user4, user5;
	Course course1, course2, course3;

	List<User> users;
	List<Course> courses;

	static TestUtil instance = null;

	private TestUtil()
	{
		super();

		users = new LinkedList<User>();
		courses = new LinkedList<Course>();
	}

	public static TestUtil getInstance()
	{
		if (instance == null)
		{
			instance = new TestUtil();
		}

		return instance;
	}

	public void initTestDB()
	{
		course1 = new Course();
		course1.setCourseCode("COURSE1");
		course1.setTitle("Course One");
		course1.setDescription("The first course");
		courses.add(course1);

		course2 = new Course();
		course2.setCourseCode("COURSE2");
		course2.setTitle("Course Two");
		course2.setDescription("The second course");
		courses.add(course2);

		course3 = new Course();
		course3.setCourseCode("COURSE3");
		course3.setTitle("Course Three");
		course3.setDescription("The third course");
		courses.add(course3);

		testUser = new User();
		testUser.setFirstName("Test");
		testUser.setLastName("User");
		testUser.setUsername("testUser");
		testUser.setPassword("password");
		testUser.setEmail("test@example.com");
		users.add(testUser);

		user1 = new User();
		user1.setFirstName("User");
		user1.setLastName("One");
		user1.setUsername("user1");
		user1.setPassword("password");
		user1.setEmail("user1@example.com");
		user1.addCourseEnrolled(course1);
		users.add(user1);

		user2 = new User();
		user2.setFirstName("User");
		user2.setLastName("Two");
		user2.setUsername("user2");
		user2.setPassword("password");
		user2.setEmail("user2@example.com");
		user2.addCourseEnrolled(course1);
		user2.addCourseEnrolled(course2);
		users.add(user2);

		user3 = new User();
		user3.setFirstName("User");
		user3.setLastName("Three");
		user3.setUsername("user3");
		user3.setPassword("password");
		user3.setEmail("user3@example.com");
		user3.addCourseEnrolled(course1);
		user3.addCourseEnrolled(course2);
		user3.addCourseEnrolled(course3);
		users.add(user3);

		user4 = new User();
		user4.setFirstName("User");
		user4.setLastName("Four");
		user4.setUsername("user4");
		user4.setPassword("password");
		user4.setEmail("user4@example.com");
		user4.addCourseEnrolled(course2);
		user4.addCourseEnrolled(course3);
		users.add(user4);

		user5 = new User();
		user5.setFirstName("User");
		user5.setLastName("Five");
		user5.setUsername("user5");
		user5.setPassword("password");
		user5.setEmail("user5@example.com");
		user5.addCourseEnrolled(course3);
		users.add(user5);

		CourseController.saveAll(courses);
		UserController.saveAll(users);
	}

	public void cleanupDB()
	{
		UserController.deleteAll(users);
		CourseController.deleteAll(courses);

		users = new LinkedList<User>();
		courses = new LinkedList<Course>();
	}
}
