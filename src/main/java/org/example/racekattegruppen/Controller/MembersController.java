package org.example.racekattegruppen.Controller;

import org.example.racekattegruppen.Service.RacekatteService;
import org.example.racekattegruppen.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/members")
public class MembersController {

    @Autowired
    private UserService userService;
    @Autowired
    private RacekatteService racekatteService;


    @GetMapping()
    public String showMembers(Model model) {
        // henter alle medlemmer og deres katte
        model.addAttribute("members", userService.getUsers());
        model.addAttribute("cats", racekatteService.readRacekatte());
        return "members";
    }

    @GetMapping("/showMember/{id}")
    public String showMember(@PathVariable int id, Model model) {
        model.addAttribute("member", userService.getUser(id));
        model.addAttribute("cats", racekatteService.readRacekatteByOwner(id));
        return "showMember";
    }



}
