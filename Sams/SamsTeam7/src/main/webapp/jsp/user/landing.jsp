<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="org.uta.sams.beans.UserBean,org.uta.sams.controller.UserController,java.util.ArrayList"%>
<%@ page import = "java.lang.reflect.*" %>

<jsp:useBean id="userbean" scope="request" class="org.uta.sams.beans.UserBean" />
<jsp:setProperty name="userbean" property="*"/>


<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
 
  <%
          String user=request.getParameter("j_username");
           System.out.println("Landing Page_username>>>>>"+request.getParameter("j_username"));
           userbean.setUsername(user);
           System.out.println("Landing Page_set_username>>>>>"+userbean.getUsername());
           userbean.setPassword(request.getParameter("j_password"));
   UserController usercontroller=new UserController();
   UserBean ub=usercontroller.check(userbean);
           
%> 

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sams</title>
    </head>
    <body>
<div class="navbar" >

<jsp:include page="/jsp/commons/header.jsp"/>

</div>    
 <div class="landing-image-container">



 <div class="landing-content-container">
  <img src="../commons/sams/uta_horse.jpeg" style="transform: scaleX(-1); width: 80%; height:100%" />
 <div style="display: flex; flex-direction: column; justify-content: center; align-items: center; margin-left: 40px;">
  <button class="landing-button" onclick="window.location.href='login.jsp';" >Login</button>
  

</div>
</div>

 <td style="border-right: 1px solid rgb(255, 255, 255);" bgcolor="#666666" height="92" valign="top" width="331"> 
  <script type="text/javascript" src="/jsp/commons/sams/fsmenu.js"></script>
 <jsp:include page="/jsp/commons/left.jsp"/> 
 </td>
 <td style="padding-left:5px" height="425">
 
<% if(ub!=null){
        request.getSession().setAttribute("user",ub); %>
        Welcome Message !!!!!!
      
   <%}else{
	   response.sendRedirect("login.jsp");
   }%>
    
<This example uses JSTL, uncomment the taglib directive above.
    To test, display the page like this: index.jsp?sayHello=true&name=Murphy >
 <c:if test="${param.sayHello}">
        <!-- Let's welcome the user ${param.name} -->
        Hello ${param.name}!
    </c:if>

    
     <!--  </td></tr> -->
</div>
    <table align="center" cellpadding="0" cellspacing="0"><tr><td class ="footer-container">
    <jsp:include page="/jsp/commons/footer.jsp"/>
    </td></tr></table>

    
    </body>
</html>
