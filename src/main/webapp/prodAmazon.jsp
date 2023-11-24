<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>A-Z Groceries</title>
<link rel="icon" href="images/logo.jpeg" type="image/icon type">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style>
.product-table {
    width: 80%; 
    border-collapse: collapse;
    margin: 20px auto; /* Center-align the table */
    border: 1px solid #ddd;
}

.product-table th, .product-table td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: center;
}

.product-table th {
    background-color: #f2f2f2;
}

.product-table input[type="checkbox"] {
    transform: scale(1.5);
}

button[type="submit"] {
    background-color: #333; 
    color: #fff; 
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
     margin-left: 20px; /* Added left margin */
     margin-bottom:20px;
}

button[type="submit"]:hover {
    background-color: #555; 
}
</style>
</head>
<body>
	<jsp:include page="header.jsp" />
	 <%@page import="java.util.List" %>
	 <%@page import="it.Product" %>
	<form action="removeProducts" method="post" id="productForm">
    <table class="product-table">
        <thead>
            <tr>
                <th></th>
                <th>Name</th>
                <th>Left in Stock</th>
                <th>Quantity to Send</th>
            </tr>
        </thead>
        <tbody>
            <%  
            List<Product> products = (List<Product>)session.getAttribute("products"); 
            if(products != null){
	                for (int i = 0; i < products.size(); i++) {
	                    Product product = products.get(i); 
	            %>
	            <tr>
	                <td><input type="checkbox" name="productNames" value="<%= product.getName() %>"></td>
	                <td><%= product.getName() %></td>
	                <td><%= product.getQuantity() %></td>
	                <td>
					<input type="number" name="quantity_<%= product.getName().replace(" ", "_") %>"
				     value="1" min="1" max="<%= product.getQuantity() %>">
				        
				     <input type="hidden" name="originalQuantity_<%= product.getName().replace(" ", "_") %>"
               		 value="<%= product.getQuantity() %>">	                
               </td>
	            </tr>
	            <%
	                }
	            } else {
	            %>
	            <tr>
	                <td colspan="4">No products sold by You.</td>
	            </tr>
	            <%
	            }
	            %>
	        </tbody>
	    </table>
	    <button type="submit" id="sendButton">Send</button>
	</form>
	<jsp:include page="footer.jsp" />
	<script>
	    $(document).ready(function () {
	        $("#sendButton").click(function () {
	        	$(this).prop("disabled", true);
	            // Change the names of quantity input fields to "quantities"
	            $("input[type=checkbox][name=productNames]:checked").each(function () {
	                var productName = $(this).val();
	                var selector = "input[name=quantity_" + productName.replace(/\s+/g, "_")+"]";
	                var quantityInput = $(selector);
					quantityInput.attr("name", "quantities");
	            });
	            
		        $("#productForm").submit();
	        });
	    });
	</script>
</body>
</html>