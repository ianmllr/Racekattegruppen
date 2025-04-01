package org.example.racekattegruppen.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.racekattegruppen.Model.User;
import org.example.racekattegruppen.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService = new UserService();

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, Model model) {
        User loggedInUser = userService.login(user.getEmail(), user.getPassword());
        if (loggedInUser != null) {
            session.setAttribute("currentUser", loggedInUser);
            return "redirect:/menu";
        } else {
            model.addAttribute("error", "Forkert email eller password");
            return "index";
        }
    }



}
