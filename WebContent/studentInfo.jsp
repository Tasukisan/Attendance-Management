<%@ include file = "header.jsp" %>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
	<a class="navbar-brand" href="Home">Attendance Management</a>
	<div class="collapse navbar-collapse" id="navbarText">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active"><a class="nav-link" href="StudentLogin">Info<span class="sr-only"></span></a></li>
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
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${studentsListOfCourses}">
				<tr id="${item.courseCode}">
					<td><c:out value='${item.courseCode}'/></td>
					<td><c:out value='${item.title}'/></td>
					<td><c:out value='${item.description}'/></td>
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
			url: "StudentLogin",
			data:{"pass":id},
			type: 'POST',
			success:function(){
				window.location = "StudentCourse";
			}
		});
	}
})
</script>
<%@ include file = "footer.jsp" %>