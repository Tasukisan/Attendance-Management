package com.attendance.jsp.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.attendance.controllers.UserController;
import struct.User;
import struct.UserType;

/**
 * @author Matthew Schwartz The doGet() method allows users to access the
 *         /Register page while the doPost() method checks the submitted form
 *         for errors and either returns the user to the /Register page on error
 *         detection, or submits the information to the database creating a new
 *         user.
 * 
 *         The individual 'else' portions of the else-if statements detects
 *         which values are correct and saves the information to the value of
 *         the input so the user does not have to re-enter any correct
 *         information, while the 'if' statements alter the class value to allow
 *         the user to know which input is incorrect.
 */
@WebServlet("/Register")
public class RegisterServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(RegisterServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getRequestDispatcher("/register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		boolean flag = false;
		boolean pass_compare = true;

		String username = request.getParameter("username").toLowerCase();
		String email = request.getParameter("email").toLowerCase();
		String password = request.getParameter("password");
		String duplicate_password = request.getParameter("dupe");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		User user = UserController.getByUsername(username);

		log.info("Performing register action recieved for {}", username);

		String errMsg = "The following values are invalid: ";

		if (null != user || null == username || username.equals(""))
		{
			request.setAttribute("auth_name", "is-invalid");
			errMsg = errMsg + "User Name, ";
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
			request.setAttribute("auth_email", "is-invalid");
			errMsg = errMsg + "Email, ";
		}
		else
		{
			request.setAttribute("ema", email);
		}
		if (null == firstname || firstname.equals(""))
		{
			request.setAttribute("auth_first", "is-invalid");
			errMsg = errMsg + "First Name, ";
			flag = true;
		}
		else
		{
			request.setAttribute("first", firstname);
		}

		if (null == lastname || lastname.equals(""))
		{
			request.setAttribute("auth_last", "is-invalid");
			errMsg = errMsg + "Last Name, ";
			flag = true;
		}
		else
		{
			request.setAttribute("last", lastname);
		}
		if (null == password || password.equals("") || null == duplicate_password || duplicate_password.equals(""))
		{
			request.setAttribute("auth_pass", "is-invalid");
			errMsg = errMsg + "Password, ";
			flag = true;
			pass_compare = false;
		}
		if (pass_compare)
		{
			if (!password.equals(duplicate_password))
			{
				request.setAttribute("auth_pass", "is-invalid");
				errMsg = errMsg + "Password, ";
				flag = true;
			}
		}

		/*
		 * If a flag was thrown, return to /Register else create the object and add it
		 * to the database.
		 */
		if (flag)
		{
			log.info("Registration action failed for {}", username);
			String errMsgAp = errMsg.substring(0, errMsg.length() - 2) + ".";
			request.setAttribute("errorMessage", errMsgAp);
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
		else
		{
			log.info("Registration action completed for {}", username);
			user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setFirstName(firstname);
			user.setLastName(lastname);
			user.setEmail(email);
			user.setType(UserType.INSTRUCTOR);
			UserController.save(user);

			response.sendRedirect("Login");
		}
	}
}
