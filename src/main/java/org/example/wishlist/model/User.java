package org.example.wishlist.model;

import java.util.List;

public class User {

    private String id;

    private String password;

    private List<WishItem> wishes;

    public User(String userName, String password, List<WishItem> wishes) {
        this.id = userName;
        this.password = password;
        this.wishes = wishes;
    }

    public User() {}

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public List<WishItem> getWishes() {
        return wishes;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWishes(List<WishItem> wishes) {
        this.wishes = wishes;
    }

    public void addWish(WishItem wish) {
        wishes.add(wish);
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userName='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
