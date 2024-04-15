package org.example.wishlist;

import org.example.wishlist.model.User;
import org.example.wishlist.model.WishItem;
import org.example.wishlist.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("h2")
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    void getUser() {
        // Test that the repository returns the correct user
        User expected = repository.getUser("admin");
        assertEquals("admin", expected.getUsername());
    }

    @Test
    void addUser() {
        // Test that the repository adds a user
        User user = new User("test", "test");
        repository.addUser(user);
        assertEquals(user, repository.getUser("test"));
    }

    @Test
    void deleteUser() {
        // Test that the repository deletes a user
        repository.deleteUser("test");
        assertEquals(null, repository.getUser("test"));
    }

    @Test
    void deleteWish() {
        // Test that the repository deletes a wish
        repository.addWish("admin", new WishItem("test", 10, "test"));
        repository.deleteWish("admin", "test");
        assertEquals(null, repository.getWish("admin", "test"));
    }
}
