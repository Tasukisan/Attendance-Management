<%@ include file = "header.jsp" %>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
	<a class="navbar-brand" href="Home">Attendance Management</a>
	<div class="collapse navbar-collapse" id="navbarText">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item"><a class="nav-link" href="StudentLogin">Info<span class="sr-only"></span></a></li>
			<li class="nav-item active"><a class="nav-link" href="StudentCourse">Attendance<span class="sr-only"></span></a></li>
		</ul>
	</div>
	<form name="log" action="Logout" method="POST">
		<button style="float: right" class="btn btn-primary active" type="submit">Logout</button>
	</form>
</nav><br>
<div class="fadein main">
	<h4>Attendance for ${StudentCode}</h4>
		<h1>${student_username}</h1><br>
		<table class="table table-striped table-dark table-hover">
			<thead>
				<tr>
					<th scope="col">Attendance</th>
					<th scope="col">Status</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${StudentAttendanceList}" var="item">
					<tr>
						<td><c:out value='${item.classtime}'/></td>
						<td><c:out value='${item.status}'/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
<%@ include file = "footer.jsp" %>