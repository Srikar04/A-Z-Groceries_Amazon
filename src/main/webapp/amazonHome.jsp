<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="images/logo.jpeg" type="image/icon type">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<title>A-Z Groceries</title>
<style>
        .button-container {
            display: flex;
            flex-direction: column;
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
        
        .Message{
        	text-align : center;
        	font-weight: bold;
        }
        
        
		select{
		    width: 200px; 
		    margin: 10px;
            padding: 10px 20px;
		    border: 1px solid #ccc; 
		    border-radius: 5px; 
		    background-color: #fff; 
		    color: #333; 
		}
		
	    table {
	        width: 80%;
	        margin: 20px auto;
	        border-collapse: collapse;
	        text-align: left;
	    }
	
	    th, td {
	        padding: 10px;
	        border: 1px solid #ddd;
	    }
	
	    th {
	        background-color: #f2f2f2;
	        font-weight: bold;
	    }
	
	    tr:nth-child(even) {
	        background-color: #f2f2f2;
	    }
	
	    tr:hover {
	        background-color: #e0e0e0;
	    }
</style>
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="button-container">
		 <form action="mergeStocks" method="get">
	        <button type="submit">Merge products and Generate JSON</button>
	        
	        <div class = "Message">
		        <%if(request.getAttribute("message") != null)  {%>
		        	<%=request.getAttribute("message") %>
		        <%} %>
	        </div>
	        <br>
	     </form>
	     
	    <form id="fetchAllProductsForm" method="get">
		    <button type="submit">Fetch All Products</button>
		</form>
		
		<form id="fetchByShopkeeperForm" method="post">
		    <label for="shopkeeper">Select Shopkeeper:</label>
		    <div id="selectContainer"></div>
		    <button type="submit">Fetch By Shopkeeper</button>
		</form>
      
        
         <%if(request.getAttribute("allProducts") != null)  {%>
         	<h1>Products:</h1>
        	<%=request.getAttribute("allProducts") %>
        <%} %>
        
        <div id="productsContainer"></div>
        
       

	</div>
	<br><br><br><br><br><br>
	<jsp:include page="footer.jsp" />
	
	<script type="text/javascript">
	    $(document).ready(function() {
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
	        
	        $("#fetchAllProductsForm").submit(function (event) {
	            event.preventDefault();
	            $.ajax({
	                type: "GET",
	                url: "fetchFromJson",
	                success: function (data) {
	                	$("#productsContainer").html(data);
	                }
	            });
	        });
	
	        $("#fetchByShopkeeperForm").submit(function (event) {
	            event.preventDefault(); 
	            var shopkeeper = $("#selectContainer select").val();
	            
	            $.ajax({
	                type: "POST",
	                url: "fetchByShopkeeper",
	                data: { shopkeeper: shopkeeper },
	                success: function (data) {
	                	$("#productsContainer").html(data);
	                }
	            });
	        });
	    });
</script>
	
</body>
</html>