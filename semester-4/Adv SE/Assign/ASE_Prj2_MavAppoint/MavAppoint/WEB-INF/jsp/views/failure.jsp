<%@ include file="templates/no_header.jsp"%>
<html>
<head>
    <title>Failed</title>
    <link rel="stylesheet"
	href="components/bootstrap3/css/bootstrap.css">
	<script type="text/javascript"
	src="components/bootstrap3/js/bootstrap.min.js"></script>
</head>
	<div class="container" style="margin-top:120px">
		<div class="jumbotron">
			<span>
				<img src="img/wrong.png" style="float:left; height:90px; width:90px;">
				<h1>Success!</h1>
			</span>
    		
       	    <span id="second" >3</span> seconds will redirect to previous page...
       	    <div class="progress progress-striped active">
   <div id ="progress" class="progress-bar role="progressbar" 
      aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 
      style="width: 0%;">
      <span class="sr-only"></span>
   </div>
</div>
    	</div>
	</div>
    

<script type="text/javascript">

 
    setInterval("clock()", 1000);
    function clock() {
        var span = document.getElementById('second');
        var num = span.innerHTML;
        if(num != 0) {
            num--;
            span.innerHTML = num;
        }
    };
    
    var num = 0;
    setInterval("clock2()", 100);
    function clock2() {
    	var prog = document.getElementById('progress');
        
        if(num <= 100) {
            num = num + 10/3;
            prog.setAttribute("style","width:"+num+"%");
        }
    };
</script>


</html>