<% String jsppath=request.getContextPath()+"/jsp/commons/";%>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<link rel="stylesheet" href="<%=jsppath%>sams/fsmenu.css" type="text/css" title="International Stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<nav class="header">
                <div class="header-container">
                    <a href="#">
                    </a>
                    <a data-toggle="toggle-header" data-target="#header-items" href="#"
                    >
                    </a>
                </div>
              
                <div id="header-items" class="header-menu" >
                    <a href="https://www.uta.edu/student-affairs/oie" target="_blank" class="header-menu-item">OIE Home</a>
                    <a href="https://www.uta.edu/about" target="_blank" class="header-menu-item">About OIE</a>
                    <a href="https://www.uta.edu/student-affairs/oie/about-oie/contact-us" target="_blank"class="header-menu-item">Contact Us</a>
                    <a href="https://www.uta.edu/" target="_blank" class="header-menu-item">UTA Home</a>
                    <a href="https://www.uta.edu/student-affairs/orientation/register" target="_blank" class="primary-default-button">New Students</a>
                    <a href="https://www.uta.edu/student-success/university-studies/prospective" target="_blank" class="header-menu-item">Prospective Students</a>
                    <a href="https://www.uta.edu/alumni" target="_blank" class="header-menu-item">Alumni</a>
                    <a class="header-menu-item" href="https://www.uta.edu/directory"  target="_blank" >Find People</a>
                    <a class="header-menu-item" href="http://www.outlook.com/mavs.uta.edu" target="_blank">Check E-mail</a>
                    <form name="uta-search" method="get" action="https://www.uta.edu/search" target="_blank" >
                        <div id="secondary" align="center">
                        <input name="domains" value="uta.edu" type="hidden">
                        <input name="sitesearch" value="uta.edu" type="hidden">
                        <input name="q" id="q" value="Search UTA" onFocus="if(this.value=='Search UTA')value=''" onBlur="if(this.value=='')value='Search UTA';" type="text" size="20">
                        <input name="Submit" value="Go!" type="submit">
                        </div>
                    </form>
<!--                     Hamburger -->
                    <a href="javascript:void(0);" class="icon" onclick="hamBurger()">
                    <i class="fa fa-bars"></i>
                    </a>
                </div>
            </nav>
<script>
function hamBurger(){
	var headNavItem = document.getElementById("header-items");
	if(headNavItem.className === "header-menu"){
		headNavItem.className +=" responsive";
	}
	else{
		headNavItem.className = "header-menu";
	}
}
</script>