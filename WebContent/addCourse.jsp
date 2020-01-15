<% String username = session.getAttribute("user").toString(); %>
<%@ include file = "header.jsp" %>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
	<a class="navbar-brand" href="Home">Attendance Management</a>
	<div class="collapse navbar-collapse" id="navbarText">
    	<ul class="navbar-nav mr-auto">
      		<li class="nav-item"><a class="nav-link" href="Home">Home</a></li>			
			<li class="nav-item active"><a class="nav-link" href="AddCourse">Add Course</a></li>
			<li class="nav-item"><a class="nav-link" href="RemoveCourse">Remove Courses</a></li>
			<li class="nav-item"><a class="nav-link" href="Statistics">Statistics</a></li>
    	</ul>
	</div>
  	<form name="log" action="Logout" method="POST">
		<button style="float: right" class="btn btn-primary active" type="submit">Logout</button>
	</form>
</nav><br>
<div class="main p-3 fadein">
	<h4>Add Course</h4><br>
	<form action="AddCourse" method="POST">
	<div class="form-row">
		<div class="form-group col-sm-6">
			<label style="color: white; text-shadow: 2px 2px 2px #000000;">Course Code</label>
			<input type="text" class="form-control ${ course_id }" placeholder="Course Code" value="${ id }" name="courseId">
		</div>
		<div class="form-group col-sm-6">
			<label style="color: white; text-shadow: 2px 2px 2px #000000;">Course Name</label>
			<input type="text" class="form-control ${ course_name }" placeholder="Course Name" value="${ cname }" name="coursename">
		</div>
		</div>
		<div class="form-group">						
   			<label style="color: white; text-shadow: 2px 2px 2px #000000;">Course Description</label>
    		<textarea class="form-control" placeholder="Course Description" name="coursedesc" rows="5"></textarea>
  		</div>
		<button type="submit" class="btn btn-primary active" style="float: right">Add</button>		
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