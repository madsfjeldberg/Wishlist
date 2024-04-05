package org.example.wishlist.service;

import org.example.wishlist.model.User;
import org.example.wishlist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void addUser(User user){
        repository.addUser(user);
    }

    public void deleteUser(String username){
        repository.deleteUser(username);
    }

    public User getUser(String username){
        return repository.getUser(username);
    }

    public List<User> getAllUsers(){
        return repository.getAllUsers();
    }



}