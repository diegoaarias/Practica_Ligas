package com.example.practica_ligas.model;

public class Temporadas {

    private String strSeason;

    public void setStrSeason(String strSeason) {
        this.strSeason = strSeason;
    }

    public Temporadas(String strSeason) {
        this.strSeason = strSeason;
    }

    public String getStrSeason() {
        return strSeason;
    }

    @Override
    public String toString() {
        return strSeason;
    }



}
