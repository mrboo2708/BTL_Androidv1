package com.example.btl_androidv1.Model;

public class Order {
    private String ProductID;
    private String ProductName;
    private String Quantity;
    private String Price;

    private String Discount;

    public Order() {
    }

    public Order(String orderId, String productName, String quantity, String price, String discount) {
        ProductID = orderId;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        Discount = discount;

        // 0 is  order, 1 is shipping,2 is shipped
    }


    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getOrderId() {
        return ProductID;
    }

    public void setOrderId(String orderId) {
        ProductID = orderId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
