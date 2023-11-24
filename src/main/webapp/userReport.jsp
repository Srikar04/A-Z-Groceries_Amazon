<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Report</title>
    <link rel="icon" href="images/logo.jpeg" type="image/icon type">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style>
        .transForm form {
            background-color: #fff;
            border: 1px solid #ddd;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .transForm  label {
            font-weight: bold;
        }

       .transForm input[type="date"] {
            padding: 8px;
            width: 25%;
            margin: 6px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .transForm  input[type="submit"] {
            background-color: #0074d9;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .transForm  input[type="submit"]:hover {
            background-color: #0056b3;
        }
        
        table {
		    width: 100%;
		    border-collapse: collapse;
		}
		
		table, th, td {
		    border: 1px solid #000; /* Add borders as needed */
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
	<script>
	$(document).ready(function () {
	    $('#submitForm').on('click', function () {
	        const startingDate = new Date($('#startingDate').val());
	        const endingDate = new Date($('#endingDate').val());

	        if (startingDate > endingDate) {
	            alert('Starting date cannot be greater than ending date.');
	            return false;
	        }
	    });
	});
    </script>
  
	 <%@page import="java.util.List" %>
	<%@page import="it.Transaction" %>
	<jsp:include page="header.jsp"/>
	<div class="transForm">
		<form action="reportDates" method="post" onsubmit="return validateForm()">
	        <label for="startingDate">Starting date:</label>
	        <input type="date" name="startingDate" id="startingDate" required><br>
	        <label for="endingDate">Ending date:</label>
	        <input type="date" name="endingDate" id="endingDate" required><br><br>
	        <input type="submit" value="Get Transactions" id="submitForm">
    	</form>
	</div>
         <!-- Loop through your transactions data obtained from the servlet and display it in rows -->
       <% 
           List<Transaction> transactions = (List<Transaction>) request.getAttribute("Transactions");
       	if (transactions != null) { %>
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
            <% 
           	for (Transaction transaction : transactions) {
       %>
      <tbody>
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
         	}
         %>
     </tbody>
    </table>
    <br><br>
	<jsp:include page="footer.jsp"/>
	
</body>
</html>
