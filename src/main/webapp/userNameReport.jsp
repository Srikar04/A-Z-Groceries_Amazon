<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="images/logo.jpeg" type="image/icon type">
<title>A-Z Groceries</title>
<style>
.transForm form {
    background-color: #fff;
    border: 1px solid #ddd;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.transForm label {
    font-weight: bold;
}

.transForm select {
    padding: 8px;
    width: 25%;
    margin: 6px 0;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.transForm input[type="submit"] {
    background-color: #0074d9;
    color: #fff;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.transForm input[type="submit"]:hover {
    background-color: #0056b3;
}

table {
    width: 100%;
    border-collapse: collapse;
}

table, th, td {
    border: 1px solid #000;
}

th, td {
    text-align: left;
    padding: 8px;
}

td:nth-child(even) {
    background-color: #f2f2f2;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp"/>

	     <%@page import="java.util.List" %>
		 <%@page import ="it.Transaction" %>
	<!-- Button to Get User Names -->
   	<div class="transForm">
	   	<form action="reportUsers" method="post">
	     <label for="userName">Select a User:</label>
		     <select id="userName" name="userName">
		        <%
		            List<String> users = (List<String>) session.getAttribute("users");
		            if (users != null && !users.isEmpty()) {
		                for (String str : users) {
		        %>
		        <option> <%= str %></option>
		        <%
		                }
		            }
		        %>
		    </select>
		     <br>
		     <br>
	        <input type="submit" value="Get Transactions">
	    </form>
   	</div>
    <% 
           List<Transaction> transactions = (List<Transaction>) request.getAttribute("Transactions");
       	if (transactions != null && transactions.size() != 0) { %>
       		<table>
            <thead>
                <tr>
                    <th>Transaction ID</th>
                    <th>Customer Username</th>
                    <th>Product ID</th>
                    <th>Transaction Date</th>
                    <th>Transaction Quantity</th>
                    <th>Transaction Amount</th>
                </tr>
            </thead>
             <tbody>
            <% 
           	for (Transaction transaction : transactions) {
       %>
         <tr>
                 <td><%= transaction.getTransactionId() %></td>
                 <td><%= transaction.getCustomerUsername() %></td>
                 <td><%= transaction.getProductId() %></td>
                 <td><%= transaction.getTransactionDate() %></td>
                 <td><%= transaction.getQuantity()  %></td>
                 <td><%= transaction.getTransactionAmount() %></td>
             </tr>
         <%
             	}
         	
         %>
     </tbody>
    </table>
    <%
       	}
    %>
    <br><br><br><br><br><br>
    <jsp:include page="footer.jsp"/>
</body>
</html>