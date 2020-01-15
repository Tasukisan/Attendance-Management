package com.attendance.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.attendance.controllers.CourseController;
import struct.Course;

class CourseControllerTest
{

	@BeforeAll
	static void setUp() throws Exception
	{
		TestUtil.getInstance().initTestDB();
	}

	@AfterAll
	static void tearDown() throws Exception
	{
		TestUtil.getInstance().cleanupDB();
	}

	@Test
	void testLookupByTitle()
	{
		Course theCourse = CourseController.getByTitle("Course One");
		assertEquals(theCourse.getCourseCode(), "COURSE1");
		assertEquals(theCourse.getTitle(), "Course One");
		assertEquals(theCourse.getDescription(), "The first course");
	}

	@Test
	void testLookupByCode()
	{
		Course theCourse = CourseController.getByCode("COURSE2");
		assertEquals(theCourse.getCourseCode(), "COURSE2");
		assertEquals(theCourse.getTitle(), "Course Two");
		assertEquals(theCourse.getDescription(), "The second course");

	}

	@Test
	void testGetAllCourses()
	{
		List<Course> courses = CourseController.getAllCourses();
		assertTrue(4 <= courses.size());
	}

//	@Test
//	void testSetupAttendanceDemo() {
//		Course course = new Course();
//		course.setTitle("The Demo Class");
//		course.setCourseCode("DEMO101");
//		course.setDescription("A class to use for demoing.");
//		course.setDaysInSession(7 * 12);
//		CourseController.save(course);
//
//		User user1 = new User();
//		user1.setFirstName("Alice");
//		user1.setLastName("Adams");
//		user1.setUsername("aadams");
//		user1.setEmail("aadams@example.com");
//		user1.addCourseEnrolled(course);
//		UserController.save(user1);
//		User user2 = new User();
//		user2.setFirstName("Bruce");
//		user2.setLastName("Banner");
//		user2.setUsername("bbanner");
//		user2.setEmail("bbanner@example.com");
//		user2.addCourseEnrolled(course);
//		UserController.save(user2);
//
//		User user3 = new User();
//		user3.setFirstName("Chris");
//		user3.setLastName("Calvert");
//		user3.setUsername("ccalvert");
//		user3.setEmail("ccalvert@example.com");
//		user3.addCourseEnrolled(course);
//		UserController.save(user3);
//
//	}

}
