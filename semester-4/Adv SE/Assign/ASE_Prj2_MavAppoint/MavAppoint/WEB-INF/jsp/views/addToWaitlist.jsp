<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />

<style>
.custab {
	border: 1px solid #ccc;
	padding: 5px;
	margin: 5% 0;
	box-shadow: 3px 3px 2px #ccc;
	transition: 0.5s;
	background-color: #e67e22;
}

.custab:hover {
	box-shadow: 3px 3px 0px transparent;
	transition: 0.5s;
}
</style>

<div class="container">
	<div class="btn-group">
		<form action="" method="post" name="add">
			
			<div class="row col-md-16  custyle">
				<table class="table table-striped custab">
					<thead>
						<tr>
							<th>Advisor Name</th>
							<th>Appointment Date</th>
							<th>Start Time</th>
							<th>End Time</th>
							<th>Advising Type</th>
							<th>Advising Email</th>
							<th>Description</th>
							<th>UTA Student ID</th>
							<th>Student Email</th>
							
							
							
							
							<!-- This adds "Phone Number" to the table  -->
							<th>Phone Number</th> 
							
							
							
							
							
							<th class="text-center">Action</th>
						</tr>
					</thead>
					<%@ page import="java.util.ArrayList"%>
					<%@ page import="uta.mav.appoint.beans.Appointment"%>
					<!-- begin processing appointments  -->
					<% ArrayList<Appointment> array = (ArrayList<Appointment>)session.getAttribute("appointments");
		    			if (array != null){%>
					<%for (int i=0;i<array.size();i++){ %>
					<tr>
						<td><%=array.get(i).getPname()%></td>
						<td><%=array.get(i).getAdvisingDate()%></td>
						<td><%=array.get(i).getAdvisingStartTime()%></td>
						<td><%=array.get(i).getAdvisingEndTime()%></td>
						<td><%=array.get(i).getAppointmentType()%></td>
						<td><%=array.get(i).getAdvisorEmail()%></td>
						<td><%=array.get(i).getDescription() %></td>
						<td><%=array.get(i).getStudentId()%></td>
						<td><%=array.get(i).getStudentEmail()%></td>
						
						
						
						<!-- =array.get(i).getstudentPhoneNumber() -->
						<td><%=array.get(i).getStudentPhoneNumber()%></td>
						
						
						
						
						
						
						<td class="text-center"><button type="button" id=button1
								<%=i%> onclick="button<%=i%>()">Cancel</button></td>
						<td class="text-center"><button type="button" id=button2_
								<%=i%> onclick="button_<%=i%>()">Edit</button></td>
						<td class="text-center"><button type="button" id=button3_
								<%=i%> onclick="button__<%=i%>()">Email</button></td>
					</tr>
					<script> function button<%=i%>(){
										document.getElementById("cancel_button").value = "<%=array.get(i).getAppointmentId()%>"; 
										if (validate() == true){
											cancel.submit();
										}
								}</script>
					<script> function button_<%=i%>(){
										document.getElementById("id2").value = "<%=array.get(i).getAppointmentId()%>"; 
										document.getElementById("apptype").value = "<%=array.get(i).getAppointmentType()%>"; 
										document.getElementById("date").value = "<%=array.get(i).getAdvisingDate()%>";
										document.getElementById("start").value = "<%=array.get(i).getAdvisingStartTime()%>"; 
										document.getElementById("end").value = "<%=array.get(i).getAdvisingEndTime()%>"; 
										document.getElementById("pname").value = "<%=array.get(i).getPname()%>"; 
										document.getElementById("description").value = "<%=array.get(i).getDescription()%>";
										
										
										
										
										//<Hien>
										document.getElementById("StudentPhoneNumber").value = "<%=array.get(i).getStudentPhoneNumber()%>";
										
										
										
										
										
										$('#addApptModal').modal();
								}</script>
					<script> function button__<%=i%>(){
										document.getElementById("to").value = "<%=array.get(i).getStudentEmail()%>";
										$('#emailModal').modal();
								}</script>
					<script> function emailSend(){
									var to = document.getElementById("to").value;
									var body = document.getElementById("email").value;
									var subject = document.getElementById("subject").value;
									var params = ('to='+to+'&body='+body+'&subject='+subject);
									var xmlhttp;
									xmlhttp = new XMLHttpRequest();
									xmlhttp.onreadystatechange=function(){
										if (xmlhttp.readyState==4){
											alert("Email sent.");	
											return false;
										}
									}
									xmlhttp.open("POST","notify",true);
									xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
									xmlhttp.setRequestHeader("Content-length",params.length);
									xmlhttp.setRequestHeader("Connection","close");
									xmlhttp.send(params);
								}
								</script>
					</div>
					<%	}
		    			}
		    			%>
					<!-- end processing advisors -->
				</table>
		</form>
	</div>
</div>


<%@include file="templates/footer.jsp"%>