package org.example.racekattegruppen.Infrastructure;

import org.example.racekattegruppen.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public boolean createUser(User user) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        int result = jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPassword());
        return result == 1;
    }

    public List<User> readAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password")));
    }

    public User readUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ? LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
        int update = jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPassword(), user.getId());
        if (update == 1) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean updateUserNoPass(User user) {
        String sql = "UPDATE users SET username = ?, email = ? WHERE id = ?";
        int update = jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getId());
        if (update == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteUser(User user) {
        String sql = "DELETE FROM users WHERE id = ?";
        int ok = jdbcTemplate.update(sql, user.getId());
        return ok == 1;
    }

    public User getUser(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        //return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password")));
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password")));
    }


}
