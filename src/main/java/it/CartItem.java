package it;

public class CartItem {
    private String productName;
    private int productId;
    private int quantity;
    private double price;
    private String imageURL;

    public CartItem(int productId, String productName, int quantity, double price,String imageURL) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.imageURL = imageURL;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getCost() {
        return quantity * price;
    }
    
    public String getImageURL() {
    	return this.imageURL;
    }
}
