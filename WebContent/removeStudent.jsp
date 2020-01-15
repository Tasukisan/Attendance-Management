<%@ include file = "header.jsp" %>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
	<a class="navbar-brand" href="Home">Attendance Management</a>
	<div class="collapse navbar-collapse" id="navbarText">
    	<ul class="navbar-nav mr-auto">
      		<li class="nav-item"><a class="nav-link" href="Home">Home<span class="sr-only"></span></a></li>
      		<li class="nav-item"><a class="nav-link" href="Course">${code}<span class="sr-only"></span></a></li>
      		<li class="nav-item"><a class="nav-link" href="AddStudent">Add Student</a></li>
      		<li class="nav-item active"><a class="nav-link" href="RemoveStudent">Remove Students</a></li>
    	</ul>
	</div>
  	<form name="log" action="Logout" method="POST">
		<button style="float: right" class="btn btn-primary active" type="submit">Logout</button>
	</form>
</nav><br><br>
<div class="main fadein">
	<h4>Remove Students from ${code}</h4><br>
	<div>
		<form class="p-3" action="RemoveStudent" method="POST">
			<table class="table table-striped table-dark table-hover">
				<tr>
					<th scope="col">First Name</th>
					<th scope="col">Last Name</th>
					<th scope="col">Email</th>
					<th scope="col">Remove</th>
				</tr>
				<c:forEach items="${listOfStudents}" var="item">
					<tr>
						<td scope="col"><c:out value="${item.getFirstName()}"/></td>
						<td scope="col"><c:out value="${item.getLastName()}"/></td>
						<td scope="col"><c:out value="${item.getEmail()}"/></td>
						<td style="text-align: center"><input type="checkbox" name="${item.getUsername()}"></td>
					</tr>
				</c:forEach>
			</table>
			<button style="float: right" class="btn btn-primary active" type="submit">Remove Students</button>
			<br><br>
			<% String err = (String)request.getAttribute("error"); %>
			<% if (null != err){ %>
			<div id="error" class="alert alert-primary" role="alert">
	  			No students have been selected.
			</div>	
			<% } %>
		</form>
	</div>
</div>
<%@ include file = "footer.jsp" %>