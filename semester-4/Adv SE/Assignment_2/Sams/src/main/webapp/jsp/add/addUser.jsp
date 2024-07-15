<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="org.uta.sams.beans.*,org.uta.sams.controller.UserController,java.util.ArrayList"%>
<%@ page import = "java.lang.reflect.*" %>


<jsp:useBean id="Userbean" scope="request" class="org.uta.sams.beans.UserBean" />
<jsp:setProperty name="Userbean" property="*"/>


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
<% 
UserController UserController = new UserController();




%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sams</title>
		<style type="text/css">
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
.style1 {font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #333333; font-weight: bold; }
.normalStyle {font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #333333; }
-->
</style>
    </head>
    <script>
        function yesFunction()
        {
        
       <%--  window.location.href = "uploadImage_Form.jsp?prg=<%= Userbean.getUserName()%>&term=<%= Userbean.getTerm()%>";
        
         --%>
        }
        
        function noFunction()
        {
        
        window.location.href = "../../jsp/user/welcome.jsp";
        
        }
    </script>
    <body>
<table valign="top" cellpadding="0"align="center" cellspacing="0" width='759'>
    <tr><td>
          <jsp:include page="/jsp/commons/header.jsp"/>
    </td></tr>
</table>    
 <table valign="top" align="center" width="759" cellspacing="0" cellpadding="0"><tr>
<td style="border-right: 1px solid rgb(255, 255, 255);" bgcolor="#666666" height="92" valign="top" width="331">
 <script type="text/javascript" src="/jsp/commons/sams/fsmenu.js"></script>
              <jsp:include page="/jsp/commons/left.jsp"/>
            
 </td>
<td style="padding-left:5px">
    
    
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
    
    <table border="0" width="100%">
        
        <tr >
            
            <% String name=""; if(session.getAttribute("user")!=null)name=((UserBean)session.getAttribute("user")).getUsername(); 
            //name=request.getRemoteUser();
            %>
        <td  width="322" class="formtable" ><span>Welcome <%=name%></span><span>.</span> </td>
        <td width="91" class="formtable" align="right"><span class="style4"><a href="../user/logoff.jsp" >Sign Out </a></span></td>
      </tr>
	  <tr class="formtable">
	     <td height="1" bgcolor="#990000" colspan="2"></td>
	  </tr>
	  <tr class="formtable">
	     <td height="1" colspan="2" align="center" valign="middle" class="style1">Add User</td>
	  </tr>
	  <tr class="formtable">
	     <td height="1" bgcolor="#990000" colspan="2"></td>
	  </tr>
       <tr class="formtable">
	     <td colspan="2">&nbsp;</td>
	  </tr>       
       
        <tr>
                 <td width="50%" valign="top" class="formtable">Name:                 </td>
              <%--    <td class="normalStyle"><%= Userbean.getUserName() %></td> --%>
        </tr>

        <tr>
                 <td width="50%" valign="top" class="formtable">UTA Email :              </td>
              <%--    <td class="normalStyle"><%= Userbean.getUserType() %></td> --%>
        </tr>
        
        <tr>
                 <td width="50%" valign="top" class="formtable">Phone Number :                </td>
           <%--       <td class="normalStyle"><%= Userbean.getTerm() %></td> --%>
        </tr>
        
        <tr>
                 <td width="50%" valign="top" class="formtable"> Address :                </td>
        <%--   <td class="normalStyle"><% for(int i=0;i<Userbean.getLanguages().length;i++){%><%= Userbean.getLanguages()[i] %>&nbsp;&nbsp;<%}%></td>
         --%>
        </tr>
        
        <tr>
                 <td width="50%" valign="top" class="formtable"> PAssword :           </td>
            <%--      <td class="normalStyle"><%= Userbean.getRegion() %></td> --%>
        </tr>
       
        
   
        <tr>
        <td align="center">
        <input type="button" value="Yes" onclick="yesFunction();">
</td>
<td align="center">
<input type="button" value="No" onclick="noFunction();">
</td>
        </tr>
         <tr class="formtable">
	     <td colspan="2">&nbsp;</td>
	  </tr>
    </table>  
        
   
        
   
    </td></tr>
</table>
    <table align="center" cellpadding="0" cellspacing="0"><tr><td>
    <jsp:include page="/jsp/commons/footer.jsp"/>
    </td></tr></table>
    
    </body>
</html>
