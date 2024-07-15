<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="org.uta.sams.beans.UserBean,org.uta.sams.controller.ProgramController,java.util.ArrayList"%>
<%@ page import = "java.lang.reflect.*" %>



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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sams</title>
    </head>
    <body>
    
<div style ="display:flex">

          <jsp:include page="/jsp/commons/header.jsp"/>




 
    
     
     
 

   <div class="container">
  <div class="px-4 py-5 px-md-5 text-center text-lg-start" style="background-color: hsl(0, 0%, 96%)">
 
      <div class="col gx-lg-5 align-items-center">
        <div class=" mb-5 mb-lg-0">
          <h1 class="my-5 display-3 fw-bold ls-tight">
            OFFICE OF<br />
            <span class="text-primary">INDIAN EDUCATION</span>
          </h1>
          <div class="row">
          <div class="col">
          <p style="color: hsl(217, 10%, 50.8%)">
            The Office of International Education is a comprehensive unit committed to 
            providing programs and services that transform the lives of our students, 
            faculty, staff, and community through high impact, international education experiences.
          </p>
          </div>
           <div class="col col-lg-6 mb-5 mb-lg-0 ">
          <div class="card">
            <div class="card-body py-5 px-md-5 ">
              <form action="<%= response.encodeURL("welcome.jsp") %>" method="post">
                <!-- 2 column grid layout with text inputs for the first and last names -->
                <div class="row">
                  <div class="mb-4">
                    <div class="form-outline">
                          <label class="form-label" for="form3Example1">User</label>
                      <input type="text" id="form3Example1" class="form-control" name="j_username"/>
                
                    </div>
                  </div>
                  <div class="mb-4">
                    <div class="form-outline">
                     <label class="form-label" for="form3Example2">Password</label>
                      <input type="text" id="form3Example2" class="form-control" name ="j_password"/>
                     
                    </div>
                  </div>
                </div>

             


          
                <button type="submit" class="btn btn-primary btn-block mb-4" value="submit">
                  Login
                </button>
                
                
                 <button type="reset" class="btn btn-primary btn-block mb-4" value="reset">
                  Reset
                </button>

               
              </form>
            </div>
          </div>
        </div>
      </div>
      </div>
        </div>
        </div>

       
    </div>
  </div>



 
   




    <table align="center" cellpadding="0" cellspacing="0"><tr><td class ="footer-container">
    <jsp:include page="/jsp/commons/footer.jsp"/>
    </td></tr></table>

    
    </body>
</html>
