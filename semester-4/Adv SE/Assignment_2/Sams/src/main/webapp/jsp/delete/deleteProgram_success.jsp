<%
String jsppath = request.getContextPath() + "/jsp/commons/";
%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="org.uta.sams.beans.*,org.uta.sams.controller.ProgramController,java.util.ArrayList"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sams</title>
    <style>
<!--
.formtable {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #333333;
	font-weight: bold;
}
.width1 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: normal;
	width: 180px;
}
.errorcode {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: bold;
	color: #FF0000;
}
.style1 {font-family: Arial, Helvetica, sans-serif; font-size: 12px; color: #333333; font-weight: bold; }
.normalstyle {font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #333333; font-weight: bold;}
-->
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
				
<table class="table">
   <tr >
            <% String name=""; 
			if(session.getAttribute("user")!=null)name=((UserBean)session.getAttribute("user")).getUsername(); 
            //name=request.getRemoteUser();
            %>
   
   
   </tr>
  
          
 <tr><td colspan="2">
    <%
   //String contextpath=request.getContextPath()+"/"; 
   String programID = request.getParameter("programID");
   String termID=request.getParameter("termID");
   String getClick = request.getParameter("delete");
   ProgramController programcontroller= new ProgramController();
    String getResponse;
  if(getClick.equalsIgnoreCase("Delete"))
   {
        getResponse = programcontroller.deleteProgram(programID,termID);
      
   } 
   else
   {
    getResponse = "Delete is canceled"; 

   }           
    %>
    <br>
    <br>
    <h1 class="normalstyle" align="center"> <%=getResponse%>       </h1>
     <% response.setHeader("Refresh", "1;url=../user/welcome.jsp"); %>
    <br>
    <br>
    <%--
    This example uses JSTL, uncomment the taglib directive above.
    To test, display the page like this: index.jsp?sayHello=true&name=Murphy
    --%>
    <%--
    <c:if test="${param.sayHello}">
        <!-- Let's welcome the user ${param.name} -->
        Hello ${param.name}!
    </c:if>
    --%>
    </td></tr></table>
    </main>
    </div>
    </div>
   


    
    </body>
</html>
