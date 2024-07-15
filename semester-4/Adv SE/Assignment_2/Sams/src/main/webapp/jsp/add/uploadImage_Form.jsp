<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.uta.sams.controller.ProgramController,org.uta.sams.beans.*"%>
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
    
    
    <script>
        var nm;
        function change(){
        var ele = document.uploadForm.position;
        for(var j=0;j<ele.options.length;j++){
        //alert(ele.options[j].selected+" "+ele.options[j].text+" "+ele.options[j].value);
         if(ele.options[j].selected){
         
            nm=ele.options[j].value;
            document.uploadForm.pos.value=nm;
             }
        
        }
        
        
        }
        function change2(){
       
uploadForm.Upload.value='Uploading...';
document.uploadForm.action='uploadImage.jsp?prg=<%=request.getParameter("prg")%>&term=<%=request.getParameter("term")%>&position='+document.uploadForm.pos.value;
 document.uploadForm.submit();
  
        }
    </script>
    <body>
<table valign="top" cellpadding="0"align="center" cellspacing="0" width='759'>
    <tr><td>
          <jsp:include page="/jsp/commons/header.jsp"/>
    </td></tr>
</table>    

<table valign="top" align="center" width="759" cellspacing="0" cellpadding="0" border="2"><tr>
<td style="border-right: 1px solid rgb(255, 255, 255);" bgcolor="#666666" height="92" valign="top" width="331">
 <script type="text/javascript" src="/jsp/commons/sams/fsmenu.js"></script>
              <jsp:include page="/jsp/commons/left.jsp"/>
            
 </td>
<td style="padding-left:5px">
    
   <table>
       
   <form name="uploadForm" action="change2();" enctype="multipart/form-data" method="post">    
    
       <%
       String programName = request.getParameter("prg");
       String term1 = request.getParameter("term");
       
       
       %>
       <tr>
            <td>Program Name</td>
            <%
            if(request.getParameter("prg") != null)
            {%>
                <td><input type=text name="prg" value="<%=request.getParameter("prg")%>"></td>
           <% }
            else
           {%>
            
            <td><input type=text name="prg"></td>
            <%
            }
            %>
            
            
            
       </tr>
       
       <tr>
            <td>Term of Study</td>
<%            
            if(request.getParameter("term") != null)
{%>
                <td><input type=text name="term" value="<%=request.getParameter("term")%>"></td>
           <% }
            else
            {
            %>
            <td><input type=text name="term"></td>
            <%
            }
            %>
       </tr>
       
       <tr>
       <td colspan="2"><b>Images already present for this program name and term:</b></td>
      </tr>
      <tr>
            <td>
            <%
               ProgramBean pb = new ProgramBean();
            ProgramController pc = new ProgramController();
            pb= pc.showDetail(programName,term1);
            %>    
              Image1: <%=pb.getImg1()%><br>
              Image2: <%=pb.getImg2()%><br>
              Image3: <%=pb.getImg3()%>
               
            
     
            </td>
      
      </tr>
      <tr>
       <td colspan="2"><b>Upload Image:</b></td>
      </tr>
      
      <tr>
            <td>Select the image position: </td>
            <td><select name="position" onchange="change();">
                <option value="null">Select One</option> 
                <option value="img1">Image 1</option>
                <option value="img2">Image 2</option>
                <option value="img3">Image 3</option>
</select></td>
       </tr>
       
       
       <tr>
         <td valign="top">Select file: </td>
      <td >
       
       

                
    <input type="hidden" name="pos" value=""/>
    <input type="file" name="file"/>
    <input TYPE=Button name='Upload' Value='Upload' onClick="change2();">
     
    </form>
 </td>
       </tr>
   </table>


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
   
        
   
    </td></tr>
</table>
    <table align="center" cellpadding="0" cellspacing="0"><tr><td>
    <jsp:include page="/jsp/commons/footer.jsp"/>
    </td></tr></table>
    </body>
</html>
