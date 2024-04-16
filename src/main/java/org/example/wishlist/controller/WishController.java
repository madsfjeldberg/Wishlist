package org.example.wishlist.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.wishlist.model.User;
import org.example.wishlist.model.WishItem;
import org.example.wishlist.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.ArrayList;

@Controller
@RequestMapping("")
public class WishController {

    private final UserService userService;

    public WishController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String redirectHome() {
        return "redirect:/home";
    }


    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        User authenticatedUser = (User) session.getAttribute("user");
        boolean isLoggedIn = authenticatedUser != null;
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("user", authenticatedUser);
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }


    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpSession session, RedirectAttributes redirectAttributes) {
        User authenticatedUser = userService.authenticateUser(user.getUsername(), user.getPassword());
        if (authenticatedUser != null) {
            session.setAttribute("user", authenticatedUser);
            return "redirect:/" + authenticatedUser.getUsername() + "/wishlist";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Incorrect username or password");
            return "redirect:/login";
        }
    }
    @GetMapping("/{username}/wishlist")
    public String showWishlist(@PathVariable("username") String username, HttpSession session, Model model) {
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser != null && authenticatedUser.getUsername().equals(username)) {
            model.addAttribute("user", authenticatedUser);
            model.addAttribute("wishes", userService.getWishesForUser(username));
            // model.addAttribute("wishes", authenticatedUser.getWishes());
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
        User existingUser = userService.getUser(user.getUsername());
        if (existingUser != null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Username already in use");
            return "redirect:/register";
        } else {
            user.setWishes(new ArrayList<>());
            userService.addUser(user);
            session.setAttribute("user", user);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful");
            return "redirect:/" + user.getUsername() + "/wishlist";
        }
    }

    @GetMapping("/{username}/wishlist/edit/{wishName}")
    public String edit(@PathVariable String username, @PathVariable String wishName, Model model) {
        User user = userService.getUser(username);
        WishItem wish = userService.getWish(username, wishName);
        model.addAttribute("user", user);
        model.addAttribute("wish", wish);
        model.addAttribute("originalName", wishName);
        return "edit";
    }

    @PostMapping("/{username}/wishlist/update")
    public String update(@PathVariable String username, @RequestParam("originalName") String originalName,
                         @ModelAttribute("wish") WishItem wish, HttpSession session) {
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null || !authenticatedUser.getUsername().equals(username)) {
            return "redirect:/login";
        }
        userService.editWish(username, originalName, wish);
        return "redirect:/" + username + "/wishlist";
    }


    @GetMapping("/{username}/addWish")
    public String showAddWishForm(@PathVariable String username, HttpSession session, Model model) {
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null || !authenticatedUser.getUsername().equals(username)) {
            return "redirect:/login";
        }
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        model.addAttribute("wish", new WishItem());
        return "addWish";
    }

    @PostMapping("/{username}/save")
    public String addWish(@PathVariable String username,
                                @ModelAttribute("wish") WishItem wish,
                                HttpSession session) throws SQLException {
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null || !authenticatedUser.getUsername().equals(username)) {
            return "redirect:/login";
        }
        userService.addWish(username, wish);
        System.out.println("Wish added for user: " + username);
        return "redirect:/" + username + "/wishlist";
    }



    @GetMapping("{username}/wishlist/deleteWish/{name}")
    public String deleteWish(@PathVariable String username, @PathVariable String name, HttpSession session) {
        User authenticatedUser = (User) session.getAttribute("user");

        if (authenticatedUser == null || !authenticatedUser.getUsername().equals(username)) {
            return "redirect:/login";
        }
        WishItem wishToDelete = userService.getWish(username, name);
        if (wishToDelete != null){
            userService.deleteWish(username, name);
            System.out.println("wish deleted");
        } else {
            System.out.println("No such wish found or does not belong to user");
        }
        return "redirect:/" + username + "/wishlist";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/home";
    }

    @GetMapping("/{username}/view_wishlist")
    public String viewWishlist(@PathVariable("username") String username, Model model, HttpSession session) {
        User authenticatedUser = (User) session.getAttribute("user");

        if(authenticatedUser == null){
            return "redirect:/login";
        }
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        model.addAttribute("wishes", user.getWishes());
        model.addAttribute("authenticatedUser", authenticatedUser);
        return "view_wishlist";
    }

    @PostMapping("/{username}/view_wishlist/reserve/{name}")
    public String reserveWish(@PathVariable String username, @PathVariable String name, HttpSession session) {
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            return "redirect:/login";
        }
        System.out.println(authenticatedUser.getUsername() + " is reserving " + name);
        System.out.println("logged in user pw: " + authenticatedUser.getPassword());
        userService.reserveWish(authenticatedUser.getUsername(), name, username);
        return "redirect:/" + username + "/view_wishlist";
    }

    @PostMapping("/{username}/view_wishlist/unreserve/{name}")
    public String unreserveWish(@PathVariable String username, @PathVariable String name, HttpSession session) {
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            return "redirect:/login";
        }
        System.out.println(authenticatedUser.getUsername() + " is unreserving " + name);
        userService.unreserveWish(username, name);
        return "redirect:/" + username + "/view_wishlist";
    }


}