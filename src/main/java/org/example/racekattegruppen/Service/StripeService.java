package org.example.racekattegruppen.Service;

import com.stripe.Stripe;
//import com.stripe.model.billingportal.Session;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StripeService {

    @Value("${stripe.secret.key}") // henter api key
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey; // sætter api key
    }


    public String createCheckoutSession(int ammountDKK, String successUrl, String cancelUrl) throws Exception {
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(successUrl)
                        .setCancelUrl(cancelUrl)
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPriceData(
                                                SessionCreateParams.LineItem.PriceData.builder()
                                                        .setCurrency("dkk")
                                                        .setUnitAmount((long) ammountDKK * 100) // beløb i øre
                                                        .setProductData(
                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                        .setName("Betaling for kat")
                                                                        .build())
                                                        .build())
                                        .build())
                        .build();


        Session session = Session.create(params);
        return session.getUrl();
    }
}
