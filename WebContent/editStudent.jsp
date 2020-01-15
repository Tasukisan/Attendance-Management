<%@ include file = "header.jsp" %>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
	<a class="navbar-brand" href="Home">Attendance Management</a>
	<div class="collapse navbar-collapse" id="navbarText">
    	<ul class="navbar-nav mr-auto">
      		<li class="nav-item"><a class="nav-link" href="Home">Home<span class="sr-only"></span></a></li>
      		<li class="nav-item"><a class="nav-link" href="Course">${code}<span class="sr-only"></span></a></li>
      		<li class="nav-item"><a class="nav-link" href="Student">${student_lastname}</a></li>
      		<li class="nav-item active"><a class="nav-link" href="EditStudent">Edit Student</a></li>
    	</ul>
	</div>
  	<form name="log" action="Logout" method="POST">
		<button style="float: right" class="btn btn-primary active" type="submit">Logout</button>
	</form>
</nav><br><br>
<div class="main fadein p-3">
	<h4>Edit ${student_firstname} ${student_lastname}</h4><br>
	<form action="EditStudent" method="POST">
		<div class="form-row">
			<div class="form-group col-sm-6">
				<label id="white-text">Student User Name</label>
				<input type="text" class="form-control" value="${student_username}" name="username" disabled>
			</div>
			<div class="form-group col-sm-6">
				<label id="white-text">Student Email</label>
				<input type="text" class="form-control" value="${student_email}" name="email" disabled>
			</div>
		</div>
		<div class="form-row">
			<div class="form-group col-sm-6">
				<label id="white-text">Student First Name</label>
				<input type="text" class="form-control" placeholder="Student First Name" value="${student_firstname}" name="firstname">
			</div>
			<div class="form-group col-sm-6">
				<label id="white-text">Student Last Name</label>
				<input type="text" class="form-control" placeholder="Student Last Name" value="${student_lastname}" name="lastname">
			</div>
		</div>
		<div class="form-row">
			<div class="form-group col-sm-6">
				<label id="white-text">Student Password</label>
				<input type="password" class="form-control" placeholder="Student Password" name="password">
			</div>
			<div class="form-group col-sm-6">
				<label id="white-text">Re-Enter Student Password</label>
				<input type="password" class="form-control" placeholder="Re-Enter Password" name="dupe">
			</div>
		</div>
		<button type="submit" class="btn btn-primary active" style="float: right">Save Changes</button>
		<br><br>
		<% String err = (String)request.getAttribute("error"); %>
		<% if (null != err){ %>
		<div id="error" class="alert alert-primary" role="alert">
  			Please ensure all information is valid before submitting.
		</div>	
		<% } %>
	</form>
</div>
<%@ include file = "footer.jsp" %>