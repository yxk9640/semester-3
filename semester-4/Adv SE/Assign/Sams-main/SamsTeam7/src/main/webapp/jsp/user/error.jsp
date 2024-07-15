<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="org.uta.sams.beans.UserBean,org.uta.sams.controller.ProgramController,java.util.ArrayList"%>
<%@ page import = "java.lang.reflect.*" %>



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
    </head>
    <body>
<table valign="top" cellpadding="0"align="center" cellspacing="0" width='759'>
    <tr><td>
          <jsp:include page="/jsp/commons/header.jsp"/>
    </td></tr>
</table>    
 <table valign="top" align="center" width="759" height="425"cellspacing="0" cellpadding="0"><tr>
<td style="border-right: 1px solid rgb(255, 255, 255);" bgcolor="#666666" height="92" valign="top" width="331">
 <script type="text/javascript" src="/jsp/commons/sams/fsmenu.js"></script>
             
            
 </td>
<td style="padding-left:5px" valign="middle">
   

<table width="350" height="128" border="1" cellpadding=0 cellspacing=0 bordercolor="#990000">
      <tr>
        
		<td height="21" colspan=2 bgcolor="#990000">&nbsp;</td>
		
      </tr>
      <tr bordercolor="#ffffff">
        <td  bgcolor="#FFFFFF"><img src="<%=request.getContextPath()%>/jsp/user/login_images/login_image.jpg" width="169" height="194">          </td>
        <td >
		<!--<form name="form1" action="/Sams/jsp/user/login_msg.jsp" method="post">-->
	    <form name="form1" action='<%= response.encodeURL("j_security_check") %>' method="post">	
            <table width="200" border="0" cellpadding="5" cellspacing="0" bordercolor="#666666">
            <tr bordercolor="#ffffff">
             <td align="center"><a href="/Sams/jsp/add/addProgram_Form.jsp">Login Failed</a></td>
             
            </tr>
          </table>
		  </form>
		   </td>
	  </tr>
    </table>
    
    
    </td></tr>
</table>
    <table align="center" cellpadding="0" cellspacing="0"><tr><td>
    <jsp:include page="/jsp/commons/footer.jsp"/>
    </td></tr></table>

    
    </body>
</html>
