package com.example.personalvehicledelivery;



public class businessOwner {
    private String Name;
    private String restaurantName;
    private String streetAddress;
    private String businessPhone;
    private String employeeID;
    private String State;

//constructor
    public businessOwner(){

    }

    public businessOwner(String Name, String restaurantName, String streetAddress, String businessPhone,String employeeID,String State ){
        this.Name = Name;
        this.restaurantName = restaurantName;
        this.streetAddress = streetAddress;
        this.businessPhone = businessPhone;
        this.employeeID = employeeID;
        this.State = State;
    }

    public String getName() { return Name; }

    public String getRestaurantName() { return restaurantName; }

    public String getStreetAddress() { return streetAddress; }

    public String getBusinessPhone() { return businessPhone; }

    public String getEmployeeID() { return employeeID; }

    public String getState() {return State; }

    public void setName(String name) { Name = name; }

    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

    public void setEmployeeID(String employeeID) { this.employeeID = employeeID; }

    public void setBusinessPhone(String businessPhone) { this.businessPhone = businessPhone; }

    public void setState(String state) { State = state; }
}