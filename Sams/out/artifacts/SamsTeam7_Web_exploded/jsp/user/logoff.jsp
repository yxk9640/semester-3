<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="org.uta.sams.beans.ProgramBean,org.uta.sams.controller.ProgramController,java.util.ArrayList"%>
<%@ page import = "java.lang.reflect.*" %>



<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%--<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> --%>
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
 <%session.invalidate();%>
 <% response.setHeader("Refresh", "1;url=login.jsp"); %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sams</title>
    </head>
    <body>
   
<div style ="display:flex">

          <jsp:include page="/jsp/commons/header.jsp"/>




 
    
     
     
 

   <div class="container">
  <div class="px-4 py-5 px-md-5 text-center text-lg-start" style="background-color: hsl(0, 0%, 96%)">
 
      <div class="col gx-lg-5 align-items">
        <div class=" mb-5 mb-lg-0">
          <h1 class="my-5 display-3 fw-bold ls-tight">
            <span class="text-primary">You are successfully logged off!!</span>
          </h1>

          </div>
        </div>
      </div>

       
    </div>
  </div>



 
   




    <table align="center" cellpadding="0" cellspacing="0"><tr><td class ="footer-container">
    <jsp:include page="/jsp/commons/footer.jsp"/>
    </td></tr></table>
  




    </body>
</html>
