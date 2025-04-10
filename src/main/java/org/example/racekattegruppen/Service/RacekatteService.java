package org.example.racekattegruppen.Service;

import org.example.racekattegruppen.Infrastructure.RacekatteRepo;
import org.example.racekattegruppen.Model.Racekat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RacekatteService {

    @Autowired
    private RacekatteRepo racekatteRepo;

    public boolean createRacekat(Racekat racekat)  {
        return racekatteRepo.createRacekat(racekat);
    }

    public Racekat readRacekat(int id) {
        return racekatteRepo.readRacekat(id);
    }

    public List<Racekat> readRacekatte() {
        return racekatteRepo.readRacekatte();
    }

    public List<Racekat> readRacekatteByOwner(int id) {
        return racekatteRepo.readRacekatteByOwner(id);
    }

    public boolean updateRacekat(Racekat racekat) {
        return racekatteRepo.updateRacekat(racekat);
    }

    public boolean deleteRacekat(Racekat racekat) {
        return racekatteRepo.deleteRacekat(racekat);
    }

    // returnerer sandt eller falsk ud fra om brugerens id er lig med kattens userID
    public boolean userOwnsCat(int userId, int catId) {
        Racekat racekat = racekatteRepo.readRacekat(catId);
        return racekat.getUserID() == userId;
    }
}
