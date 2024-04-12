package org.example.wishlist.controller;

import jakarta.servlet.http.HttpSession;
import org.example.wishlist.model.TagEnum;
import org.example.wishlist.model.User;
import org.example.wishlist.model.WishItem;
import org.example.wishlist.repository.WishRepository;
import org.example.wishlist.service.UserService;
import org.example.wishlist.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class WishController {

    private final WishService wishService;
    private final UserService userService;

    public WishController(WishService wishService, UserService userService) {
        this.wishService = wishService;
        this.userService = userService;
    }


    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("user", new User());
        return "home";
    }


    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    /*@PostMapping("/login")
    public String login(@ModelAttribute("user") User user) {
        for (User u : userService.getAllUsers()) {
            if (u.getId().equals(user.getId()) && u.getPassword().equals(user.getPassword())) {
                return "redirect:/" + u.getId() + "/wishlist";
            }
        }
        // TODO: errormessage med "forkert login"
        return "redirect:/login";
    }*/
    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpSession session, RedirectAttributes redirectAttributes) {
        User authenticatedUser = userService.authenticateUser(user.getId(), user.getPassword());
        if (authenticatedUser != null) {
            // Authentication successful, store user in session
            session.setAttribute("user", authenticatedUser);
            return "redirect:/" + authenticatedUser.getId() + "/wishlist";
        } else {
            // Authentication failed, redirect to login page with flash message
            redirectAttributes.addFlashAttribute("errorMessage", "Incorrect username or password");
            return "redirect:/login";
        }
    }


    //bliver forhåbentligt redirectet hertil fra login
   /* @GetMapping("/{username}/wishlist")
    public String showWishlist(@PathVariable("username") String username, Model model) {
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        model.addAttribute("wishes", user.getWishes());
        return "wishlist";
    }*/
    @GetMapping("/{username}/wishlist")
    public String showWishlist(@PathVariable("username") String username, HttpSession session, Model model) {
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser != null && authenticatedUser.getId().equals(username)) {
            model.addAttribute("user", authenticatedUser);
            model.addAttribute("wishes", authenticatedUser.getWishes());
            return "wishlist";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register_user")
    public String register(@ModelAttribute("user") User user, HttpSession session, RedirectAttributes redirectAttributes) {
        User existingUser = userService.getUser(user.getId());
        if (existingUser != null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Username already in use");
            return "redirect:/register";
        } else {
            userService.addUser(user);
            session.setAttribute("user", user);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful");
            return "redirect:/" + user.getId() + "/wishlist";
        }
    }

    @GetMapping("/{username}/addWish")
    public String showAddWishForm(@PathVariable String username, Model model) {
        User user = userService.getUser(username);
        List<TagEnum> tags = wishService.getTags();
        model.addAttribute("user", user);
        model.addAttribute("tags", tags);
        model.addAttribute("wish", new WishItem());
        return "addWish";
    }


    @PostMapping("/{username}/save")
    public ModelAndView addWish(@PathVariable String username,
                                @ModelAttribute("wish") WishItem wish) {
        userService.addWish(username, wish);
        return new ModelAndView("redirect:/" + username + "/wishlist");
    }


    @GetMapping("{username}/wishlist/deleteWish/{name}")
    public String deleteWish(@PathVariable String username, @PathVariable String name){
        WishItem wishToDelete = userService.getWish(username, name);
        if (wishToDelete != null){
            userService.deleteWish(username, name);
            System.out.println("wish deleted");
        }
        return "redirect:/" + username + "/wishlist";
    }






}
