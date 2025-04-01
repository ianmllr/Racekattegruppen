package org.example.racekattegruppen.Database;

import org.example.racekattegruppen.Model.Racekat;
import org.example.racekattegruppen.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RacekatteRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RacekatteRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // racekatte metoder
    public void createRacekat(Racekat racekat) {
        String sql = "INSERT INTO racekat (id, name, race, description, age) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, racekat.getId(), racekat.getName(), racekat.getRace(), racekat.getDescription(), racekat.getAge());
    }

    public List<Racekat> readRacekatte() {
        String sql = "SELECT * FROM racekat";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Racekat(rs.getInt("id"), rs.getString("navn"), rs.getString("race"), rs.getString("description"), rs.getInt("age")));
    }

    public Racekat readRacekat(int id) {
        String sql = "SELECT * FROM racekat WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Racekat(rs.getInt("id"), rs.getString("navn"), rs.getString("race"), rs.getString("description"), rs.getInt("age")), id);
    }

    public void updateRacekat(Racekat racekat) {
        String sql = "UPDATE racekat SET name = ?, race = ?, description = ?, age = ? WHERE id = ?";
        jdbcTemplate.update(sql, racekat.getName(), racekat.getRace(), racekat.getDescription(), racekat.getAge(), racekat.getId());
    }

    public void deleteRacekat(Racekat racekat) {
        String sql = "DELETE FROM racekat WHERE id = ?";
        jdbcTemplate.update(sql, racekat.getId());
    }

    ////////////////

    // user metoder
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

    public String updateUser(User user) {
        String sql = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPassword(), user.getId());
        return "User updated";
    }

    public void deleteUser(User user) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, user.getId());
    }

    public User getUser(int id) {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), 1);
    }
}
