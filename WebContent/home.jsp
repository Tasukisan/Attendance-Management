<%@ include file = "header.jsp" %>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
	<a class="navbar-brand" href="Home">Attendance Management</a>
	<div class="collapse navbar-collapse" id="navbarText">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active"><a class="nav-link" href="Home">Home</a></li>			
			<li class="nav-item"><a class="nav-link" href="AddCourse">Add Course</a></li>
			<li class="nav-item"><a class="nav-link" href="RemoveCourse">Remove Courses</a></li>
			<li class="nav-item"><a class="nav-link" href="Statistics">Statistics</a></li>
			<%@ page import="struct.User" %>
			<% HttpSession ses = request.getSession();
			   if (((User)ses.getAttribute("user")).getUsername().equals("admin")){%>
				<li class="nav-item"><a class="nav-link" href="RemoveUser">Remove User</a></li>
			<%}%>
		</ul>
	</div>
	<form name="log" action="Logout" method="POST">
		<button style="float: right" class="btn btn-primary active" type="submit">Logout</button>
	</form>
</nav><br>
<div class="fadein main-wide">
	<h4>Courses</h4><br>
	<table class="table table-striped table-dark table-hover">
		<thead>
			<tr id="na">
				<th scope="col">Course Code</th>
				<th scope="col">Title</th>
				<th scope="col"> Description</th>
				<th scope="col">Number of Students</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${listOfCourses}">
				<tr id="${item.id}">
					<td><c:out value='${item.courseCode}'/></td>
					<td><c:out value='${item.title}'/></td>
					<td><c:out value='${item.description}'/></td>
					<td><c:out value='${item.students.size()}'/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<input type="hidden" name="pass" id="id" value="">
<script>
$('tr').click(function(){
	var id = this.id;
	if (id != "na")
	{
		$.ajax({
			url: "Home",
			data:{"pass":id},
			type: 'POST',
			success:function(){
				window.location = "Course";
			}
		});
	}
})
</script>
<%@ include file = "footer.jsp" %>