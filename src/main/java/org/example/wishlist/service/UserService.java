package org.example.wishlist.service;

import org.example.wishlist.model.User;
import org.example.wishlist.model.WishItem;
import org.example.wishlist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService{

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void addUser(User user)  {
        repository.addUser(user);
    }

    public List<WishItem> getWishesForUser(String username) {
        return repository.getWishesForUser(username);
    }

    public WishItem getWish(String username, String name)  {
        return repository.getWish(username, name);
    }

    public void deleteWish(String username, String name) {
        repository.deleteWish(username, name);
    }

    public void deleteUser(String username) {
        repository.deleteUser(username);
    }

    public void addWish(String username, WishItem wish) {
        repository.addWish(username, wish);
    }

    public User getUser(String username) {
        return repository.getUser(username);
    }

    public void editWish(String username, String originalName, WishItem wishItem) {
        repository.editWish(username, originalName, wishItem);
    }

    public List<User> getAllUsers(){
        return repository.getAllUsers();
    }

    public User authenticateUser(String username, String password) {
        for (User user : repository.getAllUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
      return null; //repository.authenticateUser(username,password);
    }



}
