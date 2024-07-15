<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<%@ page import="java.util.ArrayList"%>
<%@ page import ="uta.mav.appoint.login.Department"%>
<%@ page import ="uta.mav.appoint.login.AdvisorUser"%>

<% ArrayList<AdvisorUser> array = (ArrayList<AdvisorUser>)session.getAttribute("advisors");
if (array != null){
	
ArrayList<Department> departments = (ArrayList<Department>)session.getAttribute("departments");

ArrayList<String> degreeType = (ArrayList<String>)session.getAttribute("degreeType");

ArrayList<String> major = (ArrayList<String>)session.getAttribute("major");

ArrayList<Character> letters = (ArrayList<Character>)session.getAttribute("letters");

%>
	

<div class="container-fluid">
<!-- Panel -->
<div class="panel panel-default">
<!-- Default panel contents -->
<div class="panel-heading text-center"><h1>Student Information</h1></div>
<div class="panel-body">

		<form action="waitlist" method="post" name="advisor_form">
	<div class="row">
    <div class="col-md-2">
        <label for="wl_drp_department"><font color="#0" size="4">Department</font></label> 

		<br>
		<select id="wl_drp_department" onchange = "submit();" name = "wl_drp_department" class="btn btn-default btn-lg dropdown-toggle">
			<%

			for (int i=0;i<departments.size();i++){
				
				%>
				<option id="option" value = <%=i%>> <%=departments.get(i).getName()%></option>
		<%	}%>
			



		</select> 

    </div>
    <div class="col-md-2">

        <label for="wl_drp_degreeType"><font color="#0" size="4">Degree Type</font></label> 
		<br>
		<select id="wl_drp_degreeType" name="wl_drp_degreeType" onchange = "submit();" class="btn btn-default btn-lg dropdown-toggle">
			<%
							for (int i=0;i<degreeType.size();i++){
								
								%>
					<option id = "degree" value=<%=i%> ><%=degreeType.get(i)%></option>
					<%	}%>
		</select> 
		<br>

    </div>
    <div class="col-md-4">

        <label for="wl_drp_major"><font color="#0" size="4">Major</font></label> 
        <br>
		<select id="wl_drp_major" name="wl_drp_major" onchange = "submit();" class="btn btn-default btn-lg dropdown-toggle">
				<%
							for (int i=0;i<major.size();i++){
								
								%>
					<option id = "major"  value=<%=i%>><%=major.get(i)%></option>
					<%	}%>
					
					<script>function selectmajor(){
						document.getElementById("major").value;
						advisor_form.submit();
					 }
				</script>
		</select> 
		<br>

    </div>
    
    <div class="col-md-4"></div>
    <label for="wl_drp_lastName"><font color="#0" size="4">Last Name</font></label> 
		<br>
		<select id="wl_drp_lastName" name="wl_drp_lastName" onchange = "submit();" class="btn btn-default btn-lg dropdown-toggle">
				<%
							for (int i=0;i<letters.size();i++){
								
								%>
					<option id = "letter" onclick = "selectLetter()" value=<%=i%>><%=letters.get(i)%></option>
					<%	}%>
			
					<script>function selectLetter(){
						document.getElementById("letter").value;
						advisor_form.submit();
					 }
				</script>
		</select> 
		<br>
		
		</div>
		


		
		<div class="pull-right form-inline">
			<div class="btn-group">
		
					<input type=hidden name=wl_advisor_button id="wl_advisor_button">
					<script>

					document.getElementById("wl_advisor_button").value = "all";
					</script>

					<!-- begin processing advisors  -->
					<button type="button" id="all1" onclick="alladvisors()">All</button>
					<script> function alladvisors(){
		    							document.getElementById("wl_advisor_button").value = "all";
		    							advisor_form.submit();
		    						 }
		    				</script>
					<%for (int i=0;i<array.size();i++){	%>
					
					<button type="button" id="button1<%=i%>" onclick="button<%=i%>()"><%= array.get(i).getPname()%></button>
					<script> function button<%=i%>(){
						document.getElementById("wl_advisor_button").value = "<%=array.get(i).getPname()%>";
						advisor_form.submit();
						}
									</script>
					<%	}%>
					</div>
					</div>
				</form>
			
		

	<%}
		 else{%>
	<label><font color="#0" size="5"> No advisors available for advising</font></label>
	<% } %>
	<!-- end processing advisors -->
	</div>
</div>
</div>
	<div class="container-fluid">
	<div class="date-display span12">
		

	<div id='calendar'>
	<%@ page import="uta.mav.appoint.TimeSlotComponent"%>
	<%@ page import="uta.mav.appoint.PrimitiveTimeSlot"%>
	<%@ page import="uta.mav.appoint.CompositeTimeSlot"%>
	<%@ page import="uta.mav.appoint.beans.AdvisingSchedule"%>
	<%@ page import="uta.mav.appoint.beans.Appointment"%>

	<!--  begin processing schedules -->
	<% ArrayList<TimeSlotComponent> schedules = (ArrayList<TimeSlotComponent>)session.getAttribute("schedules");
		   ArrayList<Appointment> appointments = (ArrayList<Appointment>)session.getAttribute("appointments");
			   ArrayList<TimeSlotComponent> waitlist = (ArrayList<TimeSlotComponent>)session.getAttribute("waitlist");
		
		    				%><script>
		    				$(document).ready(function(){
		    					$('#calendar').fullCalendar({
		    						header: {
		    							left:'month,basicWeek,basicDay',
		    							right: 'today, prev,next',
		    							center: 'title'
		    						},
		    						displayEventEnd : {
		    							month: true,
		    							basicWeek: true,
		    							'default' : true,
		    						}
		    						<%if (waitlist != null){%>
		    						,
		    						eventClick: function(event,element){
		    							if (event.id >= 0){
		    							//document.getElementById("id1").value = event.id;
		    							//document.getElementById("pname").value = event.title;
		    							//addToWL.submit();
		    							document.getElementById("wl_pname").value = event.title;
		    							document.getElementById("wl_date").value = event.start;
										$('#addToWLModal').modal();

		    							}
		    							else{
		    								updateWaitlist.submit();
		    							}
		    						},
		    						events: [
		 		    				<% int i = 0;
										for (i=0;i<waitlist.size();i++){%> 
		 									{
		 										title:'<%=waitlist.get(i).getName()%>',
		 										start:'<%=waitlist.get(i).getDate()+"T"+waitlist.get(i).getStartTime()%>',
		 										end:'<%=waitlist.get(i).getDate()+"T"+waitlist.get(i).getEndTime()%>',
		 										id:<%=i%>,
		 										backgroundColor: 'red'
		 									}
		 									<%if(i != (schedules.size()-1)||appointments != null){%>,<%}%>
		 					 			<%}
										if (appointments != null){
											for(i=1;i<1+appointments.size();i++){%>
		 										{
 													title:'<%=appointments.get(i-1).getAppointmentType()%>',
 													start:'<%=appointments.get(i-1).getAdvisingDate()+"T"+appointments.get(i-1).getAdvisingStartTime()%>',
 													end:'<%=appointments.get(i-1).getAdvisingDate()+"T"+appointments.get(i-1).getAdvisingEndTime()%>',
 													id:<%=-i%>,
 													backgroundColor: 'orange'
		 										}
 											<%if(i != appointments.size()){%>,<%}%>
 			 					 			<%}
										}
	 					 			}%>
	 					 			]
		    					});
		    				});
	 						</script>


<form name="addToWL" onsubmit="return addToWaitList()" action="waitlist" method="post">
	<div class="modal fade" id="addToWLModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Add to wait list</h4>
				</div>
				<div class="modal-body">
					<b>Student ID:</b><br> <input type=text name=wl_studentID id="wl_studentID"><br>
					<b>Student Email ID.:</b><br> <input type=text name=wl_studentEmail id="wl_studentEmail"><br>
					<b>Student Phone No.:</b><br> <input type=text name=wl_studentPhoneNum id="wl_studentPhoneNum"><br>
					<b>Advisor:</b><br><input name=wl_pname id="wl_pname" readonly><br>
					<b>Date:</b><br><input name=wl_date id="wl_date" readonly><br>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						Close</button>
					<input type="submit" value="Submit">
				</div>
			</div>
		</div>
	</div>
</form>
<script>
function addToWaitList(){
	if (document.getElementById("wl_studentID").value == ""){
		alert("Student ID is required.");
		return false;
	}
	if (document.getElementById("wl_studentEmail").value == ""){
		alert("Student Email is required.");
		return false;
	}
	if (document.getElementById("wl_studentPhoneNum").value == ""){
		alert("Student Phone number  is required.");
		return false;
	}
		//return confirm('Are you sure you want to get added to the waitlist?');
	var sID = document.getElementById("wl_studentID").value;
	var sEmail = document.getElementById("wl_studentEmail").value;
	var sPhoneNum = document.getElementById("wl_studentPhoneNum").value;
	addToWL.submit();
	}
</script>
	<br /> <br />
	<hr>
</div>
</div>
</div>
<style>
#calendar {
	background-color: white;
}
</style>
<%@include file="templates/footer.jsp"%>