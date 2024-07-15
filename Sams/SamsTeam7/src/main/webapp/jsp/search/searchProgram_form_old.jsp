<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.uta.sams.controller.ProgramController,org.uta.sams.beans.*"%><%@page contentType="text/html"%>
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

<script language="JavaScript">
    
var opt = "<option>option1</option><option>option1</option><option>option1</option><option>option1</option>";
var programtype="<select id='pid' name='programType' multiple size='3'><option value=''>-----Program Type-----</option><option>affiliated</option><option>Exchange</option><option>Faculty-Led</option></select>";
var programterm="<select id='termid'  name='term' multiple size='3'><option value=''>-----Program Term-----</option><option value='semester/year'>semester/year</option><option value='summer'>summer</option></select>";
var reg="<select  id='regionSelect' name='region' onchange='updateCountry()' multiple size='3'><option value=''>---Regions-----</option></select>";
var co="<select  id='countrySelect' name='country' multiple size='3'><option value=''>---country-----</option></select>";
var school="<select   id='schoolSelect' name='school'onchange='updateDepartment()' multiple size='3'><option>---Schools-----</option></select>";
var subj="<select   id='departmentSelect' name='subjects' multiple size='3'><option>---Subjects-----</option></select>";
var cur=0;
var t1="Please select Program Type and Program Term you are interested in. One can select more then one options by pressing the ctrl key. One can further Filter the result by clicking on above tabs<br><br>";
var t2="Please select Regions and Country to filter your result. One can select more then one options by pressing the ctrl key <br><br>";
var t3="Please select Departments and Subjects to filter your result. One can select more then one options by pressing the ctrl key";
var str="<TABLE id=secondary cellSpacing=0 cellPadding=2 width='80%' border=0><TBODY><tr>";
 var str2="</tr></table>";
 str="";
 str2="";
 
function initiall(){
//alert("in iniital1");

 
  tab1=programterm+"&nbsp;&nbsp;"+programtype+"&nbsp;&nbsp;"+str2;
  tab2=str+"&nbsp;&nbsp;"+reg+"&nbsp;&nbsp;"+co+"&nbsp;&nbsp;"+str2;
  //alert("tab2 is "+tab2);
  tab3=str+"&nbsp;&nbsp;"+school+"&nbsp;&nbsp;"+subj+"&nbsp;&nbsp;"+str2;
   
   var rdiv = document.getElementById("regionDIV");
   rdiv.innerHTML = tab2;
   rdiv.style.visibility='hidden';
   var sdiv = document.getElementById("subsDIV");
   sdiv.innerHTML = tab3;
   sdiv.style.visibility='hidden';
   var con  = document.getElementById("content1");
   con.innerHTML="";
   con.innerHTML = tab1;
   //alert(con.innerHTML);
}

function perform(){
   //alert("hi");
}

function caroSwitcher(num){
   //alert("in caro Switcher");
      
       var con  = document.getElementById("content1");
       var rdiv = document.getElementById("regionDIV");  
       var sdiv = document.getElementById("subsDIV");
       var termSelect = document.getElementById("termid"); 
       var progSelect =document.getElementById("pid"); 
       var regSelect =document.getElementById("regionSelect"); 
       var conSelect =document.getElementById("countrySelect"); 
       var scSelect =document.getElementById("schoolSelect"); 
       var deSelect =document.getElementById("departmentSelect"); 
       var ov = document.getElementById("overview"); 
       
       termSelect.size="1";
       progSelect.size="1";
       regSelect.size="1";
       conSelect.size="1";
       scSelect.size="1";
       deSelect.size="1";
       
	  
	   	   
           if(num==0){
	   cur=0;
           termSelect.size="3";
           progSelect.size="3";
	   con.style.visibility = 'visible';         
           rdiv.style.visibility = 'hidden';
           sdiv.style.visibility = 'hidden';
           ov.innerHTML=t1;
	   }
	   if(num==1){
	   cur=1;
           //var rdiv = document.getElementById("regionDIV");
          /// alert(rdiv);
           regSelect.size="3";
           conSelect.size="3";
	   con.style.visibility = 'hidden';
	   rdiv.style.visibility="visible";
           sdiv.style.visibility = 'hidden';
           ov.innerHTML=t2;
	   }
	   if(num==2){
	   scSelect.size="3";
           deSelect.size="3";
           rdiv.style.visibility = 'hidden';
	   con.style.visibility = 'hidden';
           sdiv.style.visibility = 'visible';
           ov.innerHTML=t3;
	   }
}
    
    
    </script>
 <script  language=JavaScript>



       var country= new Array();
       var region= new Array();
       var departments=new Array();
       var schools=new Array();
       var deptList=new Array();
       
       function populateCountry(){
       var  i=0;
       
    <%
        String program=request.getParameter("program");
        String term=request.getParameter("term");
        ProgramController programcontroller=new ProgramController();
        ContainerBean containerbean=programcontroller.updateProgramShowDetail(program,term);
        UtilityBean utilitybean=containerbean.getUtilitybean();
        ProgramBean programbean=containerbean.getProgrambean();
        String[][] region_country=utilitybean.getRegion_country();
        if(region_country!=null){ 
            for(int i=0;i<region_country.length;i++)
            {
                for(int j=0;j<region_country[i].length;j++){
                    if(region_country[i][j]!=null&&!region_country[i][j].equalsIgnoreCase("null")){

    %>
     if('<%=j%>'!='0'){
     country[i]='<%=region_country[i][j]%>';
     region[i]='<%=region_country[i][0]%>';
     i++;
     }
    <%
                    }
    }%>
    
     <%
            }
        }
    %>
    }
    
    function populateDepartments(){
    
        var  i=0;
       
    <%
      
        String[][] dept_school=utilitybean.getSchool_departments();
        if(dept_school!=null){ 
            for(int i=0;i<dept_school.length;i++)
            {
                for(int j=0;j<dept_school[i].length;j++){
                    if(dept_school[i][j]!=null&&!dept_school[i][j].equalsIgnoreCase("null")){

    %>
     if('<%=j%>'!='0'){
     departments[i]='<%=dept_school[i][j]%>';
     schools[i]='<%=dept_school[i][0]%>';
     i++;
     }
    <%
                    }
    }%>
    
     <%
            }
        }
    %>
    
    }
    
   /* function display(){
        
        populateCountry();
        populateDepartments();
        var temp;
        temp="Region\t Country<br>";
        for(var i=0;i<region.length;i++){
          temp+=""+region[i]+"\t"+country[i]+"<br>";
         }
         temp+="School\t department<br>";
         for(var i=0;i<schools.length;i++){
          temp+=""+schools[i]+"\t"+departments[i]+"<br>";
         }
         var ele=document.getElementById("test");
         ele.innerHTML=temp;
         ele.visible=true;
         
    }
    */
    
    function isPresent(selectbox,key){
         var i=0;
         for(i=0;i<selectbox.options.length;i++){
             if(selectbox.options[i].value==key)return true;
         }
         return false;
    }
    
    function initialize(){
        //alert("before initial1");
        initiall();
        //alert("after initial");
        populateCountry();
        populateDepartments();
      var i=0,j=0;
      var regionSelect=document.getElementById("regionSelect");
      //alert("region length "+region.length);
      //alert("regionselect "+regionSelect);
      for(i=0;i<region.length;i++){
         if(!isPresent(regionSelect,region[i])){
             //alert("adding optioin to region "+region[i]);
             var optn=document.createElement("OPTION");
             optn.text=region[i];
             optn.value=region[i];
             regionSelect.options.add(optn);
         }
      }
      
      var schoolSelect=document.getElementById("schoolSelect");
        for(i=0;i<schools.length;i++){
          if(!isPresent(schoolSelect,schools[i])){
              var optn=document.createElement("OPTION");
              optn.text=schools[i];
              optn.value=schools[i];
              schoolSelect.options.add(optn);
           }
        }  
        caroSwitcher(0);
     }
     
     function emptySelectBox(selectBox){
        var i=0;
        for(i=selectBox.options.length-1;i>=0;i--){
           selectBox.remove(i);
        }
     }
     
     function updateCountry(){
        var j=0,i=0;
        var name = new Array;
        var index = 0;
        var ele=document.getElementById("regionSelect");
        for(j=0;j<ele.options.length;j++){
        //alert(ele.options[j].selected+" "+ele.options[j].text+" "+ele.options[j].value);
         if(ele.options[j].selected){
             name[index++]=ele.options[j].value;
             //alert("option is selected");
             }
        }
        var countrySelect=document.getElementById("countrySelect");
        emptySelectBox(countrySelect);
        //alert("after select box"+name);
        for(index=0;index<name.length;index++){
        for(i=0;i<region.length;i++){
         if(region[i]==name[index]){
             var optn=document.createElement("OPTION");
             optn.text=country[i];
             optn.value=country[i];
             countrySelect.options.add(optn);
         }
       }
      }
        
     }
     
     function addToCountryList(){
         var ele=document.getElementById("countrySelect");
         var countryList=new Array();
         var index=0;
        for(j=0;j<ele.options.length;j++){
        //alert(ele.options[j].selected+" "+ele.options[j].text+" "+ele.options[j].value);
         if(ele.options[j].selected){
             countryList[index++]=ele.options[j].value;
             //alert("option is selected");
             }
        }
        var countrySelect=document.getElementById("countryList");
        //emptySelectBox(countrySelect);
        //alert("after select box"+name);
        for(i=0;i<deptList.length;i++){
             var optn=document.createElement("OPTION");
             optn.text=countryList[i];
             optn.value=countryList[i];
             countrySelect.options.add(optn);
         
      }
     }
     
      function deleteFromCountryList(){
         var i=0;
         var selectBox=document.getElementById("countryList");
        for(i=selectBox.options.length-1;i>=0;i--){
           if(selectBox.options[i].selected)selectBox.remove(i);
        }
     }
     
     function updateDepartment(){
             var j=0,i=0;
        var name=new Array();
        var index=0;
        var ele=document.getElementById("schoolSelect");
        for(j=0;j<ele.options.length;j++){
        //alert(ele.options[j].selected+" "+ele.options[j].text+" "+ele.options[j].value);
         if(ele.options[j].selected){
             name[index++]=ele.options[j].value;
             //alert("option is selected");
             }
        }
        var deptSelect=document.getElementById("departmentSelect");
        emptySelectBox(deptSelect);
        //alert("after select box"+name);
        for(index=0;index<name.length;index++){
        for(i=0;i<schools.length;i++){
         if(schools[i]==name[index]){
             var optn=document.createElement("OPTION");
             optn.text=departments[i];
             optn.value=departments[i];
             deptSelect.options.add(optn);
         }
      }
      }
     }
     
      function addToDepartmentList(){
         var ele=document.getElementById("departmentSelect");
         var deptList=new Array();
         var index=0;
        for(j=0;j<ele.options.length;j++){
        //alert(ele.options[j].selected+" "+ele.options[j].text+" "+ele.options[j].value);
         if(ele.options[j].selected){
             deptList[index++]=ele.options[j].value;
             //alert("option is selected");
             }
        }
        var deptSelect=document.getElementById("departmentList");
        //emptySelectBox(departmentSelect);
        //alert("after select box"+name);
        for(i=0;i<deptList.length;i++){
             var optn=document.createElement("OPTION");
             optn.text=deptList[i];
             optn.value=deptList[i];
             deptSelect.options.add(optn);
         
      }
     }
     
     function deleteFromDeptList(){
         var i=0;
         var selectBox=document.getElementById("departmentList");
        for(i=selectBox.options.length-1;i>=0;i--){
           if(selectBox.options[i].selected)selectBox.remove(i);
        }
     }
    </script >
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK title="International Stylesheet" 
href="fsmenu2.css" type=text/css rel=stylesheet>       
        <style type="text/css">
<!--
.searchtable {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: bold;
	text-align: left;
	text-indent: 5px;
	border-bottom-width: thin;
	border-bottom-style: solid;
	border-bottom-color: #666666;
	width: 100%;
}
.textall {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: bold;
	text-align: left;
}
.regionSelect {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	width: 125px;
	height: 80px;
	text-align: left;
        overflow: auto;
}
-->
        </style>
        
       
</head>
    <body onload="initialize()">
    <table valign="top" cellpadding="0"align="center" cellspacing="0" width='759' >
    <tr><td>
          <jsp:include page="/jsp/commons/header.jsp"/>
    </td></tr>
    </table>    
     <table valign="top" align="center" width="759" cellspacing="0" cellpadding="0" height="400"><tr>
    <td style="border-right: 1px solid rgb(255, 255, 255);" bgcolor="#666666" height="92" valign="top" width="331">
     <script type="text/javascript" src="<%=request.getContextPath()%>/jsp/commons/sams/fsmenu.js"></script>
              <jsp:include page="/jsp/commons/left.jsp"/>
            
    </td>
    <td style="padding-left:5px" valign="top">
    <form action="<%=request.getContextPath()%>/jsp/search/Program_list.jsp" method="post">   
    <table cellpadding="0" cellspacing="0" class="searchtable">
        <tr><td colspan="3" ><P aling='left'><IMG height=16 alt="" 
      src="Less than Full Course Waiver - UTA Office of International Education_files/arrow_bullet.gif" 
      width=17> <FONT color=#999999 size=4>Search Programs</FONT><FONT size=4> </P>
    <DIV id=secondary align='center' style="padding-top:5px;padding-bottom:5px">
      
      <DIV id=navcontainer align='left' >
       <UL id=neCaroTabs>
		  <LI ><A onclick=caroSwitcher(0) href="javascript:void(0);">Program Term/Type</A> 
		   <LI ><A onclick=caroSwitcher(1) href="javascript:void(0);">Region/Country</A> 
		  <LI ><A onclick=caroSwitcher(2) href="javascript:void(0);">School/Subjects</A> 
	
	</LI></UL></DIV>
        <DIV id=neCarousel align='left'>
<DIV class=team style="padding-top:5px;padding-bottom:5px">
     <FONT color=#448 size="2px"><div id="overview"></div></font>
     <div id=content1></div>
     <div id="regionDIV" style="visibility:'hidden'"></div>
     <div id="subsDIV" style="visibility:'hidden'"></div>
     
</div>

	 </div>
        <span width='100%' align='center'><input type="submit" value="submit" /></span>	 
</div>

     </td></tr>
            
            
           
            </table> 
            <br>
            <%--<table class="searchtable" cellpadding="2" cellspacing="0">
                <tr>
                    <td>School  </td>
                    <td>Department</td>
                    <td>Term of Study</td>
                </tr>  
                <tr>
                    <td align="left" valign="top"><select  style="width:146px" id="schoolSelect" name="school"onchange="updateDepartment()" multiple size="8"><option>---Schools-----</option></select></td>
                  <td align="left" valign="top" ><select  style="width:140px" id="departmentSelect" name="subjects" multiple size="8"></select></td>
                    <td align="left" valign="top"><%=programbean.getTerm()%><select class="regionSelect" name="term" multiple size="3">
                    <option value="semester/year">semester/year</option>
                    <option value="summer">summer</option>
                 </select></td>
                    </tr>            
                <tr>
               
               
            </tr>
            <tr>
                <td>Languages </td>
            </tr>
            <tr><td> <select name="languages" size="4" multiple="multiple">
                    <%for(int i=0;i<programbean.getLanguages().length;i++){%>
                    <option><%=programbean.getLanguages()[i]%>&nbsp;<%}%><br></option>
                    <option>Mandarin</option>
                    <option>Spanish</option>
                    <option>French</option>
                    </select>
               </td>
                                  
            </tr>
             <tr>
                
            </tr>
          </table>--%>
          <br>
          <!--<table align="center">
              <tr><td>
                    <input align="center" type="submit" value="submit"><input type="reset" value="reset">
              </td></tr>
          </table>-->
    </form>
    
     </td></tr>
</table>
    <table align="center" cellpadding="0" cellspacing="0"><tr><td>
    <jsp:include page="../commons/footer.jsp"/>
    </td></tr></table>
    </body>
</html>
