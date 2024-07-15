<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page
	import="org.uta.sams.beans.UserBean,org.uta.sams.controller.UserController,java.util.ArrayList"%>
<%@ page import="java.lang.reflect.*"%>

<jsp:useBean id="userbean" scope="request"
	class="org.uta.sams.beans.UserBean" />
<jsp:setProperty name="userbean" property="*" />
<!DOCTYPE html>


<%
UserBean ub = null;
if (session.getAttribute("user") == null) {
	String user = request.getParameter("j_username");
	System.out.println("Welcome username>>>>>" + request.getParameter("j_username"));
	userbean.setUsername(user);
	System.out.println("Welcome username>>>>>" + userbean.getUsername());
	userbean.setPassword(request.getParameter("j_password"));
	UserController usercontroller = new UserController();
	ub = usercontroller.check(userbean);
} else {
	ub = (UserBean) session.getAttribute("user");
	System.out.println(ub.getRole()+"- Already logged in");

	
}
%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">









<link rel="stylesheet" href="../commons/sams/sample.css" type="text/css"
	title="International Stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>



<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">



</head>

<body id="page-top">

	<!-- Page Wrapper -->

	<div class="row wrapper">
	
		<% if(ub!=null){
		        request.getSession().setAttribute("user",ub); %>
		        <!-- Welcome Message !!!!!! -->
		
		   <%}else{
			   response.sendRedirect("login.jsp");
		   }
		%>

		<jsp:include page="../commons/header.jsp" />

		<div class="row mt-5 w-100">

			<div class="col-auto mt-3 w-25 h-100" style="position: fixed">

				<jsp:include page="../commons/left.jsp" />

			</div>
			<main class="col mt-5 content">




				<!-- Content Wrapper -->
				<div id="content-wrapper" class="d-flex col">
					<!-- Page Heading -->
					<div
						class="d-sm-flex align-items-center justify-content-center mb-4">
							<h1> Welcome</h1>
						

					</div>

					<!-- /.container-fluid -->

				</div>


			


</main>
</div>
</div>



</body>

</html>