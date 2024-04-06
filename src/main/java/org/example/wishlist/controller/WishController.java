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
@RequestMapping("")
public class WishController {

    private final WishService wishService;
    private final UserService userService;

    @Autowired
    public WishController(WishService wishService, UserService userService) {
        this.wishService = wishService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute();
        return "";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user) {
        for (User u : userService.getAllUsers()) {
            if (u.getId().equals(u.getId()) && u.getPassword().equals(u.getPassword())) {
                // TODO: skal redirecte til brugers wishlist
                return "redirect:/{username}/wishlist";
            }
        }
        // TODO: errormessage med "forkert login"
        return "redirect:/login";
    }

    //bliver forhåbentligt redirectet hertil fra login
    @GetMapping("/{username}/wishlist")
    public String wishlist(@PathVariable("username") String username, Model model) {
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

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user){
        userService.addUser(user);
        // TODO: skal redirecte til brugers wishlist
        // TODO: noget tekst med "succesfuld registrering"
        return "redirect:/";
    }

    @GetMapping("/addWish")
    public String showAddWishForm(Model model){
        List<TagEnum> tags = wishService.getPredefinedTags();
        model.addAttribute("tags", tags);
        model.addAttribute("wish", new WishItem("", 0, "", null));
        return "addAttraction";
    }






}
