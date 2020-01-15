<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="header.jsp"%>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
	<a class="navbar-brand" href="Home">Attendance Management</a>
	<div class="collapse navbar-collapse" id="navbarText">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item"><a class="nav-link" href="Home">Home<span class="sr-only"></span></a></li>
			<li class="nav-item"><a class="nav-link" href="Course">${course.courseCode}</a></li>
			<li class="nav-item active"><a class="nav-link" href="Attendance">Take Attendance</a></li>
		</ul>
	</div>
	<form name="log" action="Logout" method="POST">
		<button style="float: right" class="btn btn-primary active"
			type="submit">Logout</button>
	</form>
</nav>
<br>
<br>
<div class="main">
	<h4>Take Attendance for ${course.courseCode}</h4>
	<br>
	<table class="table table-striped table-dark">
		<tr>
			<th>Course Name</th>
			<th>Date</th>
		</tr>
		<tr>
			<td id="courseCode" code="${course.courseCode}">${course.title}</td>
			<td id="date" date="${date}">${date}</td>
		</tr>
	</table>
	<table class="table table-striped table-dark">
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Status</th>
			<th>Current Status</th>
		</tr>
		<c:forEach items="${students.keySet()}" var="student">
			<tr studentID="${student.id}">
				<td style="color: white;">${student.firstName}</td>
				<td style="color: white;">${student.lastName}</td>
				<td style="color: white;"><input class="imgbutton" type="image"
					src="static/present.png" alt="Present" value="present"> <input
					class="imgbutton" type="image" src="static/excused.png"
					alt="Excused" value="excused"> <input class="imgbutton"
					type="image" src="static/absent.png" alt="Absent" value="absent"></td>
				<td>${students.get(student)}</td>
			</tr>
		</c:forEach>
	</table>
	<table class="table table-striped table-dark">
		<tr>
			<th>Present</th>
			<th>Excused</th>
			<th>Absent</th>
			<th>Remaining</th>
		</tr>
		<tr>
			<td>${totalPresent }</td>
			<td>${totalExcused }</td>
			<td>${totalAbsent }</td>
			<td>${totalUnassigned }</td>
		</tr>
	</table>
	<table class="table table-striped table-dark">
		<tr>
			<td>
				<button style="float: right" class="btn btn-outline-primary actionbutton" type="submit" value="cancel">Cancel</button>
			</td>
			<td>
				<button style="float: right" class="btn btn-outline-primary actionbutton" type="submit" value="accept">Submit</button>
			</td>
		</tr>
	</table>
</div>
<%@ include file="footer.jsp"%>