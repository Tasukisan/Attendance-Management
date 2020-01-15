<%! String error = ""; %>
<%@ include file = "header.jsp" %>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg ">
	<a class="navbar-brand" href="login.jsp">Attendance Management</a>
	<div>
	    <ul class="navbar-nav">
	      <li class="nav-item active">
	        <a class="nav-link" href="Login">Login</a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="Register">Register</a>
	      </li>
	    </ul>
	</div>
</nav><br>
<div class="main fadein">
	<form class="p-3" action="Login" method="POST">
		<h4>Login</h4><br>
		<div class="form-group sm">
			<label id="white-text">User Name</label>
			<input type="text" class="form-control ${ error }" placeholder="User Name" name="username">
		</div>
		<div class="form-group sm">
			<label id="white-text">Password</label>
			<input type="password" class="form-control ${ error }" placeholder="Password" name="password">
		</div>
		<button class="btn btn-primary active" type="submit" style="float: right">Login</button>
		<br><br>
		<% String err = (String)request.getAttribute("error"); %>
		<% if (null != err){ %>
		<div id="error" class="alert alert-primary" role="alert">
	  		Incorrect User Name or Password.
		</div>	
		<% } %>
    </form>
</div>
<%@ include file = "footer.jsp" %>	