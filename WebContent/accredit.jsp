<%String member = "Matthew Schwartz"; %>
<%@ include file = "header.jsp" %>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
	<a class="navbar-brand" href="login.html">Attendance Management</a>
	<div id="navbarText">
   		<ul class="navbar-nav mr-auto">
   			<li class="nav-item"><a class="nav-link" href="Home">Home</a></li>
   			<li class="nav-item"><a class="nav-link" href="credits.jsp">Team Members</a></li>
   			<li class="nav-item"><a class="nav-link active" href="">${member}</a></li>
   		</ul>
	</div>
</nav>
<br><br>
<div class="main">
	<table class="table table-striped table-dark">
		<tr>
			<th scope="col">Week Number</th>
			<th scope="col">Contribution</th>
		</tr>
		<c:forEach items="${work}" var="item">
			<tr>
				<td scope="col"><c:out value="${item.getWeek()}"/></td>
				<td scope="col"><c:out value="${item.getWork()}"/></td>
			</tr>
		</c:forEach>
	</table>
</div>
<style>
table 
{
	animation: fadeInAnimation ease 2s;
	animation-iteration-count: 1;
	animation-fill-mode: forwards;
}
@keyframes fadeInAnimation 
{ 
  	0% 
	{ 
		opacity: 0; 
	} 
	100% 
	{ 
		opacity: 1; 
	} 
} 
</style>
<%@ include file = "footer.jsp" %>