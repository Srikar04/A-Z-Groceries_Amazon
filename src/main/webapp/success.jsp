<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Success!</title>
<link rel="icon" href="images/logo.jpeg" type="image/icon type">
<style>
        .success {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            text-align: center;
        }

        .success h1 {
        	text-align: center;
            font-size: 24px;
            margin: 20px 0;
        }

        .success a {
         	text-align:center;
            text-decoration: none;
            color: #0074d9; 
        }

        .success a:hover {
            color: #0056b3; 
        }
    </style>
</head>
<body>
	<jsp:include page="header.jsp"/>
	<div class="success">
		<img src="images/success.jpeg" alt="Success Image" width="200">
		<h1>Your purchase has been successful. Delivery on the way!</h1>
	    <a href="fetchProducts">Return to Homepage</a>
	</div>
	<br><br>
    <jsp:include page="footer.jsp"/>
</body>
</html>