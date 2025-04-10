package org.example.racekattegruppen.Service;

import org.example.racekattegruppen.Infrastructure.RacekatteRepo;
import org.example.racekattegruppen.Model.Racekat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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
}
