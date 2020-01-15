<%@ include file = "header.jsp" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
	<a class="navbar-brand" href="Home">Attendance Management</a>
	<div class="collapse navbar-collapse" id="navbarText">
    	<ul class="navbar-nav mr-auto">
      		<li class="nav-item"><a class="nav-link" href="Home">Home<span class="sr-only"></span></a></li>
      		<li class="nav-item"><a class="nav-link" href="Course">${code}<span class="sr-only"></span></a></li>
      		<li class="nav-item active"><a class="nav-link" href="Student">${student_lastname}</a></li>
      		<li class="nav-item"><a class="nav-link" href="EditStudent">Edit Student</a></li>
    	</ul>
	</div>
  	<form name="log" action="Logout" method="POST">
		<button style="float: right" class="btn btn-primary active" type="submit">Logout</button>
	</form>
</nav><br><br>
	<div class="main-wide">
		<h4>${student_username}</h4><br>
		<table class="table table-striped table-dark table-hover">
			<thead>
				<tr>
					<th scope="col">First Name</th>
					<th scope="col">Last Name</th>
					<th scope="col">Email</th>
					<th scope="col">Attendance</th>
					<th scope="col">Status</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listOfAttendance}" var="item">
					<tr id="${student_username}">
						<td scope="row"><c:out value='${student_firstname}'/></td>
						<td><c:out value='${student_lastname}'/></td>
						<td><c:out value='${student_email}'/></td>
						<td><c:out value='${item.classtime}'/></td>
						<td><c:out value='${item.status}'/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
<%@ include file = "footer.jsp" %>