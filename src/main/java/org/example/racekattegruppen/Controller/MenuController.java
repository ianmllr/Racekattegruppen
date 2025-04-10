package org.example.racekattegruppen.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.racekattegruppen.Model.Racekat;
import org.example.racekattegruppen.Model.User;
import org.example.racekattegruppen.Service.RacekatteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MenuController {

    @Autowired
    RacekatteService racekatteService;

    @GetMapping("/menu")
    public String getMenu(Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser"); // henter user
        model.addAttribute("user", user);
        List<Racekat> racekatte = racekatteService.readRacekatteByOwner(user.getId());
        model.addAttribute("racekatte", racekatte);

        return "menu";
    }

    @GetMapping("/editcat/{id}")
    public String getEditCat(Model model, HttpSession session, @PathVariable int id) {
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);

        // sætter objektet racekat til den pågældende racekat ud fra id'et
        Racekat racekat = racekatteService.readRacekat(id);
        model.addAttribute("racekat", racekat);
        return "editcat";
    }

    @PostMapping("/editcat/{id}")
    public String postEditCat(@PathVariable int id, @ModelAttribute Racekat racekat, Model model, HttpSession session) {
        racekat.setId(id); 
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        model.addAttribute("racekat", racekat);
        racekat.setUserID(user.getId());
        racekatteService.updateRacekat(racekat); // opdaterer racekat i databasen
        return "redirect:/menu";
    }


    @GetMapping("/newcat")
    public String getNewCat(Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        Racekat racekat = new Racekat();
        model.addAttribute("racekat", racekat);

        return "newcat";
    }

    @PostMapping("/newcat")
    public String postNewCat(@ModelAttribute Racekat racekat, Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        model.addAttribute("racekat", racekat);
        racekat.setUserID(user.getId());
        racekatteService.createRacekat(racekat);
        return "redirect:/menu";
    }

    @PostMapping("/menu/delete")
    public String deleteCat(@RequestParam("id") int id){
        Racekat racekat = racekatteService.readRacekat(id);
        if (racekat != null){
            boolean deleted = racekatteService.deleteRacekat(racekat);
            if (deleted) {
                return "redirect:/menu";
            }
        }
        return "error";
    }

    @PostMapping("/menu")
    public String logout(HttpSession session) {
        if (session !=null) {
            session.invalidate(); // glemmer session (user)
        }
        return "redirect:/login";
    }
}
