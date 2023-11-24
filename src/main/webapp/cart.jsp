<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="images/logo.jpeg" type="image/icon type">
<title>A-Z Groceries</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="cart.css">
<style>
body {
    background-color:#a9a9a94f;
}
</style>
</head>
<body>
   	 <%@page import="java.util.List" %>
   	 <%@page import="it.CartItem" %>
   	 <%@ page import="jakarta.servlet.http.HttpSession" %>
	<jsp:include page="header.jsp"/>
   	 <div class="cart-container">
	 <h1 style="padding-top: 20px; padding-bottom: 20px;">Shopping Cart</h1>
        <%
			@SuppressWarnings("unchecked")
	        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
	        if (cart != null) {
            	for (CartItem item : cart) {
        %>
        <div class="Cart-Items">
        	<div class="image-box"> 
        	    <img src="images/<%= item.getImageURL() %>" alt="<%= item.getProductName() %>" >
        	</div>
            <h1 class="title"><%= item.getProductName() %></h1>
            <div class="counter">
				<p><b>Quantity:</b> <%= item.getQuantity() %></p>
            </div>
			 <div class="amount">
                 <p><b>Price:</b>Rs<%= item.getPrice() %></p>
            </div>
            <form  action="RemoveFromCart" method="post">
			        <input type="hidden" name="itemId" value="<%= item.getProductId() %>">
			        <input type="submit" class="delete-button" value="Delete">
			 </form>
         </div>
         
        <%
            	}
	        }
        %>
    <p class="bill">Total Bill: Rs<%= session.getAttribute("totalCost") %></p>
    <br>
    <hr>
    <br>
    <div class="checkout-form">
    <h2>Checkout</h2>
    <form action="checkOut" id="checkout-form" method="post">
        <label for="full-name">Full Name:</label>
        <input type="text" id="full-name" name="full_name" required>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" required>
        <label for="city">City:</label>
        <input type="text" id="city" name="city" required>
        <label for="state">State:</label>
        <input type="text" id="state" name="state" required>
        <label for="zipcode">Zipcode:</label>
        <input type="text" id="zipcode" name="zipcode" required>
        <label for="phone">Phone Number:</label>
        <input type="tel" id="phone" name="phone" required>
        <label for="credit-card">Credit Card Number:(xxxx xxxx xxxx xxxx)</label>
        <input type="text" id="credit-card" name="credit_card" required>
        <button type="submit" class="checkout-button">Checkout</button>
    </form>
</div>
	<br><br><br>
	</div>
	<jsp:include page="footer.jsp"/>
<script>
$(document).ready(function() {
    const form = $("#checkout-form");

    form.on("submit", function(event) {
        if (!validateForm()) {
            event.preventDefault();
        }
    });

    function validateForm() {
        const fullName = $("#full-name").val().trim();
        const address = $("#address").val().trim();
        const city = $("#city").val().trim();
        const state = $("#state").val().trim();
        const zipcode = $("#zipcode").val().trim();
        const phone = $("#phone").val().trim();
        const creditCard = $("#credit-card").val().trim();

        var lettersOnly = /^[A-Za-z ]+$/;
        var numbersOnly = /^[0-9]+$/;
        var creditRegex = /^[0-9 ]+$/;

        if (fullName === "") {
            alert("Full name cannot be empty.");
            return false;
        }

        if (!fullName.match(lettersOnly)) {
            alert("Full name must contain only letters.");
            return false;
        }

        if (city === "") {
            alert("City cannot be empty.");
            return false;
        }

        if (!city.match(lettersOnly)) {
            alert("City must contain only letters.");
            return false;
        }

        if (state === "") {
            alert("State cannot be empty.");
            return false;
        }

        if (!state.match(lettersOnly)) {
            alert("State must contain only letters.");
            return false;
        }
        if (zipcode === "" || !zipcode.match(numbersOnly) || zipcode.length !== 6) {
            alert("Zipcode must be a 6-digit number.");
            return false;
        }
        if (phone === "" || !phone.match(numbersOnly) || phone.length !== 10) {
            alert("Phone number must be a 10 digit number.");
            return false;
        }
        if (creditCard === "" || !creditCard.match(creditRegex) || creditCard.length !== 19) {
            alert("Credit card number must be a 16-digit number.");
            return false;
        }

        return true;
    }
});
</script>
</body>
</html>