package org.example.wishlist.model;

import java.util.List;

public class WishItem {

    private String user_id;

    private String name;

    private int price;

    private String link;

    private List<String> tags;

    public WishItem(String name, int price, String link, List<String> tags, String user_id){
        this.name = name;
        this.price = price;
        this.link = link;
        this.tags = tags;
        this.user_id = user_id;
    }

    public WishItem(String name, int price, String link){
        this.name = name;
        this.price = price;
        this.link = link;
    }

    public WishItem() {
    }

    public String getName(){
        return name;
    }

 public void setName(String name){
        this.name = name;
    }

    public int getPrice(){
        return price;
    }

    public String getLink(){
        return link;
    }


    public void setPrice(int price){
        this.price = price;
    }

    public void setLink(String link){
        this.link = link;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


}
