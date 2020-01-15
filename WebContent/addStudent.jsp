<%@ include file = "header.jsp" %>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
	<a class="navbar-brand" href="Home">Attendance Management</a>
	<div class="collapse navbar-collapse" id="navbarText">
    	<ul class="navbar-nav mr-auto">
      		<li class="nav-item"><a class="nav-link" href="Home">Home<span class="sr-only"></span></a></li>
      		<li class="nav-item"><a class="nav-link" href="Course">${code}</a></li>
      		<li class="nav-item"><a class="nav-link active" href="AddStudent">Add Student</a></li>
      		<li class="nav-item"><a class="nav-link" href="RemoveStudent">Remove Students</a></li>
    	</ul>
	</div>
  	<form name="log" action="Logout" method="POST">
		<button style="float: right" class="btn btn-primary active" type="submit">Logout</button>
	</form>
</nav><br>
<div class="main p-3 fadein">
	<h4>Add Student to ${code}</h4><br>
	<form action="AddStudent" method="POST">
		<div class="form-row">
			<div class="form-group col-sm-6">
				<label id="white-text">Student User Name</label>
				<input type="text" class="form-control ${ stud_name }" placeholder="Student User Name" value="${ name }" name="username" id="username">
			</div>
			<div class="form-group col-sm-6">
				<label id="white-text">Student Email</label>
				<input type="email" class="form-control ${ stud_email }" placeholder="attendance@gmail.com" value="${ ema }" name="email">
			</div>
		</div>
		<div class="form-row">
			<div class="form-group col-sm-6">
				<label id="white-text">Student First Name</label>
				<input type="text" class="form-control ${ stud_first }"	placeholder="Student First Name" value="${ first }" name="firstname">
			</div>
			<div class="form-group col-sm-6">
				<label id="white-text">Student Last Name</label>
				<input type="text" class="form-control ${ stud_last }" placeholder="Student Last Name" value="${ last }" name="lastname">
			</div>
		</div>
		<div class="form-row">
			<div class="form-group col-sm-6">
				<label id="white-text">Assigned Password</label>
				<input type="text" class="form-control ${ stud_pass }"	placeholder="Assigned Password" value="${ pass }" name="password" id="password">
			</div>
			<div class="form-group col-sm-6">
				<label id="white-text">Re-Enter Assigned Password</label>
				<input type="text" class="form-control ${ stud_pass }" placeholder="Re-Enter Assigned Password" value="${ pass2 }" name="dupe" id="dupe">
			</div>
		</div>
		<button type="button" class="btn btn-primary active" style="float: left" id="autoGen">Auto-Generate Password</button>
		<button type="submit" class="btn btn-primary active" style="float: right">Add</button>
		<br><br>
		<% String err = (String)request.getAttribute("errorMessage"); %>
		<% if (null != err){ %>
		<div class="alert alert-primary" role="alert">
			<%=err %>
		</div><br>
		<% } %>
	</form>
	<div><br>
		<h4>Existing Students:</h4><h6 style="text-align: left" id="white-text">(Click to add)</h6>
		<table class="table table-striped table-dark table-hover">
			<tr id="na">
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
			</tr>
			<c:forEach items="${all_students}" var="item">
				<tr id="${item.getUsername()}">
					<td><c:out value="${item.getFirstName()}"/></td>
					<td><c:out value="${item.getLastName()}"/></td>
					<td><c:out value="${item.getEmail()}"/></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
<input type="hidden" name="pass_stud" id="id" value="">
<script>
document.getElementById("autoGen").addEventListener('click', function () {
    var pass = document.getElementById('password');
    var dupe = document.getElementById('dupe');
    var random = Math.floor(Math.random() * 999999999) + 100000000;
    pass.value = random;
    dupe.value = random;
});
$('tr').click(function(){
	var id = this.id;
	if (id != "na")
	{
		$.ajax({
			url: "AddStudent",
			data:{"pass_stud":id},
			type: 'POST',
			success:function(){
				window.location = "Course";
			}
		});
	}
})
</script>
<%@ include file = "footer.jsp" %>