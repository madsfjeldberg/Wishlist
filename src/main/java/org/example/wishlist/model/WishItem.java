package org.example.wishlist.model;

import java.util.List;

public class WishItem {

    private int id;

    private String user_id;

    private String name;

    private int price;

    private String link;


    private boolean isReserved;

    private String reservedUserName;

    public WishItem(String name, int price, String link, String user_id,
                    boolean isReserved, String reservedUserName) {
        this.name = name;
        this.price = price;
        this.link = link;
        this.user_id = user_id;
        this.isReserved = isReserved;
        this.reservedUserName = reservedUserName;
        this.id = getIdAndIncrement();
    }

    public WishItem(String name, int price, String link, boolean isReserved, String reservedUserName) {
        this.name = name;
        this.price = price;
        this.link = link;
        this.isReserved = isReserved;
        this.reservedUserName = reservedUserName;
        this.id = getIdAndIncrement();
    }

    public WishItem(String name, int price, String link){
        this.name = name;
        this.price = price;
        this.link = link;
        this.id = getIdAndIncrement();
    }

    public WishItem() {
    }

    public String getName(){
        return name;
    }

    public int getIdAndIncrement(){
        return id++;
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

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public String getReservedUserName() {
        return reservedUserName;
    }

    public void setReservedUserName(String reservedUserName) {
        this.reservedUserName = reservedUserName;
    }




}
