


<%
String jsppath = request.getContextPath() + "/jsp/commons/";
%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page
	import="org.uta.sams.beans.*,org.uta.sams.controller.ProgramController,java.util.ArrayList"%>
<jsp:useBean id="searchbean" scope="request"
	class="org.uta.sams.beans.SearchBean" />
<jsp:setProperty name="searchbean" property="*" />



<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="<%=jsppath%>sams/sample.css"
	type="text/css" title="International Stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<title>Sams</title>
<style type="text/css">
</style>
</head>



<body>

	<div class="row wrapper">

    <jsp:include page="/jsp/commons/header.jsp" />





		<div class="row mt-5 w-100 ">

			<div class="col-auto mt-3 w-auto h-100">
				<script type="text/javascript"
					src="<%=jsppath%>../commons/sams/fsmenu.js"></script>

				<jsp:include page="/jsp/commons/left.jsp" />



			</div>




	


			<main class="col mt-5 content">
			
			<div class="row">
			
					<!-- Page Heading -->
					<div
						class="d-sm-flex align-items-start justify-content-between mb-4">
						<h1 class="h3 mb-0 text-gray-800 ml-3">Delete Program List</h1>

					</div>
				</div>
			<table class="table">

				<thead>


					<tr>
						<th>Program Name</th>
						<th width="15%">Term</th>
						<th width="15%">Type</th>
						<th width="15%">Region</th>
						<th width="15%">Country</th>
						<th width="25%">Departments</th>
					</tr>
				</thead>


				<%
				String contextpath = request.getContextPath() + "/";
				String txtaction = "";
				ProgramController programcontroller = new ProgramController();
				ArrayList programlist = programcontroller.searchPrograms(searchbean);
				if (programlist != null) {
					request.getSession().setAttribute("programlist", programlist);
					for (int i = 0; i < programlist.size(); i++) {
						if (programlist.get(i) == null)
					continue;
						ProgramBean program = (ProgramBean) programlist.get(i);
				%>


				<tr valign="top">

<tr><td><a href="<%=contextpath%>jsp/delete/deleteProgram_detail.jsp?program_name=<%=program.getProgramName()%>&term=<%=program.getTerm()%>"><%=program.getProgramName()%></a></td>

					
					<td width="15%"><%=program.getTerm()%></td>
					<td width="15%"><%=program.getProgramType()%></td>
					<td width="15%"><%=program.getRegion()%></td>
					<td width="15%"><%=program.getCountry()%></td>
					<td width="25%">
						<%
						for (int j = 0; j < program.getSubjects().length; j++) {
						%> <%=program.getSubjects()[j]%>,
						<%}%>
					</td>
				</tr>


				<%
				}
				}

				else {
				%>

			</table>
			<div>Some Exception Occured. No data is presently available to
				present</div>

			<%
			}
			%> </main>
	
		</div>
	</div>
	


</body>
</html>

