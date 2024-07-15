<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<html>
<head>
    <title>Time for scheduling an appointment has passed!</title>
    <link rel="stylesheet"
	href="components/bootstrap3/css/bootstrap.css">
	<script type="text/javascript"
	src="components/bootstrap3/js/bootstrap.min.js"></script>
</head>
<div class="container" style="margin-top:120px">
		<div class="jumbotron">
			<span>
				<h3>Time for scheduling an appointment has passed! You cannot schedule appointment at this time.</h3>
			</span>
   </div>
</div>
<%@include file="templates/footer.jsp"%>