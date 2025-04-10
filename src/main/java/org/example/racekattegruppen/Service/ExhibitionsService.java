package org.example.racekattegruppen.Service;

import org.example.racekattegruppen.Infrastructure.ExhibitionRepo;
import org.example.racekattegruppen.Model.Exhibition;
import org.example.racekattegruppen.Model.Racekat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExhibitionsService {

    @Autowired
    private ExhibitionRepo exhibitionRepo;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createExhibition(Exhibition exhibition) {
        if (exhibition != null) {
            exhibitionRepo.createExhibition(exhibition);
        }
    }

    public Exhibition readExhibition(int id) {
        return exhibitionRepo.readExhibition(id);
    }

    public List<Exhibition> readAllExhibitions() {
        List<Exhibition> exhibitions = exhibitionRepo.readAllExhibitions();
        if (!exhibitions.isEmpty()) {
            return exhibitions;
        } else {
            return null;
        }
    }

    public void updateExhibition(Exhibition exhibition) {
        if (exhibition != null) {
            exhibitionRepo.updateExhibition(exhibition);
        }
    }

    public void deleteExhibitionIfPossible(Exhibition exhibition, int currentUserID) {
        if (exhibition.getCreatedByID() == currentUserID) {
            deleteExhibitionById(exhibition.getId());
        }
    }

    public void deleteExhibitionById(int id) {
        exhibitionRepo.deleteExhibition(id);
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

    public List<Racekat> getUserCats(int id) {
        return exhibitionRepo.getUserCats(id);
    }

    public void addCatToExhibition(int racecatId, int exhibitionId) {
        exhibitionRepo.addCatToExhibition(racecatId, exhibitionId);
    }

    public void removeCatFromExhibition(int catId, int exhibitionId) {
        exhibitionRepo.removeCatFromExhibition(catId, exhibitionId);
    }
}
