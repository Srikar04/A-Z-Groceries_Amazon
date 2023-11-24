<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Failure!</title>
<link rel="icon" href="images/logo.jpeg" type="image/icon type">
<style>
        .failure {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            text-align: center;
        }

        .failure h1 {
        	text-align: center;
            font-size: 24px;
            margin: 20px 0;
        }

        .failure a {
         	text-align:center;
            text-decoration: none;
            color: #0074d9;
        }

        .failure a:hover {
            color: #0056b3;
        }
    </style>
</head>
<body>
	<jsp:include page="header.jsp"/>
	<div class="failure">
		<img src="images/failure.png" alt="Failure Image" width="200">
		<h1>Oops Some error has Occurred! Your Transaction has failed</h1>
	    <a href="fetchProducts">Return to Homepage</a>
	</div>
	<br><br>
    <jsp:include page="footer.jsp"/>
</body>
</html>