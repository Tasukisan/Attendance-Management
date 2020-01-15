
package com.attendance.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import struct.User;
import struct.UserType;
/**
 * @author Matthew Schwartz
 * This class only allows users that are logged-in to view
 * pages other than /Login and /Register
 */
@WebFilter("/AuthFilter")
public class AuthFilter implements Filter
{
    private ServletContext context;
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    public void init(FilterConfig fConfig) throws ServletException
    {
        this.context = fConfig.getServletContext();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String uri = req.getRequestURI();
        User user = new User();
        
        boolean isLoggedOut = uri.endsWith("Login") || uri.endsWith("Register") || uri.endsWith("login.jsp")
                || uri.endsWith("credits.jsp") || uri.endsWith("Accredit");
        boolean isCss = uri.endsWith("style.css") || uri.endsWith("astronomy-exploration-free-wallpaper-2469122.jpg") || uri.endsWith("styles.css");
        boolean isJs = uri.endsWith("attendance.js");
        boolean isInstructor = false;
        boolean isStudent = false;
        
        try
        {
            user = (User) session.getAttribute("user");
            if (user.getType().equals(UserType.INSTRUCTOR))
            {
                isInstructor = true;
            }
            else if (user.getType().equals(UserType.STUDENT))
            {
                isStudent = true;
            }
        }
        catch (Exception n)
        {
            isInstructor = false;
            isStudent = false;
        }
        
        if (isLoggedOut || isCss || isJs)
        {   
            chain.doFilter(request, response);
        }
        else if (isInstructor)
        {
            chain.doFilter(request, response);
        }
        else if (isStudent)
        {
            if (uri.endsWith("StudentLogin") || uri.endsWith("Logout") || uri.endsWith("StudentCourse"))
            {
                log.info("Page request approved: User is student.");
                chain.doFilter(request, response);
            }
            else
            {
                log.info("Page request denied: Student-User cannot access {}.", uri.toString());
                res.sendRedirect("StudentLogin");
            }
        }
        else
        {
            log.info("Page request denied: Logged-Out-User cannot access {}.", uri.toString());
            res.sendRedirect("login.jsp");
        }
    }
    
    public void destroy()
    {
        this.context = null;
    }

}
