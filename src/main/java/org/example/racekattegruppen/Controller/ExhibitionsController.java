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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        System.out.println("called getexhibitions");
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        List<Exhibition> exhibitions = exhibitionsService.readAllExhibitions();
        model.addAttribute("exhibitions", exhibitions);
        return "exhibitions";
    }




}
