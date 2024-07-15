<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="org.uta.sams.beans.ProgramBean,org.uta.sams.controller.ProgramController,java.util.ArrayList"%>
<%@ page import = "java.lang.reflect.*" %>

<jsp:useBean id="searchbean" scope="request" class="org.uta.sams.beans.SearchBean" />
<jsp:setProperty name="searchbean" property="*"/>


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
    <% String jsppath=request.getContextPath()+"/jsp/";%>
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
    
    <table>
        
        
        <%
   
        ProgramBean programbean = new ProgramBean();// added this line
        // do we need to add more data to above object ??
   
   
   
           
%>
        
        
        
        
        <tr>
                 <td>Program Name:                 </td>
                 <td><%= programbean.getProgramName() %></td>
        </tr>

        <tr>
                 <td>   Program Type :              </td>
                 <td><%= programbean.getProgramType() %></td>
        </tr>
        
        <tr>
                 <td>Term of Study :                </td>
                 <td><%= programbean.getTerm() %></td>
        </tr>
        
        <tr>
                 <td> Languages :                </td>
                 <td><% for(int i=0;i<programbean.getLanguages().length;i++){%><%= programbean.getLanguages()[i] %>&nbsp;&nbsp;<%}%></td>
        </tr>
        
        <tr>
                 <td>      Region :           </td>
                 <td><%= programbean.getRegion() %></td>
        </tr>
        <tr>
                 <td> Country :                </td>
                 <td><%= programbean.getCountry() %></td>
        </tr>

    <%--    <tr>
                 <td>   School :              </td>
                 <td><%= programbean.getSchool() %></td>
        </tr> --%>
        
        <tr>
                 <td> Department :                </td>
                     <td><% for(int i=0;i<programbean.getSubjects().length;i++){%><%= programbean.getSubjects()[i] %> &nbsp;&nbsp; <%}%> 
                     </td>
        </tr>
        
        <tr>
                 <td>      Description :           </td>
                 <td><%= programbean.getDescription() %></td>
        </tr>
        
        <tr>
                 <td> Fees :                </td>
                 <td><%= programbean.getFee() %></td>
        </tr>
        <tr>
                 <td> Housing :                </td>
                 <td><%= programbean.getHousing() %></td>
        </tr>

        <tr>
                 <td>    Image1 Url :             </td>
                 <td><%= programbean.getImg1() %></td>
        </tr>
        
        <tr>
                 <td>   Image2 Url :              </td>
                 <td><%= programbean.getImg2() %></td>
        </tr>
        
        <tr>
                 <td>   Image3 Url :              </td>
                 <td><%= programbean.getImg3() %>
       
                 </td>
        </tr>
       
       <tr>
           
        <td>  <input type="button" value="Confirm">            </td>
                 <td><input type="button" value="Cancel"></td>
        </tr>
        
    </table>
    
    
    </td></tr>
</table>

<table align="center" cellpadding="0" cellspacing="0"><tr><td>
    <jsp:include page="/jsp/commons/footer.jsp"/>
</td></tr></table>
    
    
    </body>
</html>
