package org.example.wishlist.model;

import java.util.List;

public class WishItem {
    
    private String name;

    private int price;

    private String link;

    private List<String> tags;

    public WishItem(String name, int price, String link, List<String> tags){
        this.name = name;
        this.price = price;
        this.link = link;
        this.tags = tags;
    }

    public String getName(){
        return name;
    }

    public int getPrice(){
        return price;
    }

    public String getLink(){
        return link;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setLink(String link){
        this.link = link;
    }




}
