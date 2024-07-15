<%@page import="org.uta.sams.beans.SearchBean"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="org.uta.sams.beans.ProgramBean,org.uta.sams.controller.ProgramController,java.util.ArrayList"%>


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
    <tr><th colspan="5" align="center">Delete Program List</th></tr>
    <tr><th>Program Name</th><th>Term</th><th>Type</th><th>Region</th><th>Country</th>
<%
   String contextpath=request.getContextPath()+"/";
   String txtaction="";
   ProgramController programcontroller= new ProgramController();
   SearchBean searchtemp = new SearchBean(); // created this obj
   ArrayList programlist=programcontroller.searchPrograms(searchtemp);
   if(programlist!=null){
       for(int i=0;i<programlist.size();i++){
           ProgramBean program = (ProgramBean)programlist.get(i);
%>
<tr><td><a href="<%=contextpath%>jsp/search/template.jsp?program_name=<%=program.getProgramName()%>&term=<%=program.getTerm()%>"><%=program.getProgramName()%></a></td>
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
