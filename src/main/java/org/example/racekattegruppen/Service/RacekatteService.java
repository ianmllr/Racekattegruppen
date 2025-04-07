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

    public void createRacekat(Racekat racekat) throws Exception {
        racekatteRepo.createRacekat(racekat);
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

    public void updateRacekat(Racekat racekat) {
        racekatteRepo.updateRacekat(racekat);
    }



    public void deleteRacekat(Racekat racekat) {
        racekatteRepo.deleteRacekat(racekat);
    }



}
