<% String jsppath=request.getContextPath()+"/jsp/commons/";%>
<%@page import="org.uta.sams.controller.ProgramController,org.uta.sams.beans.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OIE</title>

<link rel="stylesheet" href="<%=jsppath%>sams/sample.css" type="text/css" title="International Stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</head>
<body>
<body id="page-top">

    <!-- Page Wrapper -->
    <div class="main-sidebar">


        <ul class="left-sidebar navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <%
                String name="";
			    if(session.getAttribute("user")!=null)name=((UserBean)session.getAttribute("user")).getUsername();
                System.out.println("name: "+name);

            %>
            <a class="sidebar-brand d-flex align-items-center justify-content-center">
            
                    <i class="fas fa-laugh-wink"></i>
          
                <div class="sidebar-brand-text mx-3">Welcome <%=name%>. </div>
            </a>
            <!-- Divider -->
            <hr class="sidebar-divider my-0">

            <!-- Nav Item - Dashboard -->
            <li class="nav-item active">
                <a class="nav-link" href="../user/welcome.jsp">
                    <i class="fas fa-fw fa-tachometer-alt"></i>
                    <span>Dashboard</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

          

            <!-- Nav Item - Pages Collapse Menu -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo"
                    aria-expanded="true" aria-controls="collapseTwo">
                    <i class="fas fa-fw fa-cog"></i>
                    <span>Student and Scholar Services</span>
                </a>
                <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                     
                        <a class="collapse-item" href="https://www.uta.edu/student-affairs/oie/isss/immigration-advising"  target="_blank" >OIE Student Advising</a>
                         <a class="collapse-item" href="https://www.uta.edu/academics/schools-colleges/education/current-students/field-experiences/handbooks" target="_blank" >Student and Scholar Handbook</a>
                        <a class="collapse-item" href="https://www.uta.edu/student-affairs/oie/isss/change-of-status" target="_blank">Change of Status</a>
                        <UL>

                <LI><A href="https://www.uta.edu/student-affairs/oie/isss/change-of-status/b-2-to-f-1" target="_blank">
                <DIV class=sublist>B-2 to F-1</DIV></A></LI>
                <LI><A href="https://www.uta.edu/student-affairs/oie/isss/change-of-status/b-2-to-f-2" target="_blank">
                <DIV class=sublist>B-2 to F-2</DIV></A></LI>
                <LI><A href="https://www.uta.edu/student-affairs/oie/isss/change-of-status/f-1-to-f-2" target="_blank">
                <DIV class=sublist>F-1 to B-2</DIV></A></LI>
                <LI><A href="https://www.uta.edu/student-affairs/oie/isss/change-of-status/f-2-to-f-1" target="_blank">
                <DIV class=sublist>F-1 to F-2</DIV></A></LI>
                <LI><A href="https://www.uta.edu/student-affairs/oie/isss/change-of-status/f-2-to-f-1" target="_blank">
                <DIV class=sublist>F-2 to F-1</DIV></A></LI>
                <LI><A href="https://www.uta.edu/student-affairs/oie/isss/change-of-status/h-1-to-f-1" target="_blank">
                <DIV class=sublist>H-1B to F-1</DIV></A></LI>
                <LI><A href="https://www.uta.edu/student-affairs/oie/isss/change-of-status/h-4-to-f-1" target="_blank">

                <DIV class=sublist>H-4 to F-1</DIV></A></LI></UL>

                         <a class="collapse-item" href="https://www.uta.edu/student-affairs/oie/isss/current-f-1-students" target="_blank">Other F-1 Issues</a>

                    <UL>

                <LI><A href="https://cdn.web.uta.edu/-/media/project/website/student-affairs/oie/documents/isss/current-f1-students/lcfw.ashx" target="_blank">
                <DIV class=sublist>Less than Full Course Waiver</DIV></A></LI>
                <LI><A href="https://www.uta.edu/student-affairs/oie/isss/reinstatement" target="_blank">

                <DIV class=sublist>Reinstatement</DIV></A></LI></UL>

                        <a class="collapse-item" href="https://www.uta.edu/student-affairs/oie/isss/employment" target="_blank">Employment Issues</a>
                          <UL>

                <LI><A href="https://www.uta.edu/student-affairs/oie/isss/employment/cpt" target="_blank">
                <DIV class=sublist>Curricular Practical Training</DIV></A></LI>
                <LI><A href="https://www.uta.edu/student-affairs/oie/isss/employment/opt-categories-simplified" target="_blank">
                <DIV class=sublist>Optional Practical Training</DIV></A></LI>
                <LI><A href="https://www.uta.edu/student-affairs/oie/isss/employment/economic-hardship" target="_blank">
                <DIV class=sublist>Off Campus Employment due to Economic
                Necessity</DIV></A></LI>
                <LI><A href="https://resources.uta.edu/student-affairs/careers/student-employment.php" target="_blank">

                <DIV class=sublist>On- Campus Employment
              Forms</DIV></A></LI></UL>




                        
                         <a class="collapse-item" href="https://www.uta.edu/student-affairs/oie/isss/social-security" target="_blank">Social Security</a>
                         <UL>
                <LI><A

                href="https://www.uta.edu/student-affairs/oie/isss/social-security" target="_blank">
                <DIV class=sublist>Social Security Letter Request</DIV></A></LI>
                <LI><A
                href="http://www.uta.edu/oie/services/social_security_act" target="_blank">

                <DIV class=sublist>Changes to Social Security
              Act</DIV></A></LI></UL>



                         <a class="collapse-item" href="https://www.uta.edu/administration/registrar/students/registration/errors-holds" target="_blank">Academic Issues</a>


              <UL>

                <LI><A href="https://www.uta.edu/admissions/forms/undergraduate-change-of-major" target="_blank">
                <DIV class=sublist>Change of Educational Level or
                Major</DIV></A></LI>
                <LI><A
                href="https://cdn.web.uta.edu/-/media/project/website/student-affairs/oie/documents/optprogcompletion.ashx?revision=aaa9d2fc-31cb-4488-8b28-c9e2eec110be"
                target=_blank>
                <DIV class=sublist>Program Completion / Extension of Stay
                Form</DIV></A></LI>
                <LI><A href="https://www.uta.edu/admissions/apply/transfer" target="_blank">
                <DIV class=sublist>Transfer</DIV></A></LI>
                <LI><A href="https://www.uta.edu/administration/registrar/students/registration/concurrent-enrollment"

                target=_blank>
                <DIV class=sublist>Concurrent Enrollment
              Form</DIV></A></LI></UL>




                        <a class="collapse-item" href="https://www.uta.edu/student-affairs/oie/isss/travel-information" target="_blank">Travel</a>
                        
                        <UL>

                <LI><A href="https://www.uta.edu/student-affairs/oie/isss/travel-information">
                <DIV class=sublist>All You Need to Know about
                Travel</DIV></A></LI>
                <LI><A href="https://help.cbp.gov/s/article/Article-1218?language=en_US" target="_blank">
                <DIV class=sublist>Travel to Canada or Mexico</DIV></A></LI>
                <LI><A
                href="https://www.usembassy.gov/"

                target=_blank>
                <DIV class=sublist>US Embassies and Consulates
                Worldwide</DIV></A></LI></UL>





                           <a class="collapse-item" href="https://www.uta.edu/student-affairs/oie/isss/current-f-1-students" target="_blank">Request for Letters</a>
                            <UL>

                <LI><A href="https://www.uta.edu/student-affairs/oie/isss/f-1-j-1-office-procedures"
                target=_blank>
                <DIV class=sublist>F-1 Rules (SEVIS)</DIV></A></LI>
                <LI><A href="https://www.uta.edu/academics/schools-colleges/education/current-students/educator-certification/licensure" target=_blank>
                <DIV class=sublist>Department of State</DIV></A></LI>
                <LI><A
                href="https://listserv.uta.edu/scripts/wa.exe?A3=ind0503B&L=IO-L&E=quoted-printable&P=10514&B=------_%3D_NextPart_001_01C5240E.A6BFF727&T=text%2Fhtml;%20charset=us-ascii&pending="
                target=_blank>
                <DIV class=sublist>NSEERS</DIV></A></LI>
                <LI><A href="https://web-ded.uta.edu/cedwebfiles/osh/5400%20-%20Shipyard%20Guidelines%20For%20Ergonomics.pdf" target=_blank>
                <DIV class=sublist>Department of Labor</DIV></A></LI>
                <LI><A href="https://fmjfee.com/i901fee/index.html">
                <DIV class=sublist>SEVIS Fee</DIV></A></LI>
                <LI><A href="https://www.uta.edu/student-affairs/oie/isss/social-security" target=_blank>
                <DIV class=sublist>Social Security Administration</DIV></A></LI>
                <LI><A href="https://www.uta.edu/student-affairs/oie/isss/travel-information"

                target=_blank>
                <DIV class=sublist>US VISIT</DIV></A></LI></UL>



                         <a class="collapse-item" href="https://www.uta.edu/student-affairs/oie/isss/immigration-advising" target="_blank">Immigration Links</a>
                        <a class="collapse-item" href="https://www.uta.edu/administration/fao/scholarships" target="_blank">For scholars</a>
                        
                          <a class="collapse-item" href="https://www.uta.edu/administration/fao/apply-for-aid/international-students" target="_blank">Mustaque Ahmed Loan Fund</a>
                         <a class="collapse-item" href="https://www.uta.edu/student-affairs/oie/isss/employment/opt-categories-simplified/post-completion" target="_blank">Forms</a>
                     
                    </div>
                </div>
            </li>

            <!-- Nav Item - Utilities Collapse Menu -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities"
                    aria-expanded="true" aria-controls="collapseUtilities">
                    <i class="fas fa-fw fa-wrench"></i>
                    <span>Programs and Events</span>
                </a>
                <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities"
                    data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
          
                        <a class="collapse-item" href="https://www.uta.edu/academics/academic-calendar" target="_blank">Calendar of Events</a>
                        <a class="collapse-item" href="https://www.uta.edu/student-affairs/oie" target="_blank">OIE Programs and Resources</a>
                         <UL>
                <LI><A

                href="https://www.uta.edu/student-affairs/oie/events/i-week" target="_blank">
                <DIV class=sublist>International Week</DIV></A></LI>
                <LI><A
                href="https://www.uta.edu/student-affairs/oie/events/i-e-week" target="_blank">
                <DIV class=sublist>International Education Week</DIV></A></LI>
                <LI><A
				href="https://events.uta.edu/event/global_grounds_4175#.ZCiQw-zMLdo" target="_blank">
                <DIV class=sublist>Global Grounds</DIV></A></LI>
                <LI><A href="https://www.uta.edu/student-success/path-to-graduation/new-student-courses/pal" target="_blank">
                <DIV class=sublist>Peer Advisors</DIV></A></LI>
                <LI><A href="https://www.uta.edu/enews/mavwire/2015/02/05.php" target="_blank">
                <DIV class=sublist>The Link: International Friendship
                Program</DIV></A></LI>
                <LI><A href="https://www.uta.edu/pats/transportation/mav-mover-shuttles.php" target="_blank">
                <DIV class=sublist>Mav Mover Saturday Shuttle</DIV></A></LI>
                <LI><A href="https://www.uta.edu/student-affairs/oie/events/opt-cpt-seminars" target="_blank">
                <DIV class=sublist>OPT and CPT Seminars</DIV></A></LI>
                <LI><A href="https://listserv.uta.edu/scripts/wa.exe?A3=ind0810C&L=IO-L&E=quoted-printable&P=634&B=------_%3D_NextPart_001_01C92FA4.FB01EFA2&T=text%2Fplain;%20charset=US-ASCII&header=1" target="_blank">
                <DIV class=sublist>OIE Listserv</DIV></A></LI></UL>



                        <a class="collapse-item" href="https://www.uta.edu/student-affairs/oie/isss" target="_blank">International Student Handbook</a>
                        <a class="collapse-item" href="https://www.uta.edu/student-affairs/student-organizations" target="_blank">Student Organizations</a>
                        <a class="collapse-item" href="https://www.uta.edu/student-affairs/oie/story-carrier-folder/kassi-bryan-noel" target="_blank">The International Student
                         Organization(ISO)</a>
                        <a class="collapse-item" href="https://www.uta.edu/administration/fao/apply-for-aid/forms" target="_blank">Forms</a>
                     
                    </div>
                </div>
            </li>

        

            <!-- Nav Item - Pages Collapse Menu -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePages"
                    aria-expanded="true" aria-controls="collapsePages">
                    <i class="fas fa-fw fa-folder"></i>
                    <span>Study Abroad</span>
                </a>
                <div id="collapsePages" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                    
                    <a class="collapse-item" href="https://bpb-us-e1.wpmucdn.com/blog.uta.edu/dist/1/4994/files/2021/06/Intrinsic-Motivation-Inventory-IMI.pdf" target="_blank">Interest Questionaire</a>
                        <a class="collapse-item" href="https://library.uta.edu/digitalgallery/home" target="_blank">Photo Gallery</a>
                        <a class="collapse-item" href="https://www.uta.edu/admissions" target="_blank">Getting Started</a>
                          <UL>

                <LI><A href="https://studyabroad.uta.edu/" target="_blank">
                <DIV class=sublist>Why Study Abroad</DIV></A></LI>
                <LI><A href="https://www.uta.edu/" target="_blank">

                <DIV class=sublist>Where to Begin</DIV></A></LI></UL>



                     
                        <a class="collapse-item" href="https://studyabroad.uta.edu/index.cfm?FuseAction=Abroad.ViewLink&Parent_ID=0&Link_ID=4D6DBE8E-BCDE-E7F3-5E541761040A756A" target="_blank">UTA Study Abroad Programs</a>
                        <a class="collapse-item" href="https://events.uta.edu/" target="_blank">Calendar of Events &amp;  Deadlines</a>
                        
                                              <a class="collapse-item" href="https://www.uta.edu/administration/fao" target="_blank">Financial Aid and Scholarships</a>
                        <a class="collapse-item" href="https://www.uta.edu/admissions/apply" target="_blank">Applications</a>
                        <a class="collapse-item" href="https://www.uta.edu/campus-ops/mavexpress/" target="_blank">International Student Identity
              Cards</a>
                        <a class="collapse-item" href="https://studyabroad.uta.edu/index.cfm?FuseAction=Abroad.ViewLink&Parent_ID=4D756F72-BCDE-E7F3-5F279204300F2A78&Link_ID=664B5B7C-BCDE-E7F3-5118BD03398E81AB" target="_blank">Travel Resources</a>
                        <a class="collapse-item" href="https://studyabroad.uta.edu/index.cfm?FuseAction=Abroad.ViewLink&Parent_ID=0&Link_ID=4D7623B8-BCDE-E7F3-5F8322E6587DDD93" target="_blank">For Mavs Currently Abroad</a>
                        
                     
                                           <a class="collapse-item" href="https://www.uta.edu/student-affairs/maverick-advantage/global-engagement%20%0d" target="_blank"> Meet our Mavs Overseas</a>
                        <a class="collapse-item" href="https://academicpartnerships.uta.edu/" target="_blank">Other Overseas Opportunities</a>
                    
                    
                    </div>
                </div>
            </li>
            
            
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePages2"


                    aria-expanded="true" aria-controls="collapsePages2">
                    <i class="fas fa-fw fa-folder"></i>
                    <span>International Resource Center</span>
                    
                </a>
                <div id="collapsePages2" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                                        <a class="collapse-item" href="https://libguides.uta.edu/publicaffairs-plan" target="_blank">Guides &amp; Planning</a>
                        <a class="collapse-item" href="https://www.uta.edu/student-affairs/parents-family/programs/travel" target="_blank">Transportation &amp;
              Accomodations</a>
              
                        <a class="collapse-item" href="https://www.uta.edu/academics/schools-colleges/liberal-arts/departments/modern-languages" target="_blank">Languages</a>
                        <a class="collapse-item" href="https://www.uta.edu/campus-ops/emergency-management" target="_blank">Weather</a>
                        <a class="collapse-item" href="https://www.uta.edu/maps" target="_blank">Maps</a>
                        <a class="collapse-item" href="https://www.uta.edu/security/" target="_blank">Security &amp; Travel Tips</a>
                        
                                              <a class="collapse-item" href="https://www.uta.edu/student-affairs/health-services" target="_blank">Health</a>
                        <a class="collapse-item" href="https://www.uta.edu/student-affairs/oie" target="_blank">International Education
          Links</a>
          
          
          
  
                    </div>
                </div>
            </li>

            <!-- Nav Item - Utilities Collapse Menu -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#logOutDiv"
                    aria-expanded="true" aria-controls="collapseUtilities">
                    <i class="fas fa-fw fa-wrench"></i>
                    <span>Log Out</span>
                </a>
                <div id="logOutDiv" class="collapse" aria-labelledby="headingUtilities"
                    data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">

                        <a class="collapse-item" href="../user/logoff.jsp">Log Out</a>
                    </div>
                </div>
            </li>

           

        </ul>
        <!-- End of Sidebar -->
</div>



</body>
</body>
</html>