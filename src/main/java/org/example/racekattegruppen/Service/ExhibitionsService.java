package org.example.racekattegruppen.Service;

import org.example.racekattegruppen.Infrastructure.ExhibitionRepo;
import org.example.racekattegruppen.Model.Exhibition;
import org.example.racekattegruppen.Model.Racekat;
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

    public void updateExhibition(Exhibition exhibition) {
        if (exhibition != null) {
            exhibitionRepo.updateExhibition(exhibition);
        }
    }

    // tjekker om brugeren er skaberen af udstillingen og hvis sandt sletter udstillingen
    public void deleteExhibitionIfPossible(Exhibition exhibition, int currentUserID) {
        if (exhibition.getCreatedByID() == currentUserID) {
            deleteExhibitionById(exhibition.getId());
        }
    }

    public void deleteExhibitionById(int id) {
        exhibitionRepo.deleteExhibition(id);
    }

    public List<Racekat> getCatsInExhibition(int exhibitionId) {
        return exhibitionRepo.getCatsInExhibition(exhibitionId);
    }

    public void addCatToExhibition(int racecatId, int exhibitionId) {
        exhibitionRepo.addCatToExhibition(racecatId, exhibitionId);
    }

    public void removeCatFromExhibition(int catId, int exhibitionId) {
        exhibitionRepo.removeCatFromExhibition(catId, exhibitionId);
    }

    public int getExhibitionByPrice(int id) {
        return exhibitionRepo.readExhibition(id).getPrice();
    }

    public boolean isCatPaidForExhibition(int catId, int exhibitionId) {
        return exhibitionRepo.isCatPaidForExhibition(catId, exhibitionId);
    }

    // tjekker om brugeren har skabt udstillingen og returnerer sandt hvis ja
    public boolean userCreatedExhibition(int exhibitionId, int currentUserID) {
        Exhibition exhibition = readExhibition(exhibitionId);
        return exhibition.getCreatedByID() == currentUserID;
    }

}
