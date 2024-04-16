package org.example.wishlist.repository;

import org.example.wishlist.model.User;
import org.example.wishlist.model.WishItem;
import org.example.wishlist.repository.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    public UserRepository() {
    }

    public void addUser(User user)  {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String sql = "INSERT INTO USERS (username, password) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void deleteUser(String username) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String sql = "DELETE FROM USERS WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.executeUpdate();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void deleteWish(String username, String wishName)  {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String sql = "DELETE FROM WISHES WHERE user_id = (SELECT user_id FROM USERS WHERE username = ?) AND name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, wishName);
            ps.executeUpdate();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

}

    public void editWish(String username, String originalName, WishItem newWish)  {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String sql = "UPDATE WISHES SET name = ?, price = ?, link = ? WHERE user_id = (SELECT user_id FROM USERS WHERE username = ?) AND name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newWish.getName());
            ps.setInt(2, newWish.getPrice());
            ps.setString(3, newWish.getLink());
            ps.setString(4, username);
            ps.setString(5, originalName);
            ps.executeUpdate();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public User getUser(String username) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String sql = "SELECT * FROM USERS WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(username, rs.getString("password"), getWishesForUser(username));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;

    }

    public User authenticateUser(String username, String password)  {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String sql = "SELECT * FROM USERS WHERE username = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(username, password, getWishesForUser(username));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    public void addWish(String username, WishItem wish)  {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String sql = "INSERT INTO WISHES (user_id, name, price, link) VALUES ((SELECT user_id FROM USERS WHERE username = ?), ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, wish.getName());
            ps.setInt(3, wish.getPrice());
            ps.setString(4, wish.getLink());
            ps.executeUpdate();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public WishItem getWish(String username, String wishName) {
        Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String sql = "SELECT * FROM WISHES WHERE user_id = (SELECT user_id FROM USERS WHERE username = ?) AND name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, wishName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new WishItem(rs.getString("name"), rs.getInt("price"), rs.getString("link"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<WishItem> getWishesForUser(String username) {
        List<WishItem> wishes = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String sql = "SELECT WISHES.* FROM WISHES JOIN USERS ON WISHES.user_id = USERS.user_id WHERE USERS.username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                wishes.add(new WishItem(rs.getString("name"), rs.getInt("price"), rs.getString("link"), rs.getBoolean("is_reserved"), rs.getString("reserved_username")));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return wishes;

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String sql = "SELECT username, password FROM USERS";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                users.add(getUser(rs.getString("username")));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return users;
    }

    public void unreserveWish() {
        // TODO
    }

    public void reserveWish(String username, String name, String userToUpdate) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String sql = "UPDATE WISHES SET is_reserved = true, reserved_username = ? WHERE user_id = (SELECT user_id FROM USERS WHERE username = ?) AND name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, userToUpdate);
            ps.setString(3, name);
            System.out.println(ps.executeUpdate());
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }

    public void unreserveWish(String username, String name) {
        Connection connection = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String sql = "UPDATE WISHES SET is_reserved = false, reserved_username = null WHERE user_id = (SELECT user_id FROM USERS WHERE username = ?) AND name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, name);
            System.out.println(ps.executeUpdate());
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

}
