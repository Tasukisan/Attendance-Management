<%@ include file="header.jsp"%>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
	<a class="navbar-brand" href="Home">Attendance Management</a>
	<div class="collapse navbar-collapse" id="navbarText">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item"><a class="nav-link" href="Home">Home</a></li>			
			<li class="nav-item"><a class="nav-link" href="AddCourse">Add Course</a></li>
			<li class="nav-item"><a class="nav-link" href="RemoveCourse">Remove Courses</a></li>
			<li class="nav-item active"><a class="nav-link" href="Statistics">Statistics</a></li>
		</ul>
	</div>
	<form name="log" action="Logout" method="POST">
		<button style="float: right" class="btn btn-primary active"
			type="submit">Logout</button>
	</form>
</nav><br>
<h1>Statistics</h1><br>
<div class="main fadein">
	<h3 style="float: left">User Statistics</h3><br>
	<table class="table table-striped table-dark table-hover">
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Present</th>
			<th>Absent</th>
			<th>Excused</th>
		</tr>
		<c:forEach items="${users}" var="item">
			<tr>
				<td scope="col">${item.firstName }</td>
				<td scope="col">${item.lastName }</td>
				<td scope="col"><c:out value="${item.getStat(\"present\")}" /></td>
				<td scope="col"><c:out value="${item.getStat(\"absent\")}" /></td>
				<td scope="col"><c:out value="${item.getStat(\"excused\")}" /></td>
			</tr>
		</c:forEach>
	</table><br>
	<h3 style="float: left">Course Statistics</h3><br>
	<table class="table table-striped table-dark table-hover">
		<tr>
			<th>Course Code</th>
			<th>Present</th>
			<th>Absent</th>
			<th>Excused</th>
		</tr>
		<c:forEach items="${courses}" var="item">
			<tr>
				<td scope="col">${item.courseCode }</td>
				<td scope="col"><c:out value="${item.getStat(\"present\")}" /></td>
				<td scope="col"><c:out value="${item.getStat(\"absent\")}" /></td>
				<td scope="col"><c:out value="${item.getStat(\"excused\")}" /></td>
			</tr>
		</c:forEach>
	</table>
</div>
<%@ include file="footer.jsp"%>