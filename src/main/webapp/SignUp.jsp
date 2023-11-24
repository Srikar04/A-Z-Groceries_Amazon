<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="form.css">
<head>
<meta charset="UTF-8">
<link rel="icon" href="images/logo.jpeg" type="image/icon type">
<title>A-Z Groceries</title>
<style>
body{
	background-image: url("images/wallpaper.jpg");
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$("form").submit(function(e){
			let username = $("#username").val();
			let password = $("#password").val();
	        let confirmPassword = $("#confirmPassword").val();
	        let phoneNumber = $("#phoneNumber").val();
	        
	        if (username.trim() === '' || password.trim() === '' || confirmPassword.trim() === '' || phoneNumber.trim() === '') {
	            alert("Username, password, confirm password, and Phone Number are required fields.");
	            e.preventDefault();
	        }

	        if (password !== confirmPassword) {
	            alert("Password and Confirm Password must match.");
	            e.preventDefault(); 
	        }

	        if (!/^\d+$/.test(phoneNumber) || phoneNumber.length !== 10) {
	            alert("Phone Number should only be a 10 digit number.");
	            e.preventDefault();
	        }
		})
	})
</script>
</head>
<body>
	<jsp:include page="header.jsp" />
		<form method="post" action="SignUp">
			<div class="container">
			    <label for="userType">UserType:</label>
			    <select id="userType" name="userType"> 
			        <option value="Customer">Customer</option>
			        <option value="Shopkeeper">Shopkeeper</option>
			        <option value="Amazon">Amazon</option>
			    </select>
			    <br><br>
			    
			    <label for="username">UserName:</label>
			    <input type="text" id="username" name="username" required/><br><br>
			    
			    <label for="password">Password:</label>
			    <input type="password" id="password" name="password" required><br><br>
			    
			    <label for="confirmPassword">Confirm Password:</label>
			    <input type="password" id="confirmPassword" name="confirmPassword" required><br><br>
			    
			    <label for="phoneNumber">Phone Number:</label>
			    <input type="tel" id="phoneNumber" name="phoneNumber" required><br><br>
			    
			    <label for="email">Email (Optional):</label>
			    <input type="email" id="email" name="email"><br><br>
			    
			    <div class="error">
				    <%String error = (String)request.getAttribute("errorMessage") ;%>
					<%if(error != null && !error.isEmpty()){%>
				    <%= error %>
				    <% } %>
			    </div>
			    
			    <br>
			    
			    <input type="submit" value="Create My Account">
			    
		    </div>
	    </form>
	<br><br>
    <jsp:include page="footer.jsp" />
</body>
</html>