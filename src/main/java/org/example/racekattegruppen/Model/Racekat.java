package org.example.racekattegruppen.Model;

public class Racekat {

    private int id;
    private String name;
    private String race;
    private String description;
    private int age;

    public Racekat(int id, String name, String race, String description, int age) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.description = description;
        this.age = age;
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

}
