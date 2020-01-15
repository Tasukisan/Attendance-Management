
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

/**
 * @author Matthew Schwartz Assists the AuthFilter.java class.
 */
@WebFilter("/LoginFilter")
public class LoginFilter implements Filter
{

	private ServletContext context;

	public void init(FilterConfig fConfig) throws ServletException
	{
		this.context = fConfig.getServletContext();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		chain.doFilter(request, response);
	}

	public void destroy()
	{
		this.context = null;
	}

}