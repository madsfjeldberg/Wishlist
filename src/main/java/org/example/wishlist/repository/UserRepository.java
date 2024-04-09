package org.example.wishlist.repository;

import org.example.wishlist.model.User;
import org.example.wishlist.model.WishItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class UserRepository {

    private final List<User> users;

    private List<WishItem> testList;

    public UserRepository() {
        testList = new ArrayList<>(List.of(
                new WishItem("T-shirt", 200, "https://www.boozt.com"),
                new WishItem("Sko", 850, "https://www.nike.com"),
                new WishItem("Bukser", 500, "https://www.zalando.dk"),
                new WishItem("Jakke", 1000, "https://www.asos.com"),
                new WishItem("Hat", 150, "https://www.hm.com")
        ));
        this.users = new ArrayList<>(List.of(
                new User("user1", "password1", testList),
                new User("user2", "password2", new ArrayList<>()),
                new User("user3", "password3", new ArrayList<>())
        ));
    }

    public void addUser(User user){
        users.add(user);
    }

    public void deleteUser(String username){
        users.removeIf(user -> user.getId().equals(username));
    }

    public User getUser(String username){
        return users.stream()
                .filter(user -> user.getId().equals(username))
                .findFirst()
                .orElse(null);
    }

    public WishItem getWish(String username, String wishName) {
        User user = getUser(username);
        if (user != null) {
            for (WishItem wishItem : user.getWishes()) {
                if (wishItem.getName().equalsIgnoreCase(wishName)) {
                    return wishItem;
                }
            }
        }
        return null;
    }

    public void deleteWish(String username, String wishName) {
        User user = getUser(username);
        if (user != null) {
            Iterator<WishItem> iterator = user.getWishes().iterator();
            while (iterator.hasNext()) {
                WishItem wishItem = iterator.next();
                if (wishItem.getName().equalsIgnoreCase(wishName)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    public void addWish(String username, WishItem wish) {
        User user = getUser(username);
        if (user != null) {
            user.getWishes().add(wish);
        }
    }

    public List<User> getAllUsers(){
        return users;
    }

    public User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getId().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}


