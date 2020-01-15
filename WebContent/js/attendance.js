(function() {
	'use strict';
	$(function() {
		$('.imgbutton').click(
				function(event) {
					var row = $(this).closest("tr");
					var studentID = row.attr("studentID");
					var status = $(this).attr("value")
					var courseCode = $("#courseCode").attr("code");
					var date = $("#date").attr("date");
					console.log("Setting attendance to " + status + " for "
							+ studentID);
					$.post("Attendance", {
						studentID : studentID,
						status : status,
						courseCode : courseCode,
						classTime : date
					}).done(function() {
						console.log("Success");
						window.location.reload(true);
					}).fail(function(err) {
						console.log("Error", err)
					});
				});
		$('.actionbutton').click(
				function(event) {
					var row = $(this).closest("tr");
					var status = $(this).attr("value")
					var courseCode = $("#courseCode").attr("code");
					var date = $("#date").attr("date");
					console.log("Setting attendance to " + status);
					$.post("AttendanceConfirmation", {
						status : status,
						courseCode : courseCode,
						classTime : date
					}).done(function() {
						console.log("Success");
						window.location.replace("Home");
					}).fail(function(err) {
						console.log("Error", err)
					});
				});
	});
}());