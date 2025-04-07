package org.example.racekattegruppen.Model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.Name;

public class User {
    private int id;

    @Name("Navn skal være gyldigt")
    @NotBlank(message = "Må ikke være tom")
    private String username;

    @Email(message = "Email skal være gyldig")
    @NotBlank(message = "Email må ikke være tom")
    private String email;

    @NotBlank(message = "Password må ikke være tom")
    @Size(min = 3, message = "Password skal være mindst 3 tegn lang")
    private String password;

    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
