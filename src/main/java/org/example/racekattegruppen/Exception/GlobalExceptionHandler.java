package org.example.racekattegruppen.Exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(DataIntegrityViolationException e, Model model) {
        String message = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();
        if (message.contains("null value") || message.contains("not-null constraint")) {
            model.addAttribute("error", "Et felt mangler – tjek at alle felter er udfyldt.");
        } else if (message.contains("duplicate key") || message.contains("Duplicate entry")) {
            model.addAttribute("error", "Denne post findes allerede – brug en anden ID eller email.");
        } else if (message.contains("foreign key constraint")) {
            model.addAttribute("error", "Handlingen kunne ikke gennemføres pga. afhængigheder – fx en kat knyttet til en udstilling.");
        } else {
            model.addAttribute("error", "Der opstod en databasefejl – prøv igen.");
        }
        return "error";
    }


    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleEmptyResult(EmptyResultDataAccessException e, Model model) {
        model.addAttribute("error", "Elementet blev ikke fundet – det er måske allerede slettet.");
        return "error";
    }


    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Exception e, Model model) {
        e.printStackTrace(); // Kun til debugging
        model.addAttribute("error", "Uventet fejl: " + e.getClass().getSimpleName());
        return "error";
    }

    @ExceptionHandler
    public String handleException(BadSqlGrammarException e, Model model) {
        model.addAttribute("error", "Tjek database-forespørgslen");
        return "error";
    }

    @ExceptionHandler
    public String handleNoResourceFoundException(NoResourceFoundException e, Model model) {
        model.addAttribute("error", "Kan ikke finde ressource");
        return "error";
    }
}

