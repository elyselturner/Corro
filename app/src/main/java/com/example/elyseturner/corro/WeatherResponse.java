package com.example.elyseturner.corro;

/**
 * Created by elyseturner on 7/28/15.
 */
public class WeatherResponse {

    private int cod;
    public WeatherResponse(int cod, String base, Weather main) {
        this.cod = cod;
        this.base = base;
        this.main = main;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Weather getMain() {
        return main;
    }

    public void setMain(Weather main) {
        this.main = main;
    }

    private String base;
    private Weather main;



}

