package org.example.racekattegruppen.Controller;


import jakarta.servlet.http.HttpSession;
import org.example.racekattegruppen.Model.Exhibition;
import org.example.racekattegruppen.Model.Racekat;
import org.example.racekattegruppen.Model.User;
import org.example.racekattegruppen.Service.ExhibitionsService;
import org.example.racekattegruppen.Service.RacekatteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ExhibitionsController {
    @Autowired
    private ExhibitionsService exhibitionsService;
    @Autowired
    private RacekatteService racekatteService;

    @GetMapping("/exhibitions")
    public String getExhibitions(Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser"); // henter nuværende bruger
        model.addAttribute("user", user);

        // henter listen af udstillinger og sætter dem som en attribute
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
        exhibition.setCreatedByID(user.getId()); // henter nuværende brugers ID og sætter det som createdByID
        exhibitionsService.createExhibition(exhibition);
        return "redirect:/exhibitions";
    }

    @PostMapping("/exhibitions/delete")
    public String deleteExhibition(@RequestParam("id") int id, HttpSession session){
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
        // sætter udstillingens createdByID til userId en ekstra gang
        exhibition.setCreatedByID(user.getId());
        exhibitionsService.updateExhibition(exhibition); // opdaterer udstilling
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
        // sætter detaljer på den hentede udstilling
        model.addAttribute("user", user);
        model.addAttribute("exhibition", exhibition);
        model.addAttribute("participatingCats", exhibitionsService.getCatsInExhibition(id));
        // henter liste med kattene tilhørende brugeren
        List<Racekat> userCats = racekatteService.readRacekatteByOwner(user.getId());
        model.addAttribute("userCats", userCats);

        return "exhibitiondetails";
    }

    @PostMapping("/exhibitions/removecat")
    public String removeCatFromExhibition(@RequestParam int exhibitionId, @RequestParam int catId, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        // tjekker om brugeren enten er kattens ejer eller skaberen af udstillingen og hvis sandt fjernes katten
        if (exhibitionsService.userCreatedExhibition(exhibitionId, user.getId()) ||
                racekatteService.userOwnsCat(user.getId(), catId)) {
            exhibitionsService.removeCatFromExhibition(catId, exhibitionId);
        }
        return "redirect:/exhibitions/" + exhibitionId;
    }

    @PostMapping("/exhibitions/addcat")
    public String addCatToExhibition(@RequestParam int exhibitionId, @RequestParam List<Integer> catIds, RedirectAttributes redirectAttributes) {
        List<Racekat> catsInExhibition = exhibitionsService.getCatsInExhibition(exhibitionId);

        // tjekker om en kat man vil tilføje allerede er i udstillingen og tilføjer kat hvis det er falsk
        for (Integer catId : catIds) {
            boolean catAlreadyInExhibition = catsInExhibition.stream().anyMatch(cat -> cat.getId() == catId);
            if (!catAlreadyInExhibition) {
                exhibitionsService.addCatToExhibition(catId, exhibitionId);
            } else {
                redirectAttributes.addFlashAttribute("error", "Mindst en kat er allerede i udstillingen.");
            }
        }
        return "redirect:/exhibitions/" + exhibitionId;
    }






}
