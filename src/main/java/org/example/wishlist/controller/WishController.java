package org.example.wishlist.controller;

import org.example.wishlist.model.TagEnum;
import org.example.wishlist.model.User;
import org.example.wishlist.model.WishItem;
import org.example.wishlist.service.UserService;
import org.example.wishlist.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WishController {

    private final WishService wishService;
    private final UserService userService;

    @Autowired
    public WishController(WishService wishService, UserService userService) {
        this.wishService = wishService;
        this.userService = userService;
    }

    /*
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute();
        return "";
    }
    */

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user) {
        for (User u : userService.getAllUsers()) {
            if (u.getId().equals(user.getId()) && u.getPassword().equals(user.getPassword())) {
                return "redirect:/" + u.getId() + "/wishlist";
            }
        }
        // TODO: errormessage med "forkert login"
        return "redirect:/login";
    }

    //bliver forhåbentligt redirectet hertil fra login
    @GetMapping("/{username}/wishlist")
    public String showWishlist(@PathVariable("username") String username, Model model) {
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        model.addAttribute("wishes", user.getWishes());
        return "wishlist";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register_user")
    public String register(@ModelAttribute("user") User user, Model model){
        User existingUser = userService.getUser(user.getId());
        if (existingUser != null) {
            // TODO: errormessage med "brugernavn allerede i brug"
            return "redirect:/register";
        }
        userService.addUser(user);
        model.addAttribute("user", user);
        System.out.println(userService.getAllUsers());
        // TODO: skal redirecte til brugers wishlist
        // TODO: noget tekst med "succesfuld registrering"
        return "redirect:/" + user.getId() + "/wishlist";
    }

    @GetMapping("/addWish")
    public String showAddWishForm(Model model){
        List<TagEnum> tags = wishService.getTags();
        model.addAttribute("tags", tags);
        model.addAttribute("wish", new WishItem("", 0, "", null));
        return "addWish";
    }






}
