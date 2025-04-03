package org.example.racekattegruppen.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.racekattegruppen.Config.SecurityConfig;
import org.example.racekattegruppen.Database.RacekatteRepo;
import org.example.racekattegruppen.Model.Racekat;
import org.example.racekattegruppen.Model.User;
import org.example.racekattegruppen.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;
import java.util.List;

@Controller
public class MenuController {

    @Autowired
    UserService userService;


    @GetMapping("/menu")
    public String getMenu(Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        List<Racekat> racekatte = userService.readRacekatteByOwner(user.getId());
        model.addAttribute("racekatte", racekatte);

        return "menu";
    }

    @GetMapping("/editcat/{id}")
    public String getEditCat(Model model, HttpSession session, @PathVariable int id) {
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        Racekat racekat = userService.readRacekat(id);
        model.addAttribute("racekat", racekat);
        return "editcat";
    }

    @PostMapping("/editcat/{id}")
    public String postEditCat(@ModelAttribute Racekat racekat, Model model, HttpSession session, @PathVariable int id) {
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        model.addAttribute("racekat", racekat);
        userService.updateRacekat(racekat);
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
        userService.createRacekat(racekat);
        return "redirect:/menu";
    }

    @PostMapping("/menu")
    public String logout(HttpSession session) {
        if (session !=null) {
            session.invalidate();
        }
        return "redirect:/login";
    }








}
