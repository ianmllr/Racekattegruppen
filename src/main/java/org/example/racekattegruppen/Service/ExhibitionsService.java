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

    public List<Exhibition> readAllExhibitions() {
        List<Exhibition> exhibitions = exhibitionRepo.readAllExhibitions();
        if (!exhibitions.isEmpty()) {
            return exhibitions;
        } else {
            return null;
        }
    }

}
