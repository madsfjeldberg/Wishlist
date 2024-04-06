package org.example.wishlist.repository;

import org.example.wishlist.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private final List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>(List.of(
                new User("user1", "password1", new ArrayList<>()),
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

    public List<User> getAllUsers(){
        return users;
    }

}
