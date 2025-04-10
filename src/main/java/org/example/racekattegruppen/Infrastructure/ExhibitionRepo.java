package org.example.racekattegruppen.Infrastructure;

import org.example.racekattegruppen.Model.Exhibition;
import org.example.racekattegruppen.Model.Racekat;
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
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Exhibition(rs.getInt("id"), rs.getString("name"), rs.getDate("date"), rs.getTime("time").toLocalTime(), rs.getString("description"), rs.getInt("price"), rs.getInt("createdByID")));
    }

    public void createExhibition(Exhibition exhibition) {
        String sql = "INSERT INTO exhibition (name, date, time, description, price, createdByID) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, exhibition.getName(), exhibition.getDate(), exhibition.getTime(), exhibition.getDescription(), exhibition.getPrice(), exhibition.getCreatedByID());
    }

    public Exhibition readExhibition(int id) {
        String sql = "SELECT * FROM exhibition WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Exhibition(rs.getInt("id"), rs.getString("name"), rs.getDate("date"), rs.getTime("time").toLocalTime(), rs.getString("description"), rs.getInt("price"), rs.getInt("createdByID")), id);
    }

    public void updateExhibition(Exhibition exhibition) {
        System.out.println("Opdaterer udstilling med navnet " + exhibition.getName());
        String sql = "UPDATE exhibition SET name = ?, date = ?, time = ?, description = ?, price = ?, createdByID = ? WHERE id = ?";
        jdbcTemplate.update(sql, exhibition.getName(), exhibition.getDate(), exhibition.getTime(), exhibition.getDescription(), exhibition.getPrice(), exhibition.getCreatedByID(), exhibition.getId());
    }

    public void deleteExhibition(int id) {
        String sql = "DELETE FROM exhibition WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Racekat> getCatsInExhibition(int exhibitionId) {
        String sql = """
            SELECT rk.* FROM racekat rk
            JOIN exhibition_racecats er ON rk.id = er.racecat_id
            WHERE er.exhibition_id = ?
        """;
        return jdbcTemplate.query(sql, new Object[]{exhibitionId}, (rs, rowNum) -> {
            Racekat racekat = new Racekat();
            racekat.setId(rs.getInt("id"));
            racekat.setName(rs.getString("name"));
            racekat.setRace(rs.getString("race"));
            racekat.setDescription(rs.getString("description"));
            racekat.setAge(rs.getInt("age"));
            racekat.setUserID(rs.getInt("userID"));
            racekat.setPicture(rs.getString("picture"));
            return racekat;
        });
    }

    public void addCatToExhibition(int racecatId, int exhibitionId) {
        String sql = "INSERT INTO exhibition_racecats (racecat_id, exhibition_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, racecatId, exhibitionId);
    }

    public void removeCatFromExhibition(int catId, int exhibitionId) {
        String sql = "DELETE FROM exhibition_racecats WHERE racecat_id = ? AND exhibition_id = ?";
        jdbcTemplate.update(sql, catId, exhibitionId);
    }
}
