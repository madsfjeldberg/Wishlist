package org.example.wishlist.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;

    private String password;

    private List<WishItem> wishes;

    public User(String userName, String password, List<WishItem> wishes) {
        this.username = userName;
        this.password = password;
        this.wishes = wishes;
    }

    public User(String userName, String password) {
        this.username = userName;
        this.password = password;
        this.wishes = new ArrayList<>();
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<WishItem> getWishes() {
        return wishes;
    }

    public void setUsername(String username) {
        this.username = username;
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
                "userName='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
