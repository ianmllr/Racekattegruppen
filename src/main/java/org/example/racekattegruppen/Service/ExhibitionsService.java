package org.example.racekattegruppen.Service;

import org.example.racekattegruppen.Infrastructure.ExhibitionRepo;
import org.example.racekattegruppen.Model.Exhibition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExhibitionsService {

    @Autowired
    private ExhibitionRepo exhibitionRepo;

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

    public boolean deleteExhibitionIfPossible(Exhibition exhibition, int currentUserID) {
        if (exhibition.getId() == currentUserID) {
            deleteExhibitionById(exhibition.getId());
            return true;
            } else {
            return false;
        }
    }

    public void deleteExhibitionById(int id) {
        exhibitionRepo.deleteExhibition(id);
    }


}
