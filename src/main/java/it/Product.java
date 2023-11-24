package it;

public class Product {
	private int id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private int quantity;
    private String shopkeeperUsername; // New attribute

    public Product(int id, String name, String description, double price, String imageUrl, int quantity, String shopkeeperUsername) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.shopkeeperUsername = shopkeeperUsername;
    }

	// Getters and setters for each attribute

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	 public String getShopkeeperUsername() {
        return shopkeeperUsername;
    }

    public void setShopkeeperUsername(String shopkeeperUsername) {
        this.shopkeeperUsername = shopkeeperUsername;
    }
}
