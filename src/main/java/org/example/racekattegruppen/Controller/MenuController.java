package org.example.racekattegruppen.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.racekattegruppen.Database.RacekatteRepo;
import org.example.racekattegruppen.Model.Racekat;
import org.example.racekattegruppen.Model.User;
import org.example.racekattegruppen.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

        List<Racekat> racekatte = userService.readRacekatte();
        model.addAttribute("racekatte", racekatte);

        return "menu";
    }






}
