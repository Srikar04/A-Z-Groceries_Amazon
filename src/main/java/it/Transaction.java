package it;

import java.sql.Date;

public class Transaction {
    private int transactionId;
    private String customerUsername;
    private int productId;
    private Date transactionDate;
    private String shopkeeperUsername;
    private double transactionAmount;
    private int quantity; 

    // Constructors, getters, and setters

    public Transaction(int transactionId, String customerUsername, int productId, int quantity, Date transactionDate,
            String shopkeeperUsername, double transactionAmount){
        this.transactionId = transactionId;
        this.customerUsername = customerUsername;
        this.productId = productId;
        this.transactionDate = transactionDate;
        this.shopkeeperUsername = shopkeeperUsername;
        this.transactionAmount = transactionAmount;
        this.quantity = quantity;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getShopkeeperUsername() {
        return shopkeeperUsername;
    }

    public void setShopkeeperUsername(String shopkeeperUsername) {
        this.shopkeeperUsername = shopkeeperUsername;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
