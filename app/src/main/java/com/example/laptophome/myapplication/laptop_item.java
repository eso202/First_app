package com.example.laptophome.myapplication;

public class laptop_item {
    private String title;
    private String category;
    private String descprtion;
    private byte[]image;
    private int price;






    public void setDescprtion(String descprtion) {
        this.descprtion = descprtion;
    }

    public String getDescprtion() {

        return descprtion;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public laptop_item(String title, String category, String descprtion, byte[] image, int price) {
        this.title = title;
        this.category = category;
        this.descprtion = descprtion;
        this.image = image;
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setPrice(int price) {
        this.price = price;
    }



    public int getPrice() {
        return price;
    }
}
