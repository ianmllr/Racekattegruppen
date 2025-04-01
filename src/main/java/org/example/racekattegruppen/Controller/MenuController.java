package org.example.racekattegruppen.Controller;

import org.example.racekattegruppen.Database.RacekatteRepo;
import org.example.racekattegruppen.Model.Racekat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

import java.util.List;

@Controller
public class MenuController {

    RacekatteRepo racekatteRepo;

    @GetMapping("/menu")
    public String getMenu(Model model) {
        return "menu";
    }




}
