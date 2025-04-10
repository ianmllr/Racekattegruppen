package org.example.racekattegruppen.Infrastructure;

import org.example.racekattegruppen.Model.Racekat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RacekatteRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // racekatte metoder
    public boolean createRacekat(Racekat racekat) {
        String sql = "INSERT INTO racekat (id, name, race, description, age, picture, userID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int created = jdbcTemplate.update(sql, racekat.getId(), racekat.getName(), racekat.getRace(), racekat.getDescription(), racekat.getAge(), racekat.getPicture(), racekat.getUserID());
        return created > 0;
    }

    public List<Racekat> readRacekatte() {
        String sql = "SELECT * FROM racekat";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Racekat(rs.getInt("id"), rs.getString("name"), rs.getString("race"), rs.getString("description"), rs.getInt("age"), rs.getString("picture"), rs.getInt("userID")));
    }

    public Racekat readRacekat(int id) {
        String sql = "SELECT * FROM racekat WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Racekat(rs.getInt("id"), rs.getString("name"), rs.getString("race"), rs.getString("description"), rs.getInt("age"), rs.getString("picture"), rs.getInt("userID")), id);
    }

    public List<Racekat> readRacekatteByOwner(int id) {
        String sql = "SELECT * FROM racekat WHERE userID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Racekat(rs.getInt("id"), rs.getString("name"), rs.getString("race"), rs.getString("description"), rs.getInt("age"), rs.getString("picture"), rs.getInt("userID")), id);
    }


    public boolean updateRacekat(Racekat racekat) {
        String sql = "UPDATE racekat SET name = ?, race = ?, description = ?, age = ?, picture = ?, userID = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, racekat.getName(), racekat.getRace(), racekat.getDescription(), racekat.getAge(), racekat.getPicture(), racekat.getUserID(), racekat.getId());
        return rowsAffected > 0;
    }

//    public boolean updateRacekat(Racekat racekat) {
//        String sql = "UPDATE racekat SET name = ?, race = ?, description = ?, age = ?, picture = ?, userID = ? WHERE id = ?";
//        System.out.println("SQL query: " + sql);
//        System.out.println("Parameters: " + Arrays.toString(new Object[]{racekat.getName(), racekat.getRace(), racekat.getDescription(), racekat.getAge(), racekat.getPicture(), racekat.getUserID(), racekat.getId()}));
//        int updated = jdbcTemplate.update(sql, racekat.getName(), racekat.getRace(), racekat.getDescription(), racekat.getAge(), racekat.getPicture(), racekat.getUserID(), racekat.getId());
//        return updated > 0;
//    }

    public boolean deleteRacekat(Racekat racekat) {
        String sql = "DELETE FROM racekat WHERE id = ?";
        int deleted = jdbcTemplate.update(sql, racekat.getId());
        return deleted > 0;
    }
}
