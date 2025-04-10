package org.example.racekattegruppen.Controller;


import jakarta.servlet.http.HttpSession;
import org.example.racekattegruppen.Model.Exhibition;
import org.example.racekattegruppen.Model.Racekat;
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
        System.out.println("prøver at slette exhibition med id " + id);
        User user = (User) session.getAttribute("currentUser");
        Exhibition exhibition = exhibitionsService.readExhibition(id);
        exhibitionsService.deleteExhibitionIfPossible(exhibition, user.getId()); // sletter udstilling hvis userID matcher createdByID
        return "redirect:/exhibitions";
    }

    @GetMapping("/editexhibition/{id}")
    public String getEditExhibition(Model model, HttpSession session, @PathVariable int id) {
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        Exhibition exhibition = exhibitionsService.readExhibition(id);
        model.addAttribute("exhibition", exhibition);
        return "editexhibition";
    }

    @PostMapping("/editexhibition/{id}")
    public String postEditExhibition(@ModelAttribute Exhibition exhibition, Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        model.addAttribute("exhibition", exhibition);
        exhibition.setCreatedByID(user.getId());
        exhibitionsService.updateExhibition(exhibition);
        return "redirect:/exhibitions";
    }

    @GetMapping("/exhibitions/{id}")
    public String getExhibitionDetails(@PathVariable int id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");

        Exhibition exhibition = exhibitionsService.readExhibition(id);
        if (exhibition == null) {
            System.out.println("Ingen udstilling fundet med id: " + id);
            return "redirect:/exhibitions";
        }

        model.addAttribute("user", user);
        model.addAttribute("exhibition", exhibition);
        model.addAttribute("participatingCats", exhibitionsService.getCatsInExhibition(id));
        List<Racekat> userCats = exhibitionsService.getUserCats(user.getId());
        model.addAttribute("userCats", userCats);

        return "exhibitiondetails";
    }

    @PostMapping("/exhibitions/removecat")
    public String removeCatFromExhibition(@RequestParam int exhibitionId, @RequestParam int catId, HttpSession session) {
        exhibitionsService.removeCatFromExhibition(catId, exhibitionId);
        return "redirect:/exhibitions/" +exhibitionId;
    }


    @PostMapping("/exhibitions/addcat")
    public String addCatToExhibition(@RequestParam int exhibitionId, @RequestParam List<Integer> catIds, HttpSession session) {
        for (Integer catId : catIds) {
            exhibitionsService.addCatToExhibition(catId, exhibitionId);
        }
        return "redirect:/exhibitions/" +exhibitionId;
    }







}
