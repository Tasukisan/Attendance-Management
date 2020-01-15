package com.attendance.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.attendance.controllers.CourseController;
import com.attendance.controllers.UserController;

import struct.Attendance;
import struct.AttendanceStatus;
import struct.Course;
import struct.User;

class UserControllerTest
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
	void testGetByUsername()
	{
		User theUser = UserController.getByUsername("testUser");
		assertEquals(theUser.getFirstName(), "Test");
		assertEquals(theUser.getLastName(), "User");
		assertEquals(theUser.getEmail(), "test@example.com");
	}

	@Test
	void testCourseHandling()
	{
		User luser1 = UserController.getByUsername("user1");
		assertEquals(1, luser1.getCoursesEnrolled().size());
		assertEquals("COURSE1", luser1.getCoursesEnrolled().get(0).getCourseCode());

		User luser2 = UserController.getByUsername("user2");
		assertEquals(2, luser2.getCoursesEnrolled().size());
	}

	@Test
	void testValidateUser()
	{
		User theUser = UserController.getByUsername("testUser");
		assertTrue(theUser.authUser("password"));
	}

	@Test
	void testGetAllUsers()
	{
		List<User> users = UserController.getAllUsers();
		assertTrue(users.size() >= 6);
	}

	@Test
	void testMarkAttendance()
	{
		User user1 = UserController.getByUsername("user1");
		Course course1 = CourseController.getByCode("COURSE1");
		LocalDateTime classTime = LocalDateTime.now();
		Attendance attendance = new Attendance(user1, course1, AttendanceStatus.PRESENT, classTime);
		user1.markAttendance(attendance);
		UserController.save(user1);
		CourseController.save(course1);

		User luser1 = UserController.getByUsername("user1");
		Course lcourse1 = CourseController.getByCode("COURSE1");

		assertNotNull(luser1.getAttendance());
		assertNotNull(lcourse1.getAttendance());

		assertTrue(1 <= luser1.getAttendance().size());
		assertTrue(1 <= lcourse1.getAttendance().size());

		Map<AttendanceStatus, Double> attendanceResults = luser1.getOverallAttendance();
		assertEquals(100.0, attendanceResults.get(AttendanceStatus.PRESENT), .01);
		assertEquals(0.0, attendanceResults.get(AttendanceStatus.ABSENT), .01);
		assertEquals(0.0, attendanceResults.get(AttendanceStatus.EXCUSED), .01);

		lcourse1.getAttendance().remove(attendance);
		CourseController.save(lcourse1);
	}

//	@Test
//	void createAdminUser() {
//		User admin = new User();
//		admin.setUsername("admin");
//		admin.setPassword("admin");
//		admin.setFirstName("Admin");
//		admin.setPassword("User");
//		admin.setType(UserType.INSTRUCTOR);
//		UserController.save(admin);
//		assertTrue(true);
//	}

}
