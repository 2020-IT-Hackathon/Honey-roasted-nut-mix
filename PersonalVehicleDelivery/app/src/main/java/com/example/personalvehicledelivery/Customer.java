package com.example.personalvehicledelivery;

public class Customer {
    private String customerName;
    private String customerPhone ;
    private String customerAddress;
    private String customerCity;
    private String customerZip;
    private String customerState;


    public Customer(){

    }

    public Customer(String customerName, String customerPhone, String customerAddress, String customerCity, String customerZip, String customerState) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.customerCity= customerCity;
        this.customerZip= customerZip;
        this.customerState = customerState;

    }

    public String getCustomerName() { return customerName; }

    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }

    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getCustomerAddress() { return customerAddress; }

    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }

    public String getCustomerCity() { return customerCity; }

    public void setCustomerCity(String customerCity) { this.customerCity = customerCity; }

    public String getCustomerZip() { return customerZip; }

    public void setCustomerZip(String customerZip) { this.customerZip = customerZip; }

    public String getCustomerState() { return customerState; }

    public void setCustomerState(String customerState) { this.customerState = customerState; }
}
