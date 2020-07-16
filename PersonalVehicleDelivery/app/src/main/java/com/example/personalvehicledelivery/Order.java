package com.example.personalvehicledelivery;

public class Order {
    private String customerEmail;
    private String customerAddress;
    private String item;
    private String businessAddress;
    private String businessName;

    public Order() {

    }

    public Order(String email, String customerAddress, String item, String businessAddress, String businessName) {
        this.businessAddress = businessAddress;
        this.businessName = businessName;
        this.customerAddress = customerAddress;
        this.customerEmail = email;
        this.item = item;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getItem() {
        return item;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public String getBusinessName() {
        return businessName;
    }
}