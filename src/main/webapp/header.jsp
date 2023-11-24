<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="header.css">
<link rel="icon" href="images/logo.jpeg" type="image/icon type">
<title>A-Z Groceries</title>
</head>
<body>
	<header>
		<nav class="navbar">
			<div class="nav">
			    <div class="nav-items">
				    	<div class="brand-logo">
							<a href="fetchProducts">
								<img src="images/logo.jpeg" alt="Your Logo" class="logo">
							</a>
							<p>A-Z Groceries</p>
						</div>
			         <div class="search" id="search-container">
			            <input type="text" class="search-box" id="search-box" placeholder="search product">
			            <button class="search-btn" onclick="searchProduct()">search</button>
			        </div> 
					<% String userType = (String) session.getAttribute("userType"); %>
					<% if (userType != null) {%>
					    <% if ("Customer".equals(userType)) { %>
					        <a href="addToCart" id="cart-link"><img src="${pageContext.request.contextPath}/images/cart.png" alt=""></a>
					    <% } else if("Shopkeeper".equals(userType))  { %>
					        <a href="prodAmazon.jsp" id="amazon"><img src="${pageContext.request.contextPath}/images/amazon.png" alt=""></a>
					    <% } %>
					<% } %>
			        <a href="logOut" id="logout"><img src="${pageContext.request.contextPath}/images/logout.png" alt=""></a>
			    </div>
			</div>
			</nav>
	</header>
	<script>
	 document.addEventListener("DOMContentLoaded", function() {
	        var userType = "<%= session.getAttribute("userType") %>";

	        var searchContainer = document.getElementById("search-container");
	        var cartLink = document.getElementById("cart-link");
	        var Amazon = document.getElementById("amazon");
	        var logout = document.getElementById("logout");
				        
	        if(userType === 'null'){
	        	searchContainer.style.visibility = 'hidden'; 
	            //cartLink.style.visibility = 'hidden'; 
	            logout.style.visibility= 'hidden';
		        //Amazon.style.visibility = 'hidden'; 
	            
	            var currentLocation = window.location.href;
	            // if not on the home page redirect to home page
	            if (!currentLocation.includes("SignIn.jsp")) {
	            	if (!currentLocation.includes("SignUp.jsp")){
		                window.location.href = "SignIn.jsp";
	            	}
	            }
	        }
	        
	        if(userType === 'Shopkeeper'){
	        	searchContainer.style.visibility = 'hidden'; 
	        }
	        
	        if (userType === 'Amazon') {
	        	searchContainer.style.visibility = 'hidden'; 
	            cartLink.style.visibility = 'hidden'; 
	        }
	        
	        if (userType === 'Amazon') {
	           Amazon.style.visibility = 'hidden'; 
	        }
	        
	 });
	 
	function searchProduct() {
	    const searchQuery = document.getElementById("search-box").value;
	    window.location.href = "searchProduct?productName=" + searchQuery;
	}
	</script>
</body>
	