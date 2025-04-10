package org.example.racekattegruppen.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.racekattegruppen.Model.User;
import org.example.racekattegruppen.Service.ExhibitionsService;
import org.example.racekattegruppen.Service.StripeService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller

public class PaymentController {


    @Autowired
    private StripeService stripeService;
    @Autowired
    private ExhibitionsService exhibitionsService;


    @PostMapping("/payment/initiate")
    public String initiatePayment(@RequestParam int exhibitionId , @RequestParam List<Integer> catIds,HttpServletResponse response, HttpSession session, Model model) throws Exception {
        int price = exhibitionsService.getExhibitionByPrice(exhibitionId); // sætter pris, som er fra exhibition

        for (Integer catId : catIds) {
            System.out.println("Tjekker cat id: " + catId);

            if (exhibitionsService.isCatPaidForExhibition(catId, exhibitionId)) { // tjekker om allerede er betalt for denne kat
                System.out.println("Cat id: " + catId + "Har allerede joinede");
                model.addAttribute("error", "Katten er allerede tilmeldt");
                return "error";
            }
        }
        String checkoutUrl = stripeService.createCheckoutSession(

              price ,

                "http://localhost:8080/payment/success/" + exhibitionId, // definerer successUrl

                "http://localhost:8080/payment/cancel" // definerer cancelUrl
        );
        session.setAttribute("catIds", catIds);
        session.setAttribute("exhibitionId", exhibitionId);
        //response.sendRedirect(checkoutUrl);
        return "redirect:" + checkoutUrl;

    }


    @GetMapping("/payment/success/{exhibitionId}")
    public String paymentSuccess(@PathVariable int exhibitionId , HttpSession session, Model model) throws Exception {
        User user = (User) session.getAttribute("currentUser");
        List<Integer> catIds = (List<Integer>) session.getAttribute("catIds");
        if (catIds == null) {
            System.out.println("Kat id findes ikke i session");
            return "redirect:/exhibitions/" + exhibitionId;
        }
        for (Integer catId : catIds) {
            exhibitionsService.addCatToExhibition(catId, exhibitionId); // tilføjer katten til exhibition
        }
        model.addAttribute("exhibitionId", exhibitionId);
        return "success";

    }


    @GetMapping("/payment/cancel")

    public String paymentCancel() {
        return "cancel"; // lav en cancel.html
    }
}
