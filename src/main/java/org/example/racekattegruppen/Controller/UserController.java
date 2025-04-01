package org.example.racekattegruppen.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.racekattegruppen.Model.User;
import org.example.racekattegruppen.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService = new UserService();

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model, HttpSession session) {
        if (userService.register(user)) {
            session.setAttribute("user", user);
            return "redirect:/menu";
        }
        else {
            model.addAttribute("error", "Email findes allerede");
            return "register";
        }
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute User user){
        String updated = userService.updateUser(user);
        return "edit";
    }

    @DeleteMapping("/edit")
    public String deleteUser(@ModelAttribute User user, HttpSession session, Model model) {
        userService.deleteUser(user);
        session.invalidate();
        return "redirect:/index";
    }


}
