<%@ include file = "header.jsp" %>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
	<a class="navbar-brand" href="login.html">Attendance Management</a>
	<div id="navbarText">
   		<ul class="navbar-nav mr-auto">
   			<li class="nav-item"><a class="nav-link" href="Home">Home</a></li>
   			<li class="nav-item"><a class="nav-link active" href="credits.jsp">Team Members</a></li>
   		</ul>
	</div>
</nav>
<br><br>
<h1 id="page_title" class="credit"></h1>
<br><br>
<div class="main" style="text-align: center;">
	<form action="Accredit" method="POST">
		<button type="submit" name="sch"><h2 id="credits0"></h2></button><br><br>
	</form>
	<form action="Accredit" method="POST">
		<button type="submit" name="bec"><h2 id="credits1"></h2></button><br><br>
	</form>
	<form action="Accredit" method="POST">
		<button type="submit" name="lew"><h2 id="credits2"></h2></button><br><br>
	</form>
	<form action="Accredit" method="POST">
		<button type="submit" name="rob"><h2 id="credits3"></h2></button><br><br>
	</form>
	<form action="Accredit" method="POST">
		<button type="submit" name="arc"><h2 id="credits4"></h2></button><br><br>
	</form>
</div>
<script>
window.onload = pageTitle;
var classCode = 8;

var speed = 30;
var titleSpeed = 40;
var i = 0;
var k = 0;
var z = 0;

var titp = 'Attendance Management Team Members:';
var text0 = 'COMP 495 Matthew Schwartz';
var text1 = 'COMP 394 Benjamin Becker';
var text2 = 'COMP 394 Robert Lewis';
var text3 = 'COMP 294 Ben Roberts';
var text4 = 'COMP 294 Patrick Archer';

function pageTitle(){
	if (k < titp.length) 
	{
    	document.getElementById("page_title").innerHTML += titp.charAt(k);
    	k = k + 1;
    	setTimeout(pageTitle, titleSpeed);
  	}
	else
	{
		members();	
	}
}
function members(){
	
	for (var z = 0; z < 5; z++)
	{
			if (i < eval("text" + z).length)
			{
				document.getElementById("credits" + z).innerHTML += eval("text" + z).charAt(i);
				if (i == classCode)
				{
					document.getElementById("credits" + z).innerHTML += "<br>";			
				}
			}
	}	
	i++;
	setTimeout(members, speed);
}
</script>

<style>
button{
	background-color: Transparent;
	color: inherit;
	border: none;
	outline:none;
}
h1
{
	animation: fadeInAnimation ease 2s;
	animation-iteration-count: 1;
	animation-fill-mode: forwards;
}
form 
{
	animation: fadeInAnimation ease 6s;
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