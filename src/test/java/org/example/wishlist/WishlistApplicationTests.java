package org.example.wishlist;

import org.example.wishlist.controller.WishController;
import org.example.wishlist.model.User;
import org.example.wishlist.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WishController.class)
public class WishlistApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testRedirectHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    public void testLoginFailure() throws Exception {
        when(userService.authenticateUser("wrongUser", "wrongPassword")).thenReturn(null);

        mockMvc.perform(post("/login")
                        .param("username", "wrongUser")
                        .param("password", "wrongPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andExpect(flash().attribute("errorMessage", "Incorrect username or password"));
    }

    @Test
    public void testLoginSuccess() throws Exception {
        User user = new User("user1", "password1");
        when(userService.authenticateUser("user1", "password1")).thenReturn(user);

        mockMvc.perform(post("/login")
                        .param("username", "user1")
                        .param("password", "password1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user1/wishlist"));
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        User newUser = new User("newuser", "newpass");

        when(userService.getUser("newuser")).thenReturn(null);

        mockMvc.perform(post("/register_user")
                        .param("username", "newuser")
                        .param("password", "newpass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/newuser/wishlist"))
                .andExpect(flash().attribute("successMessage", "Registration successful"));
    }


}
