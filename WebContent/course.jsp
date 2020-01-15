<%@ include file = "header.jsp" %>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
	<a class="navbar-brand" href="Home">Attendance Management</a>
	<div class="collapse navbar-collapse" id="navbarText">
    	<ul class="navbar-nav mr-auto">
      		<li class="nav-item"><a class="nav-link" href="Home">Home<span class="sr-only"></span></a></li>
      		<li class="nav-item active"><a class="nav-link" href="Course">${code}<span class="sr-only"></span></a></li>
      		<li class="nav-item"><a class="nav-link" href="AddStudent">Add Student</a></li>
      		<li class="nav-item"><a class="nav-link" href="RemoveStudent">Remove Students</a></li>
    	</ul>
	</div>
  	<form name="log" action="Logout" method="POST">
		<button style="float: right" class="btn btn-primary active" type="submit">Logout</button>
	</form>
</nav><br><br>
<div class="main fadein">
	<form name="log" action="Attendance" method="GET">
		<h4 style="display: inline-block">${title}</h4>
		<button style="float: right" class="btn btn-primary active" type="submit">Take Attendance</button>
	</form><br>
	<table class="table table-striped table-dark table-hover">
		<tr id="na">
			<th scope="col">First Name</th>
			<th scope="col">Last Name</th>
			<th scope="col">Email</th>
		</tr>
		<c:forEach items="${listOfStudents}" var="item">
			<tr id="${item.getUsername()}">
				<td scope="col"><c:out value="${item.getFirstName()}"/></td>
				<td scope="col"><c:out value="${item.getLastName()}"/></td>
				<td scope="col"><c:out value="${item.getEmail()}"/></td>
			</tr>
		</c:forEach>
	</table>
</div>
<input type="hidden" name="pass_stud" id="id" value="">
<script>
$('tr').click(function(){
	var id = this.id;
	if (id != "na")
	{
		$.ajax({
			url: "Course",
			data:{"pass_stud":id},
			type: 'POST',
			success:function(){
				window.location = "Student";
			}
		});
	}
})
</script>
<%@ include file = "footer.jsp" %>