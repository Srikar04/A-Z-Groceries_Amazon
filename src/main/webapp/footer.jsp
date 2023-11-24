<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="footer.css">
<body>
	<footer>
    <p class="footer-title">About A-Z Groceries</p>
    <p class="info"></p>
    <p class="info">Support email - A_ZGroceries@example.com</p>
    <p class="info">telephone - 180 00 00 001, 180 00 00 002</p>
    <div class="footer-social-container">
        <div>
            <a href="#" class="social-link">terms and services</a>
            <a href="#" class="social-link">privacy page</a>
        </div>
        <div>
            <a href="#" class="social-link">instagram</a>
            <a href="#" class="social-link">facebook</a>
            <a href="#" class="social-link">twitter</a>
        </div>
    </div>
    <%
        String userRole = (String) session.getAttribute("userType");
        if ("Customer".equals(userRole)) {
    %>
	<p class="footer-credit">Enjoy exclusive discounts and a wide selection of products tailored for you.</p>
    <%
        } else if ("Shopkeeper".equals(userRole)) {
    %>
	<p class="footer-credit">Manage your store efficiently and access business tools to grow your sales.</p>
    <%
        } else if ("Amazon".equals(userRole)) {
    %>
	<p class="footer-credit">Explore a vast marketplace with millions of products and enjoy fast, reliable delivery.</p>
    <%
        }
    %>
</footer>
</body>
</html>