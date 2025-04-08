package org.example.racekattegruppen.Model;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

public class Exhibition {
    private int id;
    private String name;
    private Date date;
    private LocalTime time;
    private String description;
    private int price;
    private int createdByID;

    public Exhibition(int id, String name, Date date, LocalTime time, String description, int price, int createdByID) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
        this.price = price;
        this.createdByID = createdByID;
    }

    public Exhibition(){}

    public int getCreatedByID() {
        return createdByID;
    }

    public void setCreatedByID(int createdByID) {
        this.createdByID = createdByID;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public LocalTime getTime() {
         return time;
    }

    public void setTime(LocalTime time) {
         this.time = time;
    }

}
