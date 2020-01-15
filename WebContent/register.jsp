<%@ include file = "header.jsp" %>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
	<a class="navbar-brand" href="login.html">Attendance Management</a>
	<div id="navbarText">
   		<ul class="navbar-nav mr-auto">
   			<li class="nav-item"><a class="nav-link" href="Login">Login</a></li>
   			<li class="nav-item active"><a class="nav-link" href="Register">Register</a></li>
   		</ul>
	</div>
</nav><br>
<div class="main p-3 fadein">
	<h4>Register New User</h4><br>
	<form action="Register" method="POST">
		<div class="form-row">
			<div class="form-group col-sm-6">
				<label id="white-text">User Name</label>
				<input type="text" class="form-control ${ auth_name }" placeholder="User Name" value="${ name }" name="username" id="username">
			</div>
			<div class="form-group col-sm-6">
				<label id="white-text">Email</label>
				<input type="email" class="form-control ${ auth_email }" placeholder="attendance@gmail.com" value="${ ema }" name="email">
			</div>
		</div>
		<div class="form-row">
			<div class="form-group col-sm-6">
				<label id="white-text">First Name</label>
				<input type="text" class="form-control ${ auth_first }"	placeholder="First Name" value="${ first }" name="firstname">
			</div>
			<div class="form-group col-sm-6">
				<label id="white-text">Last Name</label>
				<input type="text" class="form-control ${ auth_last }" placeholder="Last Name" value="${ last }" name="lastname">
			</div>
		</div>
		<div class="form-row">
			<div class="form-group col-sm-6">
				<label id="white-text">Password</label>
				<input type="password" class="form-control ${ auth_pass }"	placeholder="Password" name="password">
			</div>
			<div class="form-group col-sm-6">
				<label id="white-text">Re-Enter Password</label>
				<input type="password" class="form-control ${ auth_pass }" placeholder="Re-Enter Password" name="dupe">
			</div>
		</div>
		<button type="submit" class="btn btn-primary active" style="float: right">Register</button>
		<br><br>
		<% String err = (String)request.getAttribute("errorMessage"); %>
		<% if (null != err){ %>
		<div class="alert alert-primary" role="alert">
			<%=err %>
		</div>
		<% } %>
	</form>
</div>
<%@ include file = "footer.jsp" %>