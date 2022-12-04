package com.example.jsonexample.Product_Folder;

public class Product {
    private String title;
    private String description;
    private int price;
    private float discountPercentage;
    private Double rating;
    private String thumbnail;

    public Product(String t, String d,Double ra,String thumb){
        this.title = t;
        this.description = d;
        this.rating = ra;
        this.thumbnail = thumb;
    }

    public Product(String ti,String de,int pr,float di,Double ra,String th){
        this.title = ti;
        this.description = de;
        this.price = pr;
        this.discountPercentage = di;
        this.rating = ra;
        this.thumbnail = th;
    }

    public String getTitle(){ return this.title; }
    public String getDescription() { return this.description; }
    public int getPrice() { return this.price; }
    public float getDiscountPercentage() { return this.discountPercentage; }
    public Double getRating() { return this.rating; }
    public String getThumbnail() {return this.thumbnail; }
}
