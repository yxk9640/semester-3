

<%
String jsppath = request.getContextPath() + "/jsp/commons/";
%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page
	import="org.uta.sams.controller.ProgramController,org.uta.sams.beans.*"%><%@page
	contentType="text/html"%>
<%@ page
	import="org.uta.sams.beans.UserBean,org.uta.sams.controller.UserController,java.util.ArrayList"%>
<%@ page import="java.lang.reflect.*"%>

<jsp:useBean id="userbean" scope="request"
	class="org.uta.sams.beans.UserBean" />
<jsp:setProperty name="userbean" property="*" />
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>OIE Dash-board</title>

<link rel="stylesheet" href="<%=jsppath%>sams/sample.css"
	type="text/css" title="International Stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>


<script>
       var country= new Array();
       var region= new Array();
       var departments=new Array();
       var schools=new Array();
       var deptList=new Array();
       
       
      
       
       function populateCountry(){
       var  i=0;
       
       
       
       
    <%String program = request.getParameter("program");
String term = request.getParameter("term");
ProgramController programcontroller = new ProgramController();
ContainerBean containerbean = programcontroller.updateProgramShowDetail(program, term);
UtilityBean utilitybean = containerbean.getUtilitybean();
ProgramBean programbean = containerbean.getProgrambean();
String[][] region_country = utilitybean.getRegion_country();

if (region_country != null) {
	for (int i = 0; i < region_country.length; i++) {
		for (int j = 0; j < region_country[i].length; j++) {
			if (region_country[i][j] != null && !region_country[i][j].equalsIgnoreCase("null")) {%>
     if('<%=j%>'!='0'){
     country[i]='<%=region_country[i][j]%>';
     region[i]='<%=region_country[i][0]%>';
     i++;
     }
    <%}
}%>
    
     <%}
}%>
    }
    
       
   
       
       
    function populateDepartments(){
    
        var  i=0;
       
    <%String[][] dept_school = utilitybean.getSchool_departments();
if (dept_school != null) {
	for (int i = 0; i < dept_school.length; i++) {
		for (int j = 0; j < dept_school[i].length; j++) {
			if (dept_school[i][j] != null && !dept_school[i][j].equalsIgnoreCase("null")) {%>
     if('<%=j%>'!='0'){
     departments[i]='<%=dept_school[i][j]%>';
     schools[i]='<%=dept_school[i][0]%>';
			i++;
		}
<%}
}%>
	
<%}
}%>
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

	function isPresent(selectbox, key) {
		var i = 0;
		for (i = 0; i < selectbox.options.length; i++) {
			if (selectbox.options[i].value == key)
				return true;
		}
		return false;
	}

	function initialize() {
		populateCountry();
		populateDepartments();
		var i = 0, j = 0;
		var regionSelect = document.getElementById("regionSelect");
		for (i = 0; i < region.length; i++) {
			if (!isPresent(regionSelect, region[i])) {
				var optn = document.createElement("OPTION");
				optn.text = region[i];
				optn.value = region[i];
				regionSelect.options.add(optn);
			}
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

	function deleteFromDeptList() {
		var i = 0;
		var selectBox = document.getElementById("departmentList");
		for (i = selectBox.options.length - 1; i >= 0; i--) {
			if (selectBox.options[i].selected)
				selectBox.remove(i);
		}
	}

	function trim(v1) {

	}

	function validateForm() {
		
		 var forms = document.querySelectorAll('.needs-validation');

		  // Loop over them and prevent submission
		  Array.prototype.slice.call(forms).forEach(function (form) {
		    form.addEventListener('submit', function (event) {
		      if (!form.checkValidity()) {
		        event.preventDefault();
		        event.stopPropagation();
		      } else {
		        form.classList.add('was-validated');
		    	document.programForm.submit();
		      }
		    }, false);
		  });
		 
	

	}
	function setControl(id) {
		var obj;

		if (document.getElementById(id).disabled) {
			document.getElementById(id).disabled = false;
			document.getElementById(id + "2").value = true;
			console.log(document.getElementById(id + "2").value)
		} else {
			document.getElementById(id).disabled = true;
			document.getElementById(id + "2").value = true;
		}

	}

	function countit() {

		//Character count script- by javascriptkit.com
		//Visit JavaScript Kit (http://javascriptkit.com) for script
		//Credit must stay intact for use

		formcontent = document.getElementById('programName').value
		if (formcontent.length < 4)
			document.getElementById('err1').innerHTML = "! Program Name cannot be less than 4 characters"
		else
			document.getElementById('err1').innerHTML = ""
	}
</script>



<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">


</head>

<body onload="initialize()">

	<!-- Page Wrapper -->

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
						<h1 class="h3 mb-0 text-gray-800 ml-3">Program Form</h1>

					</div>
				</div>


 <form name="programForm" action="<%=request.getContextPath()%>/jsp/add/addProgram.jsp" method="POST" class="needs-validation">
 
  
				<div class="form-row">
					<!--Program Name  -->

					<div class="col m-3">
						<label class="form-label" for="form3Example1">Program
							Name:</label> <input type="text" name="programName" class="form-control"
							onblur="countit()" required/>
					</div>





					<!-- Program Type -->

					<div class="col m-3">
						<label class="form-label" for="inputState">Program Type:</label> <select
							id="inputState" class="form-control" name="programType">

							<option>affiliated</option>
							<option>Exchange</option>
							<option>Faculty-Led</option>
						</select>
					</div>
				</div>






				<div class="row">




					<!-- Term of study -->
					<div class="col m-3">
						<label class="form-label" for="inputState">Term of Study:</label>
						<select id="inputState" class="form-control" name="term">

							<option value="summer">Summer</option>
							<option value="semester/year">Semester/year</option>
						</select>
					</div>









				</div>

				<div class="form-row">

					<!--Languages  -->

					<div class="col m-3">
						<label class="form-label" for="inputState">Languages:</label> <select
							id="inputState" class="form-control" name="languages" required>

							<%
							String[] lang = utilitybean.getLanguages();
							for (int i = 0; i < lang.length; i++) {
							%>
							<option><%=lang[i]%></option>
							<%}%>
						</select>
					</div>



					<!-- Other languages -->
					<div class="form-group col m-3">

						<label class="form-check-label" for="gridCheck"> Other
							Languages : </label>
						<div class="form-check mt-2">
							<input class="form-check-input" type="checkbox"
								id="checkLanguages" name="checkLanguages" value="enable"
								onclick="setControl('otherLanguages');"> <input
								type="text" name="otherLanguages" id="otherLanguages" value=""
								class="form-control" disabled>


						</div>
					</div>












				</div>
				<div class="form-row">


					<!-- Region -->
					<div class="col m-3">
						<label class="form-label" for="inputState">Region:</label> <select
							id="regionSelect" name="region" class="form-control"
							onchange="updateCountry()" required>

							<option>----Regions----</option>
						</select>
					</div>









					<!-- Other region -->
					<div class="col form-group col m-3">
						<label class="form-check-label" for="gridCheck"> Other
							Region : </label>
						<div class="form-check mt-2">

							<input class="form-check-input" type="checkbox"
								name="checkRegion" id="checkRegion" value="enable"
								onclick="setControl('otherRegion');"> 
								<input type="text"
								name="otherRegion" id="otherRegion" value=""
								class="form-control" disabled>
						</div>
					</div>










				</div>

				<div class="form-row d-flex ">

					<!-- Country -->
					<div class="col m-3">
						<label class="form-label" for="inputState">Country:</label> <select
							id="countrySelect" name="country" class="form-control" required>


						</select>
					</div>



					<!-- Other Country -->

					<div class="form-group col m-3">
						<label class="form-check-label" for="gridCheck"> Other
							Country : </label>
						<div class="form-check mt-2">
							<input class="form-check-input" type="checkbox"
								name="checkCountry" id="checkCountry" value="enable"
								onclick="setControl('otherCountry');"> <input
								type="text" name="otherCountry" id="otherCountry"
								class="form-control" value="" disabled>


						</div>
					</div>

				</div>









				<div class="form-row d-flex ">

					<!-- School -->
					<div class="col m-3">
						<label class="form-label" for="inputState">School:</label> <select
							id="schoolSelect" name="school" onchange="updateDepartment()"
							multiple class="form-control" required>


						</select>
					</div>



					<!-- Department -->
					<div class="col m-3">
						<label class="form-label" for="inputState">Department:</label> <select
							id="departmentSelect" name="subjects" multiple
							class="form-control" required>


						</select>
					</div>
				</div>


				<div class="form-row d-flex">


					<div class="form-group col m-3">
						<label class="form-check-label" for="gridCheck"> Other
							School/Departments : </label>
						<div class="form-check mt-2">
							<input class="form-check-input" type="checkbox"
								name="checkSchools" id="checkSchools" value="enable"
								onclick="setControl('otherSchools');">




							<textarea class="form-control" name="otherSchools"
								id="otherSchools" class="form-control" value="" rows="7"
								tabindex="9" disabled></textarea>


						</div>
					</div>

					<div class="col m-3">
						<label class="form-label" for="inputState">Description:</label>
						<textarea class="form-control" name="description" rows="7"
							tabindex="9" required></textarea>


					</div>

				</div>



				<div class="form-row d-flex">


					<div class="form-group col m-3">
						<div class="col m-3">
							<label class="form-label" for="inputState">Fees:</label>
							<textarea class="form-control" name="fee" rows="7" tabindex="10" required></textarea>


						</div>
					</div>

					<div class="col m-3 mt-2 ">
						<label class="form-label" for="inputState">Housing:</label>
						<textarea class="form-control" name="housing" rows="7"
							tabindex="10" required></textarea>


					</div>




				</div>
				
				
				<div class="flex justify-content-center " >
	
 
  	
	

<div class="col m-1 w-auto">
                <button type="submit" class="btn btn-primary btn-block mb-4 w-auto" value="Add Program" onclick="validateForm();">
                  Add Program
                </button>
                </div>
                <div class="col w-auto">
                
                 <button type="reset" class="btn btn-primary btn-block mb-4 w-auto" value="Reset Form">
                  Reset
                </button>
                </div>
			</div>
			
			<input type="hidden" id="otherRegion2" name="otherRegion2" value="false"> 
             <input type="hidden" id="otherLanguages2" name="otherLanguages2" value="false"> 
             <input type="hidden" id="otherCountry2" name="otherCountry2" value="false"> 
             <input type="hidden" id="otherSchools2" name="otherSchools2" value="false"> 
			
		</form>

			</main>
			
			
			



			<!--   
                     
    </tr>

    <tr>
      <td width="50%" class="formtable" valign="top">Description:</td>
      <td></td>
    </tr>
    <tr>
      <td width="50%" class="formtable" valign="top">Fees:</td>
      <td><textarea name="fee" rows="7" style="width:180px" tabindex="10" ></textarea></td>
    </tr>
    <tr>
      <td width="50%" class="formtable" valign="top">Housing:</td>
      <td><textarea name="housing" rows="7" style="width:180px" tabindex="11"></textarea></td>
    </tr> -->






			<!-- Footer -->
			<!-- <footer class="sticky-footer bg-white">
					<div class="container my-auto">
						<div class="copyright text-center my-auto">
							<span>Copyright &copy; Your Website 2021</span>
						</div>
					</div>
				</footer> -->
			<!-- End of Footer -->

		</div>
		
		<!-- End of Content Wrapper -->

	</div>
	<!-- End of Page Wrapper -->






</body>

</html>