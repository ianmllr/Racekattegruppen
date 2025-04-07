package org.example.racekattegruppen.Model;

import java.sql.Timestamp;

public class Exhibition {
    private int id;
    private String name;
    private Timestamp date;
    private String description;
    private int price;

    public Exhibition(int id, String name, Timestamp date, String description, int price) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
