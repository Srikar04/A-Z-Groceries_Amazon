<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="images/logo.jpeg" type="image/icon type">
<title>A-Z Groceries</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="product_form.css">
</head>
<body>
<jsp:include page="header.jsp" />
	<div class="product-form">
		<h2>Add Your Product</h2>
		<form action="createProduct" method="post" id="product-form" enctype="multipart/form-data">
	        <label for="name">Name:</label>
	        <input type="text" id="name" name="name" required><br>
	
	        <label for="description">Product Description:</label>
	        <textarea id="description" name="description" required></textarea><br>
	
	        <label for="quantity">Quantity:</label>
	        <input type="number" id="quantity" name="quantity" min="1.0" required><br>
	
	        <label for="price">Price:</label>
	        <input type="number" id="price" name="price" step="0.01" min="1.0" required><br>
	
	        <label for="image">Upload Image:</label>
	        <input type="file" id="image" name="image" accept="image/*" required><br>
	        <input class="add-button" type="submit" value="Add Product">
    	</form>
	</div>
<jsp:include page="footer.jsp" />

<script>
$(document).ready(function() {

    $("#product-form").submit(function(event) {
        if (!validateForm()) {
            event.preventDefault();
        }
    });

    function validateForm() {
        const productName = $("#name").val();
        const description = $("#description").val();

        var lettersOnly = /^[A-Za-z ]+$/;

        if ($.trim(productName) === "" || $.trim(description) === "") {
            alert("All fields are required. Please fill out all fields.");
            return false;
        }

        if (!productName.match(lettersOnly)) {
            alert("Product name must contain only letters.");
            return false;
        }

        return true;
    }
});
</script>

</body>
</html>