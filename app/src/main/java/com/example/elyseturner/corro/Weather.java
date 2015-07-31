package com.example.elyseturner.corro;

public class Weather {
    private int id;
    private String main;

    public Weather(int id, String main, String description) {
        this.id = id;
        this.main = main;
        this.description = description;
    }

    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    // default constructor, getters and setters
}
