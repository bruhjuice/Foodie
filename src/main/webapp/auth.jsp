<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="443326407406-l54k040rl05jrh1huhjubvlblb5499ej.apps.googleusercontent.com"
          name="google-signin-client_id">
    <title>Login / Register</title>
    <link href="https://fonts.googleapis.com" rel="preconnect">
    <link crossorigin href="https://fonts.gstatic.com" rel="preconnect">
    <link
            href="https://fonts.googleapis.com/css2?family=Lobster&family=Roboto:wght@300&display=swap"
            rel="stylesheet">
    <script crossorigin="anonymous"
            src="https://kit.fontawesome.com/3204349982.js"></script>
    <script async defer src="https://apis.google.com/js/platform.js"></script>
    <link href="index.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Roboto"
          rel="stylesheet" type="text/css">
    <script src="https://apis.google.com/js/api:client.js"></script>
<!--     <script src="https://apis.google.com/js/platform.js" async defer></script>
 -->
</head>
<body>



		 <%boolean error = false;
		 String temp =(String) request.getAttribute("error");
		
		 if (request.getAttribute("error") != null && temp.contentEquals("yes"))
			 {
			 error = true;	 
			 }
		if (error)
		{
			out.print("<p id = 'errorMsg'> Invalid email or password. Or, bad Google login. Please try again.</p>");
		}

		%>
		
		





        <ul class = "navBar">
  			<li><a href="index.jsp" class = "logo">Foodie!</a></li>
  			<li style = "float:right"><a href="auth.jsp">Login/Register</a></li>
  			<li style = "float:right"><a href="index.jsp">Home</a></li>
		</ul>
		
		<div class = "formDiv">
		
		<form action = "login" method = "GET" class = "loginFormLeft">
		<h1> Login</h1>
		<br>
		<label for = "loginEmail"> Email </label>
		<br>
		<input class = "regForm" type = "text" id = "loginEmail" name = "loginEmail">
		<br>
		<br>
		<label for = "loginPassword"> Password </label>
		<br>
		<input class = "regForm" type = "password" id = "loginPassword" name = "loginPassword">
		<br>
		<br>
		<button class = "submitButton" type = "submit"> <i class = 'fa fa-sign-in'> Sign In </i> </button>
		<br>
 		<div id="my-signin2"></div>
                <script>
                function onSuccess(googleUser) {
	
					  var profile = googleUser.getBasicProfile();
					  console.log('ID: ' + profile.getId()); 
					  console.log('Name: ' + profile.getName());
					  console.log('Image URL: ' + profile.getImageUrl());
					  console.log('Email: ' + profile.getEmail()); 
					  var name = ""
					  var profileName = profile.getName()
					  for (let i in profileName)
						  {
						  if (profileName[i] == " ")
							  {
							  name += "=";
							  }
						  else
							  {
							  name += profileName[i];
							  }
						  }
				/* 	  document.cookie = "name=" + profile.getName().split(" ")[0];
				 */	  
				 	  document.cookie = "name=" + name;
				 	  var redirect = "http://localhost:8080/PA2/index.jsp";
					  window.location.replace(redirect);
	
	
/* 
                      var auth2 = gapi.auth2.getAuthInstance();
                      auth2.disconnect(); */
                    }
                function onFailure(res) {
                    console.log('Fail: ',res); // This is null if the 'email' scope is not present.
                }
                function renderButton() {
					
	var screenWidth = screen.width;
	screenWidth = screenWidth * .36;
	var screenHeight = screen.height;
	screenHeight = screenHeight * .04;
                    gapi.signin2.render('my-signin2', {
                      'scope': 'profile email',
                      'width': screenWidth,
                      'height': screenHeight,
                      'longtitle': true,
                      'theme': 'dark',
                      'onsuccess': onSuccess,
                      'onfailure': onFailure
                    });
                }
                </script>
                <script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>



 	
		
		
		
		</form>
		  <%String regEmail = (String) request.getParameter("regEmail");
				if (regEmail == null)
				{
					regEmail = "";
				}
			String regName = (String) request.getParameter("regUsername");
				if (regName == null)
				{
					regName = "";
				}
			String regPassword = (String) request.getParameter("regPassword");
				if (regPassword == null)
				{
					regPassword = "";
				}
			String confirmPassword = (String) request.getParameter("confirmPassword");
				if (confirmPassword == null)
				{
					confirmPassword = "";
				}
				%>
		
		<form action = "register" method = "GET" class = "loginFormRight">
		<h1> Register</h1>
		<label for = "registerEmail"> Email </label>
		<br>
		<input class = "regForm" type = "text" id = "registerEmail" name = "registerEmail" required value =<%=regEmail%>  >
		<br>
		<br>
		<label for = "registerName"> Name </label>
		<br>
		<input class = "regForm" type = "text" id = "registerName" name = "registerName" required value =<%=regName%>  >
		<br>
		<br>
		<label for = "registerPassword"> Password </label>
		<br>
		<input class = "regForm" type = "password" id = "registerPassword" name = "registerPassword" required>
		<br>
		<br>
		<label for = "confirmPassword">Confirm Password </label>
		<br>
		<input class = "regForm" type = "password" id = "confirmPassword" name = "confirmPassword"  required>
		<br>
		<br>
		<input type="checkbox" id="terms" name="terms" value="accepted" required>
		<label for="checkbox" > I have read and agree to all terms and conditions of SalEats.</label>	
		<br>
		<br>
		<button class = "submitButton" type = "submit"> <i class = 'fas fa-user-plus'> Create Account </i> </button>
		<!-- put value = "var" -->
		
		</form>
		
	
		
		
		
		</div>


</body>
</html>