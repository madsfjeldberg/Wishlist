package org.example.wishlist.service;

import org.example.wishlist.model.TagEnum;
import org.example.wishlist.model.WishItem;
import org.example.wishlist.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private final WishRepository repository;

    @Autowired
    public WishService(WishRepository repository) {
        this.repository = repository;
    }

    public void addWish(WishItem wish){
        repository.addWish(wish);
    }

    public void deleteWish(String name){
        repository.deleteWish(name);
    }

    public WishItem getWish(String name){
        return repository.getWish(name);
    }
    public List<TagEnum> getPredefinedTags(){
        return repository.getPredefinedTags();
    }

    public List<WishItem> getAllWishes(){
        return repository.getWishes();
    }

    public void updateWish(String wishToUpdateName, WishItem wish){
        repository.updateWish(wishToUpdateName, wish);
    }
}
