package org.example.racekattegruppen.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.racekattegruppen.Model.User;
import org.example.racekattegruppen.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService = new UserService();

    // Registrering
    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, HttpSession session, Model model) {
        if (userService.register(user)) {
            session.setAttribute("currentUser", user);
            System.out.println(user.getUsername() + "registreret"); // debugging
            return "redirect:/menu";
        }
        else {
            model.addAttribute("error", "Email findes allerede");
            return "register";
        }
    }

    // Login
    @GetMapping("/login")
    public String getLogin(Model model) {
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
            return "login";
        }
    }

    // edit, update osv
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable int id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        model.addAttribute(session.getAttribute("currentUser"));
        return "edit";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute User user, Model model, HttpSession session) {
        System.out.println(user.getPassword());
        boolean updated = userService.updateUser(user);
        session.setAttribute("currentUser", user);
        model.addAttribute("updated", updated);
        return "redirect:/menu";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@ModelAttribute User user, HttpSession session, @PathVariable int id, Model model) {
        session.getAttribute("currentUser");
        model.addAttribute("currentUser", userService.getUser(id));
        System.out.println(user.getId());
        boolean isDeleted = userService.deleteUser(user);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Deleted");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
