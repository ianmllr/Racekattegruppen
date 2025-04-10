package org.example.racekattegruppen.Service;

import org.example.racekattegruppen.Infrastructure.UserRepo;
import org.example.racekattegruppen.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;


import java.util.List;

@Service
public class UserService {
@Autowired

    private UserRepo userRepo;

    private User user;

    // user metoder

    public User login(String email, String password) {
        user = userRepo.readUserByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            System.out.println("Bruger logget ind: " + user.getUsername());
            return user;
        } else {
            return null;
        }
    }

    public Boolean register(User user) {
        if (userRepo.readUserByEmail(user.getEmail()) != null) { // tjekker om email findes
            return false;
        }
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed); // sætter hashed password, som password
        return userRepo.createUser(user);
    }

    public boolean deleteUser(User user) {
        return userRepo.deleteUser(user);
    }

    public boolean updateUser(User user) {
        if(user.getPassword().isEmpty()) {
            return userRepo.updateUserNoPass(user);
        }
        else {
            String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashed);
            return userRepo.updateUser(user);
        }
    }

    public User getUser(int id) {
        return userRepo.getUser(id);
    }

    public List<User> getUsers() {
        return userRepo.readAllUsers();
    }



}
