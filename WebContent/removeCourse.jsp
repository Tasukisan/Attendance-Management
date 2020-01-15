<%@ include file = "header.jsp" %>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
	<a class="navbar-brand" href="Home">Attendance Management</a>
	<div class="collapse navbar-collapse" id="navbarText">
    	<ul class="navbar-nav mr-auto">
      		<li class="nav-item"><a class="nav-link" href="Home">Home</a></li>			
			<li class="nav-item"><a class="nav-link" href="AddCourse">Add Course</a></li>
			<li class="nav-item active"><a class="nav-link" href="RemoveCourse">Remove Courses</a></li>
			<li class="nav-item"><a class="nav-link" href="Statistics">Statistics</a></li>
    	</ul>
	</div>
  	<form name="log" action="Logout" method="POST">
		<button style="float: right" class="btn btn-primary active" type="submit">Logout</button>
	</form>
</nav><br><br>
<div class="main fadein">
	<h4>Remove Courses from ${username}</h4><br>
	<form class="p-3" action="RemoveCourse" method="POST">
		<table class="table table-striped table-dark">
			<thead>
				<tr>
					<th scope="col">Course Code</th>
					<th scope="col">Title</th>
					<th scope="col">Remove</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${listOfCourses}">
					<tr>
						<td scope="row"><c:out value='${item.courseCode}'/></td>
						<td><c:out value='${item.title}'/></td> 
						<td style="text-align: center"><input type="checkbox" name="${item.getCourseCode()}"></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<button style="float: right" class="btn btn-primary active" type="submit">Remove Courses</button>
		<br><br>
		<% String err = (String)request.getAttribute("error"); %>
			<% if (null != err){ %>
			<div id="error" class="alert alert-primary" role="alert">
	  			No courses have been selected.
			</div>	
			<% } %>
	</form>
</div>
<%@ include file = "footer.jsp" %>