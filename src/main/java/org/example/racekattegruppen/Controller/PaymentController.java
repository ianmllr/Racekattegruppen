package org.example.racekattegruppen.Controller;

import org.example.racekattegruppen.Service.StripeService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;


import jakarta.servlet.http.HttpServletResponse;


@Controller

public class PaymentController {


    @Autowired

    private StripeService stripeService;


    @GetMapping("/payment")
    public void getPaymentPage(HttpServletResponse response) throws Exception {

        String checkoutUrl = stripeService.createCheckoutSession(

                50, // fx 50 kr.

                "http://localhost:8080/success",

                "http://localhost:8080/cancel"

        );


        response.sendRedirect(checkoutUrl);

    }


    @GetMapping("/success")

    public String paymentSuccess() {

        return "success"; // lav en success.html

    }


    @GetMapping("/cancel")

    public String paymentCancel() {

        return "cancel"; // lav en cancel.html

    }
}
