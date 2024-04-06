package org.example.wishlist.repository;

import org.example.wishlist.model.TagEnum;
import org.example.wishlist.model.WishItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class WishRepository {

    private final List<WishItem> wishes;
    public List<TagEnum> getPredefinedTags() {
        return Arrays.asList(TagEnum.values());
    }
    
    public WishRepository() {

        // tilføj evt. flere ønsker her
        // bruges kun indtil vi flytter til SQL
        wishes = new ArrayList<>(List.of(
                new WishItem("Sko", 850, "https://www.nike.com")
        ));
        
    }
    // add new wish to arraylist
    public void addWish(WishItem wish){
        wishes.add(wish);
    }
    // get all wishes
    public List<WishItem> getWishes(){
        return wishes;
    }

    public WishItem getWishByName(String wishName){
        for(WishItem wish: wishes){
            if(wish.getName().equalsIgnoreCase(wishName)){
                return wish;
            }
        }
        return null;
    }
    // update wish
    public void updateWish(String wishToUpdateName, WishItem wishItem){
        for(WishItem wish : wishes){
            if(wish.getName().equalsIgnoreCase(wishToUpdateName)){
                wish = wishItem;

            }

    }}

    // delete wish
    public void deleteWish(String wishName) {
        for (WishItem wish : wishes) {
            if (wish.getName().equalsIgnoreCase(wishName)) {
                wishes.remove(wish);
            }

        }
    }}