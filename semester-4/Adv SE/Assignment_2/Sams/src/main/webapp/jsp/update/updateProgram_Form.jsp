<%
String jsppath = request.getContextPath() + "/jsp/commons/";
%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page
	import="org.uta.sams.controller.ProgramController,org.uta.sams.beans.*,java.util.Arrays"%><%@page
	contentType="text/html"%>

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
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<title>Sams</title>
</head>


<script>
       var country= new Array();
       var region= new Array();
       var departments=new Array();
       var schools=new Array();
       var deptList=new Array();
       
       function populateCountry(){
       var  i=0;
       
    <%
        String program=request.getParameter("program_name");
        String term=request.getParameter("term");
        //program.replaceAll("%20"," ");
        //term.replaceAll("%20"," ");
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
        populateCountry();
        populateDepartments();
      var i=0,j=0;
      var regionSelect=document.getElementById("regionSelect");
      for(i=0;i<region.length;i++){
        if(!isPresent(regionSelect,region[i])){
                var optn=document.createElement("OPTION");
                optn.text=region[i];
                optn.value=region[i];
                if(optn.text === "<%=programbean.getRegion()%>") 
                {
                    optn.selected = true;
                    var countrySelect=document.getElementById("countrySelect");
                    emptySelectBox(countrySelect);
                    //alert("after select box"+name);
                    for(j=0;j<region.length;j++){
                        if(region[j]==optn.text){
                            var optnCou=document.createElement("OPTION");
                            optnCou.text=country[j];
                            optnCou.value=country[j];
                            if(optnCou.text === "<%=programbean.getCountry()%>") {
								optnCou.selected = true;
							}
							countrySelect.options.add(optnCou);
						}
					}
				}

				regionSelect.options.add(optn);
			}
			console.log("i=", i, region[i])
		}
		var schoolSelect = document.getElementById("schoolSelect");
		for (i = 0; i < schools.length; i++) {
			if (!isPresent(schoolSelect, schools[i])) {
				var optn = document.createElement("OPTION");
				optn.text = schools[i];
				optn.value = schools[i];
				schoolSelect.options.add(optn);
			}
		}
	}

	function emptySelectBox(selectBox) {
		var i = 0;
		for (i = selectBox.options.length - 1; i >= 0; i--) {
			selectBox.remove(i);
		}
	}

	function updateCountry() {
		var j = 0, i = 0;
		var name;
		var ele = document.getElementById("regionSelect");
		for (j = 0; j < ele.options.length; j++) {
			//alert(ele.options[j].selected+" "+ele.options[j].text+" "+ele.options[j].value);
			if (ele.options[j].selected) {
				name = ele.options[j].value;
				//alert("option is selected");
			}
		}
		var countrySelect = document.getElementById("countrySelect");
		emptySelectBox(countrySelect);
		//alert("after select box"+name);
		for (i = 0; i < region.length; i++) {
			if (region[i] == name) {
				var optn = document.createElement("OPTION");
				optn.text = country[i];
				optn.value = country[i];
				countrySelect.options.add(optn);
			}
		}

	}

	function updateDepartment() {
		var j = 0, i = 0;
		var name = new Array();
		var index = 0;
		var ele = document.getElementById("schoolSelect");
		for (j = 0; j < ele.options.length; j++) {
			//alert(ele.options[j].selected+" "+ele.options[j].text+" "+ele.options[j].value);
			if (ele.options[j].selected) {
				name[index++] = ele.options[j].value;
				//alert("option is selected");
			}
		}
		var deptSelect = document.getElementById("departmentSelect");
		emptySelectBox(deptSelect);
		//alert("after select box"+name);
		for (index = 0; index < name.length; index++) {
			for (i = 0; i < schools.length; i++) {
				if (schools[i] == name[index]) {
					var optn = document.createElement("OPTION");
					optn.text = departments[i];
					optn.value = departments[i];
					deptSelect.options.add(optn);
				}
			}
		}
	}

	function addToDepartmentList() {
		var ele = document.getElementById("departmentSelect");
		var deptList = new Array();
		var index = 0;
		for (j = 0; j < ele.options.length; j++) {
			//alert(ele.options[j].selected+" "+ele.options[j].text+" "+ele.options[j].value);
			if (ele.options[j].selected) {
				deptList[index++] = ele.options[j].value;
				//alert("option is selected");
			}
		}
		var deptSelect = document.getElementById("departmentList");
		//emptySelectBox(departmentSelect);
		//alert("after select box"+name);
		for (i = 0; i < deptList.length; i++) {
			var optn = document.createElement("OPTION");
			optn.text = deptList[i];
			optn.value = deptList[i];
			deptSelect.options.add(optn);

		}
	}

	function deleteFromDeptList() {
		var i = 0;
		var selectBox = document.getElementById("departmentList");
		for (i = selectBox.options.length - 1; i >= 0; i--) {
			if (selectBox.options[i].selected)
				selectBox.remove(i);
		}
	}
</script>
<body onload="initialize()">


	<div class="row wrapper">


		<jsp:include page="/jsp/commons/header.jsp" />


		<div class="row mt-5 w-100 ">

			<div class="col-auto mt-3 w-auto h-100">
				<script type="text/javascript"
					src="<%=jsppath%>../commons/sams/fsmenu.js"></script>

				<jsp:include page="/jsp/commons/left.jsp" />



			</div>




			<main class="col mt-5 content">
			
			<div class="row">
					<!-- Page Heading -->
					<div
						class="d-sm-flex align-items-start justify-content-between mb-4">
						<h1 class="h3 mb-0 text-gray-800 ml-3">Update Program Form</h1>

					</div>
				</div>



				<form name="programForm" action="updateProgram.jsp" method="POST">
					<input type="hidden" name="programID"
						value="<%=programbean.getProgramID()%>" class="form-control"> <input
						type="hidden" name="termID" value="<%=programbean.getTermID()%>">
					<input type="hidden" name="programName"
						value="<%=programbean.getProgramName()%>" class="form-control">
				
				

							<%
							String name = "";
							if (session.getAttribute("user") != null)
								name = ((UserBean) session.getAttribute("user")).getUsername();
							//name=request.getRemoteUser();
							%>
							<%-- <div ><span class="style4">Welcome
									<%=name%></span><span class="style2">.</span></div> --%>
							<!-- <div><span class="style4"><a
									href="../user/logoff.jsp">Sign Out </a></span></div> -->
						
					
				

						
	
				

							<div class="form-row">
								<!--Program Name  -->

								<div class="col m-3">
									<label class="form-label" for="form3Example1">Program
										Name:</label> <input type="text" name="programName"
										class="form-control" value="<%=programbean.getProgramName()%>"
										disabled />
								</div>



								<!-- Program Type -->

								<div class="col m-3">
									<label class="form-label" for="inputState">Program
										Type:</label>
									<%=programbean.getProgramType()%>
									<select name="programType" class="form-control">

										<option
											<%if (programbean.getProgramType().equals("affiliated")) {
	out.print("selected");
}
;%>>affiliated</option>
										<option
											<%if (programbean.getProgramType().equals("Exchange")) {
	out.print("selected");
}
;%>>Exchange</option>
										<option
											<%if (programbean.getProgramType().equals("Faculty-Led")) {
	out.print("selected");
}
;%>>Faculty-Led</option>
									</select>
								</div>
							</div>
				



						<div class="form-row">
							<div class="col m-3">

								<label class="form-label">Term of Study:</label>
								<%=programbean.getTerm()%>
								<input type="hidden" name="term"
									value="<%=programbean.getTerm()%>" />
							</div>


							<div class="col m-3">

								<label class="form-label">Languages:</label>
								<%
								String[] lans = programbean.getLanguages();
								%>
								<%
								for (int i = 0; i < programbean.getLanguages().length; i++) {
								%><%=programbean.getLanguages()[i]%>&nbsp;<%}%>
								<select name="languages" size="4" multiple="multiple"
									class="form-control">
									<option
										<%if (Arrays.stream(lans).anyMatch("English"::equals)) {
	out.print("selected");
}
;%>>English</option>
									<option
										<%if (Arrays.stream(lans).anyMatch("Mandarin"::equals)) {
	out.print("selected");
}
;%>>Mandarin</option>
									<option
										<%if (Arrays.stream(lans).anyMatch("Spanish"::equals)) {
	out.print("selected");
}
;%>>Spanish</option>
									<option
										<%if (Arrays.stream(lans).anyMatch("French"::equals)) {
	out.print("selected");
}
;%>>French</option>
								</select>
							</div>

						</div>


						<div class="form-row">



							<div class="col m-3">

								Region :
								<%=programbean.getRegion()%>
								<select id="regionSelect" name="region"
									onchange="updateCountry()" class="form-control">
								</select>

							</div>
							<div class="col m-3">
								<label class="form-label">Country :</label>
								<%=programbean.getCountry()%>
								<select id="countrySelect" name="country" class="form-control"></select>

							</div>
						</div>


						<div class="form-row">




							<div class="col m-3">

								<div class="form-row">

									<div class="col m-3">
										<label class="form-label">School :</label> <select
											id="schoolSelect" name="school" onchange="updateDepartment()"
											multiple size="10" class="form-control"><option>---Schools-----</option></select>


									</div>
									

								</div>

							</div>

							<div class="col m-3">
								<label class="form-label">Department :</label> <select
									id="departmentSelect" name="subjects" multiple size=10 required
									class="form-control"></select>

							</div>

						</div>


						<div class="form-row">




							<div class="col m-3">

								<label class="form-label">Description :</label>
								<textarea class="form-control" cols="21" rows="10"
									name="description"><%=programbean.getDescription()%></textarea>

							</div>
							<div class="col m-3">

								<label class="form-label">Fees : </label> <input
									class="form-control" type="text" name="fee"
									value="<%=programbean.getFee()%>" />
							</div>



						</div>



						<!-- <tr>
                <td></td>
                <td><input type="text" name="otherRegion" value="" /></td>
            </tr>
            
            <tr>
                <td></td> 
                <td><input type="text" name="otherCountry" value="" /></td>
            </tr> -->


						<div class="form-row">


							<div class="col m-3">
								<label class="form-label">Housing : </label>

								<textarea cols="21" rows="10" class="form-control"
									name="housing"><%=programbean.getHousing()%> </textarea>


							</div>

							<div class="col m-3">
								<label class="form-label">Status : </label>


								<%=programbean.getStatus()%><br> <select name="status"
									class="form-control">
									<option>active</option>
									<option>deactive</option>
								</select>

							</div>

						</div>
						
						
<div class="col m-1 w-auto">
                <button type="submit" class="btn btn-primary btn-block mb-4 w-auto" value="submit" onclick="validateForm();">
                  Add Program
                </button>
                </div>
                <div class="col w-auto">
                
                 <button type="reset" class="btn btn-primary btn-block mb-4 w-auto" value="reset">
                  Reset
                </button>
                </div>
		

						

			


				</form> <%--
    This example uses JSTL, uncomment the taglib directive above.
    To test, display the page like this: index.jsp?sayHello=true&name=Murphy
    --%> <%--
    <c:if test="${param.sayHello}">
        <!-- Let's welcome the user ${param.name} -->
        Hello ${param.name}!
    </c:if>
    
    
    --%>
    
    </main>
    </div>
    </div>
    
</body>
</html>
