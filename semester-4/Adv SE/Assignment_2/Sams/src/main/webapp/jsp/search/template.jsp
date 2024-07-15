<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="org.uta.sams.beans.ProgramBean,org.uta.sams.controller.ProgramController,java.util.ArrayList"%>
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
  <% String jsppath=request.getContextPath()+"/jsp/commons/";%>  
   <style type="text/css">

.main {
	height: 960px;
	width: 760px;
	background-image: url(bg1.JPG);	
}
.main_div1 {
	float: right;
	height: 80px;
	width: 500px;
	margin-right: 20px;
	margin-top: 50px;
}
.main_div2 {
	width: 236px;
	height: 100px;
	float: left;
	background-image: url(stamp.JPG);
}
.main_div3 {
	height: 830px;
	width: 720px;
	background-image: url(bg2.jpg);
	margin-left: 19px;
}
.main_div4 {
	float: left;
	height: 250px;
	width: 300px;
	margin-top: 0px;
	position: absolute;
	top: 125px;
	left: 45px;
	font-family: Geneva, Arial, Helvetica, sans-serif;
	font-size: 14pt;
        font-style:italic;
	color: #FFFFFF;
	font-weight: bold;
}
.main_div5 {
	float: none;
	height: 250px;
	width: 335px;
	margin-top: 60px;
	position: absolute;
	top: 110px;
	left: 40px;
	font-family: Geneva, Arial, Helvetica, sans-serif;
	font-size: 14pt;
        font-style:italic;
	color: #0000FF;
	font-weight: 400;
	clear: none;
	margin-left: 345px;
}

.main_div6 {
	float: none;
	height: 100px;
	width: 300px;
	margin-top: 540px;
	position: absolute;
	left: 45px;
	font-family: Geneva, Arial, Helvetica, sans-serif;
	font-size: 14pt;
	color: #FFFFFF;
	font-weight: 400;
	margin-left: 30px;
	font-style: italic;
}
.main_div7 {
	float: none;
	height: 180px;
	width: 300px;
	margin-top: 665px;
	position: absolute;
	top: 125px;
	left: 45px;
	font-family: Geneva, Arial, Helvetica, sans-serif;
	font-size: 14pt;
        font-style:italic;
	color: #0000FF;
	font-weight: 400;
	margin-left: 45px;
}
   #Layer1 {
	position:absolute;
	left:47px;
	top:402px;
	width:288px;
	height:261px;
	z-index:1;
}
   .crossimage {
	height: 240px;
	width: 260px;
	top: 400px;
	position: absolute;
	left: 60px;
}
   </style>
</head>

<body>
 
 <%
    System.out.println("in template jsp");
   String contextpath=request.getContextPath()+"/"; 
   String getID = request.getParameter("program_name");
   String term=request.getParameter("term");
   ProgramController programcontroller= new ProgramController();
   ProgramBean program=null;
   System.out.println("in template jsp1111");
   ArrayList programlist = (ArrayList) session.getAttribute("programlist");
   System.out.println("in template jsp222222");
   programlist=null;
   if(programlist != null){
       for(int i=0;i<programlist.size();i++){
           program = (ProgramBean)programlist.get(i);
           if(program ==null)continue;
           System.out.println("in template jsp 3333");
           if(program!=null){
               System.out.println("in template jsp 4444");
               if(program.getProgramName().equalsIgnoreCase(getID) && program.getTerm().equalsIgnoreCase(term))break;
               System.out.println("in template jsp 5555");
             //program=null;   
           }
           
       }
   }else{
       System.out.println(">>>>>>Goiing To COntroller for Details <<<<<<");
       program=programcontroller.showDetail(getID,term);
   }
   
   if(program!=null){
            
    %>
    
    <input type="hidden" name="programID" value="<%=program.getProgramName() %>">
    <input type="hidden" name="termID" value="<%=program.getTerm()%>">
    
<div class="main" >

<div class="main_div2">
</div>

<div class="main_div1">
<table width="100%" height="100%"><tr>
<td><img src="../search/images/<%=program.getImg1()%>"  width="165" height="80">
</img>
</td>
<td>
<img src="../search/images/<%=program.getImg2()%>"  width="165" height="80">
</img>
</td>
<td><img src="../search/images/<%=program.getImg3()%>" width="160" height="80">
</img>
</td>
</tr></table>
</div>
<div class="main_div3">

<div class="main_div4">

  <table width="100%" border="0">
  <tr>
    <td style="font-size:32pt"><%=program.getCountry()%></td>
   
  </tr>
  <tr>
  	<td>&nbsp;</td>
  </tr>
  <tr>
    <td><%=program.getProgramName()%> </td>

  </tr>
  <tr>
    <td>Program Type: <%=program.getProgramType()%> </td>
      
  </tr>
  <tr>
        <td>Term: <%=program.getTerm()%> </td>
  </tr>
  <tr>
        <td>Language: <%for(int j=0;j<program.getLanguages().length;j++){%>
    <%=program.getLanguages()[j]%>, &nbsp;
    <%}%> </td>

  </tr>
  <tr>
       <td>&nbsp;</td>
  </tr>
</table>



</div>    
<div class="crossimage">
    <br>
    
    <table align="center" valign="bottom" width="70%" height="70%">
       <tr><td> <img src="../search/images/ppl2.jpg" width="200">
        </img>
       </td></tr>
    </table>
    
</div>

<div class="main_div5">

<table width="100%" border="0">
  <tr>
    <td style="font-size:20pt">Academics</td>
   
  </tr>
  
  <tr>
    <td> <%for(int j=0;j<program.getSubjects().length;j++){%>
    <%=program.getSubjects()[j]%>, &nbsp;
    <%}%></td>
    
  </tr>
  <tr><td height="10px"></td></tr>
  <tr>
    <td style="font-size:20pt">Housing</td>
       
  </tr>
  
  <tr>
    <td><%=program.getHousing()%></td>
    
  </tr>
  <tr><td height="10px"></td></tr>
  <tr>
    <td style="font-size:20pt">Fees</td>
       
  </tr>
  
  <tr>
    <td><%=program.getFee()%></td>

  </tr>
  <tr><td height="10px"></td></tr>
  <tr>
    <td style="font-size:20pt">Application</td>
       
  </tr>
  
  <tr>
    <td><%=program.getAppDetails()%></td>
        
  </tr>
</table>

</div>



<div class="main_div6">

<table width="100%" border="0">
  <tr>
    <td>Testimonial</td>
   
  </tr>
  <tr>
  	<td>&nbsp;</td>
    
  </tr>
  <tr>
    <td>&nbsp;</td>
	
  </tr>
 
</table>

</div>

<div class="main_div7">

<table width="100%" border="0">
  <tr>
    <td align="center" style="font-size:18pt">Useful WebSites</td>
   
  </tr>
  <tr>
  	<td>&nbsp;</td>
    
  </tr>
  <tr>
    <td>&nbsp;</td>
	
  </tr>
  <tr>
  	<td>&nbsp; </td>    
    
  </tr>
  
</table>


</div>

</div>

</div>
 <%
           
       }
   else{
%>
<%
   }


%>

</body>
</html>
