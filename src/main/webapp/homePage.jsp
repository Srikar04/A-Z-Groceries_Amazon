<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="card.css">
<meta charset="UTF-8">
<link rel="icon" href="images/logo.jpeg" type="image/icon type">
<title>A-Z Groceries</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style>	 
body{
	background-color: #a9a9a94f;
}

.entry button {
    margin: 10px;
    padding: 10px 20px;
    background-color: dimgray; 
    color: #fff; 
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.entry button:hover {
    background-color: #555; 
}

.entry select{
    width: 200px; 
    margin: 10px;
    padding: 10px 20px;
    border: 1px solid #ccc; 
    border-radius: 5px; 
    background-color: #fff; 
    color: #333; 
}

</style>
</head>
<body>
	<script>
	$(document).ready(function(){
			
		$.ajax({
            url: 'fetchShopkeeper',
            method: 'GET',
            dataType: 'html',
            success: function(data) {
            	console.log(data);
                $('#selectContainer').html(data);
            },
            error: function(xhr, status, error) {
                console.error('Error fetching shopkeepers: ' + error);
            }
        });
		
		 function validateQuantForm() {
		        $(".quantity").each(function() {
		            var quantityValue = $(this).val();
		            if ($.trim(quantityValue) === "" || isNaN(quantityValue)) {
		                alert("Please enter a valid quantity.");
		                return false;
		            }
		        });
		        return true;
		 }
		 
		$(".formQuant").submit(function(e){
			if(!validateQuantForm()){
		    	   e.preventDefault();
		     }
		})
		
		function validatePnumber(){
			var num = $("#pNumber").val();
			if($.trim(num) === ""|| isNaN(num)){
				alert("Please enter a valid number for Products Per page.");
	            return false;
			}
			return true;
		}

	    $(".formPnumber").submit(function(e) {
	        if (!validatePnumber()) {
	            e.preventDefault();
	        }
	    });
	    
	    $('img[data-image-url]').each(function() {
	        var $img = $(this);
	        var imageUrl = $img.data('image-url');
	        
	        setTimeout(function() {
	            // Load the image asynchronously
	            var img = new Image();
	            img.src = 'images/' + imageUrl;

	            img.onload = function() {
	                // Replace the placeholder image with the loaded image
	                $img.attr('src', img.src);
	            };
	        }, 200 + Math.floor(Math.random() * 1000)); // Random delay between 200 ms to 1200 ms
	    });
	})
	</script>
	<%@page import="java.util.List" %>
	<%@page import="it.Product" %>
	<jsp:include page="header.jsp"/>
		<form action="UpdateNumber" method="post" class="formPnumber">
		    <div class="entry">
		        <label for="pNumber">Number of Products per Page:</label>
		        <input type="number" id="pNumber" name="newPNumber" min="1" value="<%=session.getAttribute("pNumber")%>">
		        <br><br>
		        <input type="submit" value="Apply" class="button">
		    </div>
		</form>

    	<div class="pagination">
	    <form action="UpdatePage" method="post">
		        <button id="prevPageButton" name="direction" value="prev">Previous</button>
		        <input  readonly type="number" id="pageNumber" name="pageNumber" value="<%= session.getAttribute("pageNumber")%>">
		        <button id="nextPageButton" name="direction" value="next">Next</button>
		    </form>
		</div>
		
		<form action="fetchProdShop" method="post" class="formPnumber">
		    <div class="entry">
		       <label for="shopkeeper">Select Shopkeeper:</label>
	            <div id="selectContainer"></div>
	            <button type="submit">Fetch Products</button>
		    </div>
		</form>

    	<br><br><br>
    	<div class="card-container">
	        <% if (session.getAttribute("pNumber") != null || request.getParameter("productName") != null) {
	            // Only fetch and display products if the form has been submitted
	            @SuppressWarnings("unchecked")
	            List<Product> products = (List<Product>)request.getAttribute("products"); 
	            if(products != null && products.size() != 0){
	            	for (int i = 0;i<products.size();i++) {
	            		Product product = products.get(i); 
	            		String str = product.getName();%>
	            	    <div class="card">
	            	        <form action="addToCart" method="post"  class="formQuant">
							    <img src="images/placeholder.png" data-image-url="<%= product.getImageUrl() %>" alt="<%= product.getName() %>">
							    <h3><%= product.getName() %></h3>
							    <p><b>Shopkeeper:</b> <%= product.getShopkeeperUsername() %></p>
							    <p><b>Description:</b><%= product.getDescription() %></p>
							    <p>Price: <%= product.getPrice() %></p>
							    <p>Left In Stock: <%= product.getQuantity() %></p>
							    
							    <label for="quantity">Quantity:</label>
							    <input type="number" class="quantity" name="quantity" value="1" min="1" max="<%= product.getQuantity() %>">
							    <br>
							    <!-- Hidden input for product ID -->
							    <input type="hidden" name="productId" value="<%= product.getId() %>">
							    <input type="hidden" name="productName" value="<%= product.getName() %>">
							    <input type="hidden" name="price" value="<%= product.getPrice() %>">
							    <input type="hidden" name="image" value="<%= product.getImageUrl() %>">
							    <input type="submit" class="button" value="Add to Cart">
							</form>
	            	    </div>
	            	<% }
	            }else { %>
	            	<div class="card">
	            		  <p>No Products found.</p>
	            	</div>
  	          <% }
	         }%> 
	    </div>
	<jsp:include page="footer.jsp"/>
</body>
</html>