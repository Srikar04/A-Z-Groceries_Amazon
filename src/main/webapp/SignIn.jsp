<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="form.css">
<meta charset="UTF-8">
<link rel="icon" href="images/logo.jpeg" type="image/icon type">
<title>A-Z Groceries</title>
<style>
body{
	background-image: url("images/wallpaper.jpg");
}
</style>
</head>
<body>
	<jsp:include page="header.jsp" />
		<form method="post" action="SignIn">
			<div class="container">
				<h1>Welcome to A-Z Online Groceries</h1><br><br>
				<label for="usertype">User Type:</label>
		        <select name="usertype" id="usertype">
		            <option value="Customer">Customer</option>
		            <option value="Shopkeeper">Shopkeeper</option>
		            <option value="Amazon">Amazon</option>
		        </select>
		        <br><br>
		        
				<label for="username" >UserName:</Label>
				<input type="text" id="username" name="username" required/><br><br>
				
				<label for="password">Password:</label>
				<input type="password" id="password" name="password" required><br><br>
				
				<input type="submit" value="Sign In">
				<br><br>
				<div class="error">
					<%String error = (String)request.getAttribute("errorMessage") ;%>
					<%if(error != null && !error.isEmpty()){%>
				    <%= error %>
				    <% } %>
				</div>
				 <br>
				<a href="SignUp.jsp" style="text-decoration: none; color: purple; font-size: 16px;">Don't have an account?Create an account</a>
			</div>
		</form>
	<br>
	<br>
    <jsp:include page="footer.jsp" />
</body>
</html>