package org.example.racekattegruppen.Controller;


import jakarta.servlet.http.HttpSession;
import org.example.racekattegruppen.Model.Exhibition;
import org.example.racekattegruppen.Model.User;
import org.example.racekattegruppen.Service.ExhibitionsService;
import org.example.racekattegruppen.Service.RacekatteService;
import org.example.racekattegruppen.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ExhibitionsController {
    @Autowired
    private RacekatteService racekatteService;
    @Autowired
    private UserService userService;
    @Autowired
    private ExhibitionsService exhibitionsService;


    @GetMapping("/exhibitions")
    public String getExhibitions(Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        List<Exhibition> exhibitions = exhibitionsService.readAllExhibitions();
        model.addAttribute("exhibitions", exhibitions);
        return "exhibitions";
    }

    @GetMapping("/newexhibition")
    public String getNewExhibition(Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        Exhibition exhibition = new Exhibition();
        model.addAttribute("exhibition", exhibition);
        return "newexhibition";
    }

    @PostMapping("/newexhibition")
    public String postNewExhibition(@ModelAttribute Exhibition exhibition, Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        exhibition.setCreatedByID(user.getId());
        exhibitionsService.createExhibition(exhibition);
        return "redirect:/exhibitions";
    }

    @PostMapping("/exhibitions/delete")
    public String deleteExhibition(@RequestParam("id") int id, HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        Exhibition exhibition = exhibitionsService.readExhibition(id); // Sætter udstilling til den udstilling der skal slettes
        exhibitionsService.deleteExhibitionIfPossible(exhibition, user.getId()); // sletter udstilling hvis userID matcher createdByID
        return "redirect:/exhibitions";
    }




}
