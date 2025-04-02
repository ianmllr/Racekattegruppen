package org.example.racekattegruppen.Service;

import org.example.racekattegruppen.Database.RacekatteRepo;
import org.example.racekattegruppen.Model.Racekat;
import org.example.racekattegruppen.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;


import java.util.List;

@Service
public class UserService {
@Autowired
    private RacekatteRepo racekatteRepo;

    private User user;

    // user metoder

    public User login(String email, String password) {
        user = racekatteRepo.readUserByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            System.out.println("Bruger logget ind: " + user.getUsername());
            return user;
        } else {
            return null;
        }
    }

    public Boolean register(User user) {
        if (racekatteRepo.readUserByEmail(user.getEmail()) != null) {
            return false;
        }
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return racekatteRepo.createUser(user);
    }

    public boolean deleteUser(User user) {
        return racekatteRepo.deleteUser(user);
    }

    public boolean updateUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return racekatteRepo.updateUser(user);
    }

    public User getUser(int id) {
        return racekatteRepo.getUser(id);
    }

    // racekatte metoder

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



}
