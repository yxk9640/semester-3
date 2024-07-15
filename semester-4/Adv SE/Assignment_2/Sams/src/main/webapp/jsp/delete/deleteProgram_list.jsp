<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="org.uta.sams.beans.*,org.uta.sams.controller.ProgramController,java.util.ArrayList"%>


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
 <table valign="top" align="center" width="759" cellspacing="0" cellpadding="0"><tr>
<td style="border-right: 1px solid rgb(255, 255, 255);" bgcolor="#666666" height="92" valign="top" width="331">
 <script type="text/javascript" src="/jsp/commons/sams/fsmenu.js"></script>
              <jsp:include page="/jsp/commons/left.jsp"/>
            
 </td>
<td style="padding-left:5px"> 
<table border="1">
    <tr >
            
            <% 
            SearchBean sch= new SearchBean();
            String name=""; if(session.getAttribute("user")!=null)name=((UserBean)session.getAttribute("user")).getUsername(); 
//name=request.getRemoteUser();
%>
        <td  width="322" bgcolor="#FFFFFF" colspan="3"><span class="style4">Welcome <%=name%></span><span class="style2">.</span> </td>
        <td width="91" bgcolor="#FFFFFF" colspan="2"><span class="style4"><a href="../user/logoff.jsp">Sign Out </a></span></td>
      </tr>
	  <tr>
	     <td height="1" bgcolor="#990000" colspan="5"></td>
	  </tr>
 
    <tr><th colspan="5" align="center">Delete Program List</th></tr>
    <tr><th>Program Name</th><th>Term</th><th>Type</th><th>Region</th><th>Country</th>
<%
   String contextpath=request.getContextPath()+"/";
   String txtaction="";
   ProgramController programcontroller= new ProgramController();
   ArrayList programlist=programcontroller.searchPrograms(sch);
   if(programlist!=null){
       for(int i=0;i<programlist.size();i++){
           ProgramBean program = (ProgramBean)programlist.get(i);
%>
<tr><td><a href="<%=contextpath%>jsp/delete/deleteProgram_detail.jsp?program_name=<%=program.getProgramName()%>&term=<%=program.getTerm()%>"><%=program.getProgramName()%></a></td>
<td><%=program.getTerm()%></td><td><%=program.getProgramType()%></td><td><%=program.getRegion()%></td><td><%=program.getCountry()%></td></tr>           
<%
           
       }
   }else{
%>
    <tr><td colspan="4">Some Exception Occured. No data is presently available to present</td></tr>
<%
   }


%>
</table>

</td></tr>
</table>
    <table align="center" cellpadding="0" cellspacing="0"><tr><td>
    <jsp:include page="/jsp/commons/footer.jsp"/>
    </td></tr></table>

    
    </body>
</html>
