package org.example.racekattegruppen.Infrastructure;

import org.example.racekattegruppen.Model.Exhibition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public class ExhibitionRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Exhibition> readAllExhibitions() {
        String sql = "SELECT * FROM exhibition";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Exhibition(rs.getInt("id"), rs.getString("name"), rs.getDate("date"), rs.getTime("time").toLocalTime(), rs.getString("description"), rs.getInt("price")));
    }

    public void createExhibition(Exhibition exhibition) {
        String sql = "INSERT INTO exhibition (name, date, time, description, price) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, exhibition.getName(), exhibition.getDate(), exhibition.getTime(), exhibition.getDescription(), exhibition.getPrice());
    }

    public Exhibition readExhibition(int id) {
        String sql = "SELECT * FROM exhibition WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Exhibition(rs.getInt("id"), rs.getString("name"), rs.getDate("date"), rs.getTime("time").toLocalTime(), rs.getString("description"), rs.getInt("price")), id);
    }
    
    public boolean deleteExhibition(int id) {
        String sql = "DELETE FROM exhibition WHERE id = ?";
        return jdbcTemplate.update(sql, id) == 1;
    }



}
