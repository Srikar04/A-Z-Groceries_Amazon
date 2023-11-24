<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="card.css">
<link rel="icon" href="images/logo.jpeg" type="image/icon type">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<title>A-Z Groceries</title>
<style>
        .button-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
        }

        .button-container button {
            margin: 10px;
            padding: 10px 20px;
            background-color: #007bff; /* Button background color */
            color: #fff; /* Button text color */
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .button-container button:hover {
            background-color: #0056b3; /* Button background color on hover */
        }
        
        .card {
		  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
		  height:375px;
		  width:250px;
		  margin: auto;
		  margin-bottom:25px;
		  text-align: center;
		  font-family: arial;
		  padding-top: 20px;
		  padding-left:20px;
		  padding-right: 20px;
		  justify-content: space-around;
		  background-color:white
		}
</style>
<script>
	function validateForm() {
	    $(".addItems").each(function() {
	        var quantityValue = $(this).val();
	        if ($.trim(quantityValue) === "" || isNaN(quantityValue)) {
	            alert("Please enter a valid quantity to add.");
	            return false;
	        }
	    });
	    return true;
	}
	
	$(".restockForm").submit(function(e){
		if(!validateForm())
			e.preventDefault();
	});
	
</script>
</head>
<body>
<jsp:include page="header.jsp" />
	 <!-- Add a New Product Button -->
	 <div class="button-container">
		 <form action="addProduct.jsp" method="get">
	        <button type="submit">Add a New Product</button>
	     </form>
	
	    <!-- Produce User Reports Button -->
	    <form action="userReport.jsp" method="get">
	        <button type="submit">Produce User Reports by Dates</button>
	    </form>
	    
	    <form action="fetchUsers" method="get">
	        <button type="submit">Produce User Reports by Users</button>
	    </form>
	    
       <button onclick="window.location.href='prodAmazon.jsp';">Make Products available to Amazon Page</button>
	   
	 </div>
	 <br><br><br>
	 <h2 style="margin-left: 2em; margin-bottom: 1em;">Your Products:</h2>
	 <%@page import="java.util.List" %>
	 <%@page import="it.Product" %>
    	<div class="card-container">
	        <%  
	            List<Product> products = (List<Product>)session.getAttribute("products"); 
	            if(products != null){
	            	for (int i = 0;i<products.size();i++) {
	            		Product product = products.get(i); 
	            		String str = product.getName();%>
	            	    <div class="card">
	            	        <form action="RestockProduct" method="post" class="restockForm">
							    <img src="images/<%= product.getImageUrl() %>" alt="<%= product.getName() %>">
							    <h4><%= product.getName() %></h4>
							    <p><b>Description:</b><%= product.getDescription() %></p>
							    <p><b>Price</b>: <%= product.getPrice() %></p>
							    <p><b>Left In Stock:</b> <%= product.getQuantity() %></p>
							    <label for="addItems"><b>Add Items:</b></label>
            					<input type="number" class="addItems" name="addItems" value="1" min="1">
							    <br>
							    <!-- Hidden input for product ID -->
							    <input type="hidden" name="productId" value="<%= product.getId() %>">
							     <input type="hidden" name="quantity" value="<%= product.getQuantity() %>">
							    <input type="submit" class="button" value="Restock">
							</form>
	            	    </div>
	            	<% 
			        }
			    } else {
			%>
			        <p>No products sold by You.</p>
			<% 
			    }
			%>
	    </div>
<jsp:include page="footer.jsp" />
</body>
</html>