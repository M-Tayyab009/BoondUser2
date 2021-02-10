package com.example.boonduser;

public class Size {
    String ID,Price,Size;

    public Size(String ID, String price, String size) {
        this.ID = ID;
        Price = price;
        Size = size;
    }

    public Size(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

}
