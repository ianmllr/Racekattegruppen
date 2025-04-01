package org.example.racekattegruppen.Model;

public class Racekat {

    private int id;
    private String name;
    private String race;
    private String description;
    private int age;
    private String picture;

    public Racekat(int id, String name, String race, String description, int age, String picture) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.description = description;
        this.age = age;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRace() {
        return race;
    }
    public void setRace(String race) {
        this.race = race;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
}
