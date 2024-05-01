package com.example.BioskopXXL.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Movie {

    @Id
    private final int id;

    private final String title;
    private final int year;

    public Movie(int id, String title, int year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }
}
