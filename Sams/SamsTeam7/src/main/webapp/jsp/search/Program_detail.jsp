<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="org.uta.sams.beans.*,org.uta.sams.controller.ProgramController,java.util.ArrayList"%>
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
 <table valign="top" align="center" width="759" cellspacing="0" cellpadding="0"><tr>
<td style="border-right: 1px solid rgb(255, 255, 255);" bgcolor="#666666" height="92" valign="top" width="331">
 <script type="text/javascript" src="/jsp/commons/sams/fsmenu.js"></script>
              <jsp:include page="/jsp/commons/left.jsp"/>
            
 </td>
<td style="padding-left:5px">

    
   <form action="deleteProgram_success.jsp" method="post" >
    <%
   String contextpath=request.getContextPath()+"/"; 
   String getID = request.getParameter("program_name");
   String term=request.getParameter("term");
   ProgramController programcontroller= new ProgramController();
   ProgramBean program=programcontroller.showDetail(getID,term);
   if(program!=null){
            
    %>
    <input type="hidden" name="programID" value="<%=program.getProgramName() %>">
    <input type="hidden" name="termID" value="<%=program.getTerm()%>">
    
    <table border="1" cellpadding="0" cellspacing="0">
        <tr >
            
   <% String name=""; if(session.getAttribute("user")!=null)name=((UserBean)session.getAttribute("user")).getUsername();
    //name=request.getRemoteUser();   
%>
        <td  width="322" bgcolor="#FFFFFF" ><span class="style4">Welcome <%=name%></span><span class="style2">.</span> </td>
        <td width="91" bgcolor="#FFFFFF"><span class="style4"><a href="../user/logoff.jsp">Sign Out </a></span></td>
      </tr>
	  <tr>
	     <td height="1" bgcolor="#990000" colspan="2"></td>
	  </tr>
 
        <tr><th> Program Name</th><td><%=program.getProgramName()%></td></tr>
        <tr><th> Program Type</th><td><%=program.getProgramType()%></td></tr>
        <tr><th> Acdemic Subject</th><td><%for(int i=0;i<program.getSubjects().length;i++){%><%=program.getSubjects()[i]%><br><%}%></td></tr>
        <tr><th> Term of Study</th><td><%=program.getTerm()%></td></tr>
        <tr><th> Language </th><td><%for(int i=0;i<program.getLanguages().length;i++){%><%=program.getLanguages()[i]%><br><%}%></td></tr>
        <tr><th> Region</th><td><%=program.getRegion()%></td></tr>
        <tr><th> Country</th><td><%=program.getCountry()%></td></tr>
        <tr><th> Description</th><td><%=program.getDescription()%></td></tr>
        <tr><th> Fee</th><td><%=program.getFee()%></td></tr>
        <tr><th> Application Detail</th><td><%=program.getAppDetails()%></td></tr>
        <tr><th> Housing</th><td><%=program.getHousing()%></td></tr>
        <tr><th> Testemony</th><td><%=program.getTestemony()%></td></tr>
        <tr><td><input type="submit" name="delete" value="Deactivate"></td><td><input type="submit" name="delete" value="Cancel"></td>
        
    </table>
    <%
           
       }
   else{
%>
    <tr><td colspan="4">Some Exception Occured. No data is presently 
available to present</td></tr>
<%
   }


%>
   
  </td></tr>
</table>
    <table align="center" cellpadding="0" cellspacing="0"><tr><td>
    <jsp:include page="/jsp/commons/footer.jsp"/>
    </td></tr></table>
  
    </body>
</html>
