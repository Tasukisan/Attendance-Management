package com.attendance.tests;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.attendance.email.EmailUtility;

class TestEmail
{

	private String destination = "schwar24@email.franlin.edu";

	@Test
	void testSendEmail()
	{
		String username = "username";
		String password = "password";
		String iFirstName = "First";
		String iLastName = "Last";
		String sFirstname = "First";
		String sLastName = "Last";
		String courseCode = "Course";
		assertTrue(EmailUtility.SMTPEmail(username, password, destination, iFirstName, iLastName, sFirstname, sLastName,
				courseCode));
	}
}
