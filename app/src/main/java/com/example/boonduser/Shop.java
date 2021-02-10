package com.example.boonduser;

public class Shop {
    String Address, Name, Number, Shop_Name;

    public Shop(String address, String name, String number, String shop_Name) {
        Address = address;
        Name = name;
        Number = number;
        Shop_Name = shop_Name;
    }

    public Shop() {
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getShop_Name() {
        return Shop_Name;
    }

    public void setShop_Name(String shop_Name) {
        Shop_Name = shop_Name;
    }
}
